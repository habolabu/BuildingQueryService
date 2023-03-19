package edu.ou.buildingqueryservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("ParkingSpace")
public class ParkingSpaceDocument implements Serializable {
    @Id
    @JsonIgnore
    private String id;
    private Integer availableSpace;
    private Integer capacity;
    private Integer parkingId;
    private Integer parkingTypeId;
    @Transient
    ParkingTypeDocument parkingType;
}
