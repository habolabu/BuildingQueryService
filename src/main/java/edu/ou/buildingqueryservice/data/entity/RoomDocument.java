package edu.ou.buildingqueryservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("Room")
public class RoomDocument implements Serializable {
    @Id
    @JsonIgnore
    private String id;
    @JsonProperty("id")
    private int oId;
    private String name;
    private String slug;
    private int floorNumber;
    private int apartmentId;
    @JsonIgnore
    private int priceTagId;
    @Transient
    private PriceTagDocument price;
    @Transient
    private OwnerHistoryDocument owner;
}
