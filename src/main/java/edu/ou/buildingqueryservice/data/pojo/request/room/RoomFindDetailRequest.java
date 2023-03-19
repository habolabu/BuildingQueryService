package edu.ou.buildingqueryservice.data.pojo.request.room;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoomFindDetailRequest implements IBaseRequest {
    @NotBlank
    private String slug;
}
