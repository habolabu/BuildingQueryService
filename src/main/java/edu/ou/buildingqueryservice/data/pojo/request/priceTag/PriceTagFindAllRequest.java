package edu.ou.buildingqueryservice.data.pojo.request.priceTag;

import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PriceTagFindAllRequest implements IBaseRequest {
    @NotNull
    @Min(
            value = 1,
            message = "The value must be greater than or equals to 1"
    )
    private Integer page;
    private String name;
    private Integer bPricePerDay;
    private Integer ePricePerDay;
}
