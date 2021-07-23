package com.example.testekafka.service;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerExample {

    private static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9091");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return new KafkaProducer<>(props);
    }

    static void runProducer(final int sendMessageCount) throws Exception {
        final Producer<Long, String> producer = createProducer();
        long time = System.currentTimeMillis();

        try {
            String msg = "{\n" +
                    "      \"location_id\":385858,\n" +
                    "      \"location_uuid\":\"4b2107b9-3d3e-4a28-9490-5ce6d9d4c1b0\",\n" +
                    "      \"sku\":17236,\n" +
                    "      \"total_quantity\":5,\n" +
                    "      \"reserved_quantity\":0,\n" +
                    "      \"available_quantity\":5,\n" +
                    "      \"threshold\":null,\n" +
                    "      \"updated_at\":\"2021-07-07 20:00:13.003690\",\n" +
                    "      \"enabled\":true,\n" +
                    "      \"external_id\":null,\n" +
                    "      \"distribution_center\":null\n" +
                    "   }\n";

            for (long index = time; index < time + sendMessageCount; index++) {
                final ProducerRecord<Long, String> record =
                        new ProducerRecord<>("testeReactive", msg);

                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);

            }
        } finally {
            producer.flush();
            producer.close();
        }
    }

    public static void main(String[] args) throws Exception {
        runProducer(10);
    }
}
