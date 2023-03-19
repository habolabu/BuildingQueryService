package edu.ou.buildingqueryservice.controller.area;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.area.AreaFindAllRequest;
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
@RequestMapping(EndPoint.Area.BASE)
public class AreaFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> areaFindAllService;

    /**
     * find all exist area
     *
     * @param page    page index
     * @param name    area name
     * @param address area address
     * @return list of area
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_AREA)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllArea(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address
    ) {
        return new ResponseEntity<>(
                areaFindAllService.execute(
                        new AreaFindAllRequest()
                                .setPage(page)
                                .setName(name)
                                .setAddress(address)),
                HttpStatus.OK
        );
    }
}
