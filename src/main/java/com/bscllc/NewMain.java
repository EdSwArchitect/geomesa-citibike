package com.bscllc;

import com.bscllc.citibike.CitiBike;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class NewMain {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(args[0]);
        ObjectMapper mapper = new ObjectMapper();
        Kryo kyro = new Kryo();
        kyro.register(CitiBike.class);
        kyro.register(Date.class);

        try (
                Reader reader = Files.newBufferedReader(path);
                Output output = new Output(new FileOutputStream("/data/edwin-output.bin"));
        ) {
            CsvToBean<CitiBike> cb = new CsvToBeanBuilder<CitiBike>(reader)
                    .withType(CitiBike.class)
                    .build();

            cb.stream().iterator().forEachRemaining(item -> {
                try {
                    System.out.format("%s%n", mapper.writeValueAsString(item));
                    kyro.writeObject(output, item);


                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
