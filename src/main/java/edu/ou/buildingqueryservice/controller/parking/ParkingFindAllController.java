package edu.ou.buildingqueryservice.controller.parking;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.parking.ParkingFindAllWithParamsRequest;
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
@RequestMapping(EndPoint.Parking.BASE)
public class ParkingFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> parkingFindAllService;

    /**
     * find all exist parking
     *
     * @param apartmentId apartment id
     * @param page        page index
     * @param name        name of parking
     * @return list of parking
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_PARKING)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllParking(
            @RequestParam Integer apartmentId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String name

    ) {
        return new ResponseEntity<>(
                parkingFindAllService.execute(
                        new ParkingFindAllWithParamsRequest()
                                .setApartmentId(apartmentId)
                                .setPage(page)
                                .setName(name)
                ),
                HttpStatus.OK
        );
    }
}
