package edu.ou.buildingqueryservice.controller.parkingType;

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
@RequestMapping(EndPoint.ParkingType.BASE)
public class ParkingTypeFindAllWithoutPaginationController {
    private final IBaseService<IBaseRequest, IBaseResponse> parkingTypeFindAllWithoutPaginationService;

    /**
     * find all exist parking type without pagination
     *
     * @return list of parking type
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_PARKING_TYPE)
    @GetMapping(EndPoint.ParkingType.ALL)
    public ResponseEntity<IBaseResponse> getAllParkingTypeWithOutPagination() {
        return new ResponseEntity<>(
                parkingTypeFindAllWithoutPaginationService.execute(null),
                HttpStatus.OK
        );
    }
}
