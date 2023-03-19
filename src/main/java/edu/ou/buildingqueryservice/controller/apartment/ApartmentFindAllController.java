package edu.ou.buildingqueryservice.controller.apartment;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.apartment.ApartmentFindAllWithParamsRequest;
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
@RequestMapping(EndPoint.Apartment.BASE)
public class ApartmentFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> apartmentFindAllService;

    /**
     * find all exist apartment
     *
     * @param areaId       area id
     * @param page         page
     * @param name         name of apartment
     * @param bFloorAmount begin floor amount
     * @param eFloorAmount end floor amount
     * @return list of apartment
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_APARTMENT)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllApartment(
            @RequestParam Integer areaId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer bFloorAmount,
            @RequestParam(required = false) Integer eFloorAmount
    ) {
        return new ResponseEntity<>(
                apartmentFindAllService.execute(
                        new ApartmentFindAllWithParamsRequest()
                                .setAreaId(areaId)
                                .setPage(page)
                                .setName(name)
                                .setBFloorAmount(bFloorAmount)
                                .setEFloorAmount(eFloorAmount)
                ),
                HttpStatus.OK
        );
    }
}
