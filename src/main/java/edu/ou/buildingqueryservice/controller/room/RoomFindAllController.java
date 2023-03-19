package edu.ou.buildingqueryservice.controller.room;

import edu.ou.buildingqueryservice.common.constant.EndPoint;
import edu.ou.buildingqueryservice.data.pojo.request.room.RoomFindAllRequest;
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
@RequestMapping(EndPoint.Room.BASE)
public class RoomFindAllController {
    private final IBaseService<IBaseRequest, IBaseResponse> roomFindAllService;

    /**
     * find all exist room
     *
     * @param apartmentId  apartment id
     * @param page         page index
     * @param name         room name
     * @param bFloorNumber floor number begin index
     * @param eFloorNumber floor number end index
     * @return room list
     * @author Nguyen Trung Kien - OU
     */
    @PreAuthorize(SecurityPermission.VIEW_ROOM)
    @GetMapping()
    public ResponseEntity<IBaseResponse> getAllRoom(
            @RequestParam Integer apartmentId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer bFloorNumber,
            @RequestParam(required = false) Integer eFloorNumber
    ) {
        return new ResponseEntity<>(
                roomFindAllService.execute(
                        new RoomFindAllRequest()
                                .setApartmentId(apartmentId)
                                .setPage(page)
                                .setName(name)
                                .setBFloorNumber(bFloorNumber)
                                .setEFloorNumber(eFloorNumber)
                ),
                HttpStatus.OK
        );
    }
}
