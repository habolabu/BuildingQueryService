package edu.ou.buildingqueryservice.controller.room;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.room.RoomFindDetailRequest;
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
@RequestMapping(EndPoint.Room.BASE)
public class RoomFindDetailController {
    private final IBaseService<IBaseRequest, IBaseResponse> roomFindDetailService;

    /**
     * find detail of exist room
     *
     * @param roomSlug slug of room
     * @return detail of exist room
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_ROOM)
    @GetMapping(EndPoint.Room.DETAIL)
    public ResponseEntity<IBaseResponse> findDetailArea(
            @Validated
            @PathVariable
            String roomSlug
    ) {
        return new ResponseEntity<>(
                roomFindDetailService.execute(new RoomFindDetailRequest().setSlug(roomSlug)),
                HttpStatus.OK
        );
    }
}
