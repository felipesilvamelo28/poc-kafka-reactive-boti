package com.example.testekafka.service;

import com.example.testekafka.entity.Arquivo;
import com.example.testekafka.repository.ArquivoRepository;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class SampleConsumer {

    private static final Logger log = LoggerFactory.getLogger(SampleConsumer.class.getName());

    private final static String TOPIC = "testeReactive";
    private final static String BOOTSTRAP_SERVERS = "localhost:9091";

    private final ReceiverOptions<Integer, String> receiverOptions;
    private final SimpleDateFormat dateFormat;

    public SampleConsumer(String bootstrapServers) {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "sample-consumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        receiverOptions = ReceiverOptions.create(props);
        dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
    }

    public List<Arquivo> consumeMessages(String topic, CountDownLatch latch, ArquivoRepository arquivoRepository) {

        List<Arquivo> arquivosRercebidos = new ArrayList<>();

        ReceiverOptions<Integer, String> options = receiverOptions.subscription(Collections.singleton(topic))
                .addAssignListener(partitions -> log.debug("onPartitionsAssigned {}", partitions))
                .addRevokeListener(partitions -> log.debug("onPartitionsRevoked {}", partitions));
        Flux<ReceiverRecord<Integer, String>> kafkaFlux = KafkaReceiver.create(options).receive(); //cria um fluxo de leitura de msgs

        kafkaFlux.subscribe(record -> { //cria um subscriber do fluxo, isso ativa o recebimento das msgs, daqui em diante não sei salvar os recebidos
            ReceiverOffset offset = record.receiverOffset();
            System.out.printf("Received message: topic-partition=%s offset=%d timestamp=%s key=%d value=%s\n",
                    offset.topicPartition(),
                    offset.offset(),
                    dateFormat.format(new Date(record.timestamp())),
                    record.key(),
                    record.value());
            arquivosRercebidos.add(new Gson().fromJson(record.value(), Arquivo.class));
            offset.acknowledge();
            latch.countDown();
        });

        return arquivosRercebidos; //nunca chega nesse return, fica eternamente escutando, então essa alternativa nao rola
    }

    public static void main(ArquivoRepository arquivoRepository) throws Exception {
        int count = 20;
        CountDownLatch latch = new CountDownLatch(count);
        SampleConsumer consumer = new SampleConsumer(BOOTSTRAP_SERVERS);
        List<Arquivo> arquivos = consumer.consumeMessages(TOPIC, latch, arquivoRepository);
    }
}
