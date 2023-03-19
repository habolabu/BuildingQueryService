package edu.ou.buildingqueryservice.data.pojo.request.parkingSpace;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

@Data
public class ParkingSpaceFindAllRequest implements IBaseRequest {
    private int parkingId;
}
