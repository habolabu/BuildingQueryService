package edu.ou.buildingqueryservice.controller.parking;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.parking.ParkingFindDetailRequest;
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
@RequestMapping(EndPoint.Parking.BASE)
public class ParkingFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> parkingFindDetailService;

    /**
     * find detail of exist parking
     *
     * @param parkingSlug slug of parking
     * @return detail of exist parking
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_PARKING)
    @GetMapping(EndPoint.Parking.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailParking(
            @Validated
            @PathVariable
            String parkingSlug
    ) {
        return new ResponseEntity<>(
                parkingFindDetailService.execute(new ParkingFindDetailRequest().setSlug(parkingSlug)),
                HttpStatus.OK
        );
    }
}
