package edu.ou.buildingqueryservice.controller.area;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.area.AreaFindDetailRequest;
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
@RequestMapping(EndPoint.Area.BASE)
public class AreaFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> areaFindDetailService;

    /**
     * find detail of exist area
     *
     * @param areaSlug slug of area
     * @return detail of exist area
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_AREA)
    @GetMapping(EndPoint.Area.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailArea(
            @NotNull
            @PathVariable
            String areaSlug
    ) {
        return new ResponseEntity<>(
                areaFindDetailService.execute(new AreaFindDetailRequest().setSlug(areaSlug)),
                HttpStatus.OK
        );
    }
}
