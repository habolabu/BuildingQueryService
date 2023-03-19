package edu.ou.buildingqueryservice.controller.parkingSpace;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.parkingSpace.ParkingSpaceFindAllWithParamsRequest;
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
@RequestMapping(EndPoint.ParkingSpace.BASE)
public class ParkingSpaceFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> parkingSpaceFindAllService;

    /**
     * find all exist parking space of parking
     *
     * @param parkingId       parking id
     * @param page            page index
     * @param bCapacity       capacity begin range
     * @param eCapacity       capacity end range
     * @param bAvailableSpace available space begin range
     * @param eAvailableSpace available space end range
     * @return list of parking space
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_PARKING_SPACE)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllParkingSpace(
            @RequestParam Integer parkingId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) Integer bCapacity,
            @RequestParam(required = false) Integer eCapacity,
            @RequestParam(required = false) Integer bAvailableSpace,
            @RequestParam(required = false) Integer eAvailableSpace
    ) {

        return new ResponseEntity<>(
                parkingSpaceFindAllService.execute(
                        new ParkingSpaceFindAllWithParamsRequest()
                                .setParkingId(parkingId)
                                .setPage(page)
                                .setBCapacity(bCapacity)
                                .setECapacity(eCapacity)
                                .setBAvailableSpace(bAvailableSpace)
                                .setEAvailableSpace(eAvailableSpace)
                ),
                HttpStatus.OK
        );
    }
}
