package edu.ou.buildingqueryservice.controller.parkingType;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.parkingType.ParkingTypeFindAllRequest;
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
@RequestMapping(EndPoint.ParkingType.BASE)
public class ParkingTypeFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> parkingTypeFindAllService;

    /**
     * find all exist parking type
     *
     * @param page page index
     * @param name name of parking type
     * @return list of parking type
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_PARKING_TYPE)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllParkingType(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String name
    ) {
        return new ResponseEntity<>(
                parkingTypeFindAllService.execute(
                        new ParkingTypeFindAllRequest()
                                .setPage(page)
                                .setName(name)
                ),
                HttpStatus.OK
        );
    }
}
