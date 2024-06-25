package com.bscllc.citibike;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class CitiBike {
    /*
 ride_id,rideable_type,started_at,ended_at,start_station_name,start_station_id,end_station_name,end_station_id,
 start_lat,start_lng,end_lat,end_lng,member_casual

  */
    @CsvBindByName(column="ride_id")
    @JsonProperty
    private String rideId;
    @CsvBindByName(column="rideable_type")
    @JsonProperty
    private String rideableType;

    @CsvDate(value="yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column="started_at")
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startedAt;
    @CsvDate(value="yyyy-MM-dd HH:mm:ss")
    @CsvBindByName(column="ended_at")
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endedAt;
    @CsvBindByName(column="start_station_name")
    @JsonProperty
    private String startStationName;
    @CsvBindByName(column="start_station_id")
    @JsonProperty
    private String startStationId;
    @CsvBindByName(column="end_station_name")
    @JsonProperty
    private String endStationName;
    @CsvBindByName(column="end_station_id")
    @JsonProperty
    private String endStationId;
    @CsvBindByName(column="start_lat")
    @JsonProperty
    private Double startLat;
    @CsvBindByName(column="start_lng")
    @JsonProperty
    private Double startLon;
    @CsvBindByName(column="end_lat")
    @JsonProperty
    private Double endLat;
    @CsvBindByName(column="end_lng")
    @JsonProperty
    private Double endLon;
    @CsvBindByName(column="member_casual")
    @JsonProperty
    private String memberCasual;

    public CitiBike() {}

    public String getRideId() {
        return rideId;
    }

    public String getRideableType() {
        return rideableType;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public String getStartStationId() {
        return startStationId;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public String getEndStationId() {
        return endStationId;
    }

    public Double getStartLat() {
        return startLat;
    }

    public Double getStartLon() {
        return startLon;
    }

    public Double getEndLat() {
        return endLat;
    }

    public Double getEndLon() {
        return endLon;
    }

    public String getMemberCasual() {
        return memberCasual;
    }

    public static String getSftAttributes() {
        StringBuilder sb = new StringBuilder();
        sb.append("rideId:String");
        sb.append(", rideableType:String");
        sb.append(", startedAt:Date");
        sb.append(", endedAt:Date");
        sb.append(", startStationName:String");
        sb.append(", startStationId:String");
        sb.append(", endStationName:String");
        sb.append(", endStationId:String");
        sb.append(", startLat:Double");
        sb.append(", startLon:Double");
        sb.append(", endLat:Double");
        sb.append(", endLon:Double");
        sb.append(", memberCasual:String");
        sb.append(", dtg:Date");
        sb.append(", *geom:LineString:srid=4326");
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CitiBike{");
        sb.append("rideId='").append(rideId).append('\'');
        sb.append(", rideableType='").append(rideableType).append('\'');
        sb.append(", startedAt=").append(startedAt);
        sb.append(", endedAt=").append(endedAt);
        sb.append(", startStationName='").append(startStationName).append('\'');
        sb.append(", startStationId='").append(startStationId).append('\'');
        sb.append(", endStationName='").append(endStationName).append('\'');
        sb.append(", endStationId='").append(endStationId).append('\'');
        sb.append(", startLat=").append(startLat);
        sb.append(", startLon=").append(startLon);
        sb.append(", endLat=").append(endLat);
        sb.append(", endLon=").append(endLon);
        sb.append(", memberCasual='").append(memberCasual).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
