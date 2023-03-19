package edu.ou.buildingqueryservice.data.pojo.response.roomPayment;

import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RoomPaymentResponse implements IBaseResponse {
    private int roomId;
    private int ownerId;
    private BigDecimal pricePerDay;
    private Date joinDate;
}
