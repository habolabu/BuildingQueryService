package edu.ou.buildingqueryservice.service.room;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.OwnerHistoryDocument;
import edu.ou.buildingqueryservice.data.entity.RoomDocument;
import edu.ou.buildingqueryservice.data.pojo.request.room.RoomFindAllRequest;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.util.PaginationUtils;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.queue.human.external.user.UserFindDetailByIdQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<RoomDocument>> roomFindAllRepository;
    private final IBaseRepository<Query, Integer> roomGetPageAmountRepository;
    private final IBaseRepository<Integer, OwnerHistoryDocument> ownerHistoryFindByRoomIdRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, RoomFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "room parameters"
            );
        }
    }

    /**
     * Find all room
     *
     * @param request request
     * @return room list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final RoomFindAllRequest roomFindAllWithParamsRequest = (RoomFindAllRequest) request;

        final Query query = this.filter(roomFindAllWithParamsRequest);

        final List<RoomDocument> roomDocuments = roomFindAllRepository.execute(query);

        final int pageAmount = roomGetPageAmountRepository.execute(query.skip(0).limit(0));
        roomDocuments.forEach(roomDocument -> {
            final OwnerHistoryDocument ownerHistory = ownerHistoryFindByRoomIdRepository.execute(roomDocument.getOId());
            if(Objects.isNull(ownerHistory)){
                roomDocument.setOwner(null);
            }
            final Object owner = rabbitTemplate.convertSendAndReceive(
                    UserFindDetailByIdQueueE.EXCHANGE,
                    UserFindDetailByIdQueueE.ROUTING_KEY,
                    ownerHistory.getOwnerId());

            ownerHistory.setOwnerInfo((Map<String, Object>) owner);

            roomDocument.setOwner(ownerHistory);
        });

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", roomDocuments,
                                "totalPage", PaginationUtils.getPageAmount(pageAmount)
                        ),
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest input) {
        // do nothing
    }

    /**
     * Filter response
     *
     * @param request request params
     * @author Nguyen Trung Kien - OU
     */
    private Query filter(RoomFindAllRequest request) {
        final Query query = new Query();

        if (Objects.nonNull(request.getApartmentId())) {
            query.addCriteria(
                    Criteria.where("apartmentId")
                            .is(request.getApartmentId()));
        }

        if (Objects.nonNull(request.getName())) {
            query.addCriteria(
                    Criteria.where("name")
                            .regex(
                                    request.getName(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getBFloorNumber())
                && Objects.nonNull(request.getEFloorNumber())) {
            query.addCriteria(
                    Criteria.where("floorNumber")
                            .gte(request.getBFloorNumber())
                            .lte(request.getEFloorNumber())
            );

        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}