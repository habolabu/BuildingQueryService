package edu.ou.buildingqueryservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("Apartment")
public class ApartmentDocument implements Serializable {
    @Id
    @JsonIgnore
    private String id;
    @JsonProperty("id")
    private int oId;
    private String name;
    private String slug;
    private int floorAmount;
    @JsonIgnore
    private int areaId;
}
