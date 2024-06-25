package com.bscllc;

import com.bscllc.citibike.CitiBike;
import com.bscllc.citibike.CitiBikeSFT;
import org.geotools.api.data.DataStore;
import org.geotools.api.data.DataStoreFinder;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.feature.simple.SimpleFeatureBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SFTMain {
    // https://www.geomesa.org/documentation/stable/user/kafka/usage.html

    public static void main(String... args) {
        CitiBikeSFT citiBikeSFT = new CitiBikeSFT();
        List<SimpleFeature> features = new ArrayList();
        SimpleFeatureType sft = citiBikeSFT.getSimpleFeatureType();
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(sft);

        List<CitiBike> citiBikes = citiBikeSFT.getList("");

        citiBikes.forEach(citiBike -> {
            builder.set("rideId", citiBike.getRideId());
            builder.set("rideableType", citiBike.getRideableType());
            builder.set("startedAt", citiBike.getStartedAt());
            builder.set("endedAt", citiBike.getEndedAt());
            builder.set("startStationName", citiBike.getEndStationName());
            builder.set("startStationId", citiBike.getStartStationId());
            builder.set("endStationName", citiBike.getEndStationName());
            builder.set("endStationId", citiBike.getEndStationId());
            builder.set("startLat", citiBike.getStartLat());
            builder.set("startLon", citiBike.getStartLon());
            builder.set("endLat", citiBike.getEndLat());
            builder.set("endLon", citiBike.getEndLon());
            builder.set("memberCasual", citiBike.getMemberCasual());
            builder.set("dtg", citiBike.getStartedAt());
            builder.set("geom", "LINESTRING ((" + citiBike.getStartLat() + " " + citiBike.getStartLon() + "), (" + citiBike.getEndLat() + " " + citiBike.getEndLon() + "))");
            features.add(builder.buildFeature(citiBike.getRideId()));
        });

        Map<String, Serializable> params = new HashMap<>();
        DataStore dataStore;
        try {
            params.put("kafka.brokers", "localhost:9092");
            params.put("kafka.zookeepers", "localhost:2181");
            dataStore = DataStoreFinder.getDataStore(params);

            dataStore.createSchema(sft);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
