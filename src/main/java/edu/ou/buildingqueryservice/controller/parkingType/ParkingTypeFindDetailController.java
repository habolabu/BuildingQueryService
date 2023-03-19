package edu.ou.buildingqueryservice.controller.parkingType;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.parkingType.ParkingTypeFindDetailRequest;
import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.ParkingType.BASE)
public class ParkingTypeFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> parkingTypeFindDetailService;

    /**
     * find detail of exist parking type
     *
     * @param parkingTypeSlug slug of parking type
     * @return detail of exist parking type
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_PARKING_TYPE)
    @GetMapping(EndPoint.ParkingType.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailParkingType(
            @Validated
            @PathVariable
            String parkingTypeSlug
    ) {
        return new ResponseEntity<>(
                parkingTypeFindDetailService.execute(new ParkingTypeFindDetailRequest().setSlug(parkingTypeSlug)),
                HttpStatus.OK
        );
    }
}
