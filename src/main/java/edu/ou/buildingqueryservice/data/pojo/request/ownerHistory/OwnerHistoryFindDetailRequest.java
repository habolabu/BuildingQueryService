package edu.ou.buildingqueryservice.data.pojo.request.ownerHistory;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

@Data
public class OwnerHistoryFindDetailRequest implements IBaseRequest {

    private int id;
}
