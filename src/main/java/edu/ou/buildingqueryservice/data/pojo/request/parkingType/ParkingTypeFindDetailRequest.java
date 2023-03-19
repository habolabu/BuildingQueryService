package edu.ou.buildingqueryservice.data.pojo.request.parkingType;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ParkingTypeFindDetailRequest implements IBaseRequest {
    @NotBlank
    private String slug;
}
