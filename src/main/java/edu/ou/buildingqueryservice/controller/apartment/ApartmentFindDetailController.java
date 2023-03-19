package edu.ou.buildingqueryservice.controller.apartment;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.apartment.ApartmentFindDetailRequest;
import edu.ou.coreservice.common.constant.SecurityPermission;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.service.base.IBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoint.Apartment.BASE)
public class ApartmentFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> apartmentFindDetailService;

    /**
     * find detail of exist apartment
     *
     * @param apartmentSlug slug of apartment
     * @return detail of exist apartment
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_APARTMENT)
    @GetMapping(EndPoint.Apartment.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailApartment(
            @NotNull
            @PathVariable
            String apartmentSlug
    ) {

        return new ResponseEntity<>(
                apartmentFindDetailService.execute(new ApartmentFindDetailRequest().setSlug(apartmentSlug)),
                HttpStatus.OK
        );
    }
}
