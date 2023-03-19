package edu.ou.buildingqueryservice.data.pojo.request.parking;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

@Data
public class ParkingFindAllRequest implements IBaseRequest {
    private int apartmentId;
}
