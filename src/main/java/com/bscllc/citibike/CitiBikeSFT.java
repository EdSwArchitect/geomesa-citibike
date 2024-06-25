package com.bscllc.citibike;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.locationtech.geomesa.utils.interop.SimpleFeatureTypes;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CitiBikeSFT {
    public CitiBikeSFT() {

    }

    public SimpleFeatureType getSimpleFeatureType() {
        SimpleFeatureType sft = SimpleFeatureTypes.createType("CitiBike", CitiBike.getSftAttributes());
        sft.getUserData().put(SimpleFeatureTypes.DEFAULT_DATE_KEY, "dtg");
        sft.getDescriptor("rideId").getUserData().put("index", "true");
        sft.getDescriptor("startStationId").getUserData().put("index", "true");
        sft.getDescriptor("endStationId").getUserData().put("index", "true");
        return sft;
    }

    public List<CitiBike> getList(String filePath) {
        ArrayList<CitiBike> list = new ArrayList<>();

        Path path = FileSystems.getDefault().getPath(filePath);

        try (
                Reader reader = Files.newBufferedReader(path)
        ) {
            CsvToBean<CitiBike> cb = new CsvToBeanBuilder<CitiBike>(reader)
                    .withType(CitiBike.class)
                    .build();

            cb.stream().iterator().forEachRemaining(list::add);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        return list;
    }

    /*
    public class TDriveData implements TutorialData {
    private SimpleFeatureType sft = null;
    private List<SimpleFeature> features = null;
    private List<Query> queries = null;
    private Filter subsetFilter = null;

    public TDriveData() {
    }

    public String getTypeName() {
        return "tdrive-quickstart";
    }

    public SimpleFeatureType getSimpleFeatureType() {
        if (this.sft == null) {
            StringBuilder attributes = new StringBuilder();
            attributes.append("taxiId:String,");
            attributes.append("dtg:Date,");
            attributes.append("*geom:Point:srid=4326");
            this.sft = SimpleFeatureTypes.createType(this.getTypeName(), attributes.toString());
            this.sft.getUserData().put(SimpleFeatureTypes.DEFAULT_DATE_KEY, "dtg");
        }

        return this.sft;
    }

    public List<SimpleFeature> getTestData() {
        if (this.features == null) {
            List<SimpleFeature> features = new ArrayList();
            URL input = this.getClass().getClassLoader().getResource("1277-reduced.txt");
            if (input == null) {
                throw new RuntimeException("Couldn't load resource 1277-reduced.txt");
            }

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
            SimpleFeatureBuilder builder = new SimpleFeatureBuilder(this.getSimpleFeatureType());

            try {
                CSVParser parser = CSVParser.parse(input, StandardCharsets.UTF_8, CSVFormat.DEFAULT);

                try {
                    Iterator var6 = parser.iterator();

                    while(var6.hasNext()) {
                        CSVRecord record = (CSVRecord)var6.next();
                        builder.set("taxiId", record.get(0));
                        builder.set("dtg", Date.from(LocalDateTime.parse(record.get(1), dateFormat).toInstant(ZoneOffset.UTC)));
                        double longitude = Double.parseDouble(record.get(2));
                        double latitude = Double.parseDouble(record.get(3));
                        builder.set("geom", "POINT (" + longitude + " " + latitude + ")");
                        builder.featureUserData(Hints.USE_PROVIDED_FID, Boolean.TRUE);
                        features.add(builder.buildFeature(record.get(0)));
                    }
                } catch (Throwable var13) {
                    if (parser != null) {
                        try {
                            parser.close();
                        } catch (Throwable var12) {
                            var13.addSuppressed(var12);
                        }
                    }

                    throw var13;
                }

                if (parser != null) {
                    parser.close();
                }
            } catch (IOException var14) {
                IOException e = var14;
                throw new RuntimeException("Error reading t-drive data:", e);
            }

            this.features = Collections.unmodifiableList(features);
        }

        return this.features;
    }

    public List<Query> getTestQueries() {
        if (this.queries == null) {
            List<Query> queries = new ArrayList();
            queries.add(new Query(this.getTypeName(), Filter.INCLUDE));
            this.queries = Collections.unmodifiableList(queries);
        }

        return this.queries;
    }

    public Filter getSubsetFilter() {
        return Filter.INCLUDE;
    }

     */

}
