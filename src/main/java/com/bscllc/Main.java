package com.bscllc;

import com.bscllc.citibike.CitiBike;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Path path = FileSystems.getDefault().getPath(args[0]);
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

        Properties props = new Properties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, args[1]);
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        try (
                Reader reader = Files.newBufferedReader(path);
                Producer<String, String>producer = new KafkaProducer<>(props);
        ) {
            CsvToBean<CitiBike> cb = new CsvToBeanBuilder<CitiBike>(reader)
                    .withType(CitiBike.class)
                    .build();

            cb.stream().iterator().forEachRemaining(item -> {
                try {
                    System.out.format("%s%n", mapper.writeValueAsString(item));

                    ProducerRecord<String, String>producerRecord = new ProducerRecord<>(args[2], item.getRideId(), mapper.writeValueAsString(item));

                    producer.send(producerRecord);

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
