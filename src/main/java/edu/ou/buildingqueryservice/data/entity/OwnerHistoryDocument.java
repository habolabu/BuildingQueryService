package edu.ou.buildingqueryservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@Document("OwnerHistory")
public class OwnerHistoryDocument implements Serializable {
    @Id
    @JsonIgnore
    private String id;
    @JsonProperty("id")
    private int oId;
    private Date joinDate;
    private Date leaveDate;
    @JsonIgnore
    private Integer ownerId;
    private Integer roomId;

    private Map<String, Object> ownerInfo;
}
