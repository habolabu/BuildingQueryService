package edu.ou.buildingqueryservice.data.pojo.request.parking;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ParkingFindDetailRequest implements IBaseRequest {
    @NotBlank
    private String slug;
}
