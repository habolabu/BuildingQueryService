package edu.ou.buildingqueryservice.controller.priceTag;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.PriceTag.BASE)
public class PriceTagFindAllWithoutPaginationController {
    private final IBaseService<IBaseRequest, IBaseResponse> priceTagFindAllWithoutPaginationService;


    /**
     * Find all price tag with out pagination
     *
     * @return list of price tag
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_PRICE_TAG)
    @GetMapping(EndPoint.PriceTag.ALL)
    public ResponseEntity<IBaseResponse> getAllPriceTagWithoutPagination() {
        return new ResponseEntity<>(
                priceTagFindAllWithoutPaginationService.execute(null),
                HttpStatus.OK
        );
    }
}
