package edu.ou.buildingqueryservice.controller.priceTag;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.priceTag.PriceTagFindAllRequest;
import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.PriceTag.BASE)
public class PriceTagFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> priceTagFindAllService;


    /**
     * Find all price tag
     *
     * @param page         page index
     * @param name         name of price tag
     * @param bPricePerDay begin price per day
     * @param ePricePerDay end price per day
     * @return list of price tag
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_PRICE_TAG)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllPriceTag(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer bPricePerDay,
            @RequestParam(required = false) Integer ePricePerDay
    ) {
        return new ResponseEntity<>(
                priceTagFindAllService.execute(
                        new PriceTagFindAllRequest()
                                .setPage(page)
                                .setName(name)
                                .setBPricePerDay(bPricePerDay)
                                .setEPricePerDay(ePricePerDay)
                ),
                HttpStatus.OK
        );
    }
}
