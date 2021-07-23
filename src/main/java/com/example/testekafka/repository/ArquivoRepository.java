package com.example.testekafka.repository;

import com.example.testekafka.entity.Arquivo;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoRepository extends ReactiveMongoRepository<Arquivo, String> {
}
