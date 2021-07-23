package com.example.testekafka;

import com.example.testekafka.entity.Arquivo;
import com.example.testekafka.repository.ArquivoRepository;
import com.example.testekafka.service.ConsumerExample;
import com.example.testekafka.service.SampleConsumer;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class TestekafkaApplication extends AbstractReactiveMongoConfiguration implements CommandLineRunner {

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create();
	}

	@Override
	protected String getDatabaseName() {
		return "gera";
	}

	@Autowired
	ArquivoRepository arquivoRepository;

	public static void main(String[] args) {
		SpringApplication.run(TestekafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SampleConsumer.main(arquivoRepository);
	}


}
