package edu.ou.buildingqueryservice.service.room;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.OwnerHistoryDocument;
import edu.ou.buildingqueryservice.data.entity.PriceTagDocument;
import edu.ou.buildingqueryservice.data.entity.RoomDocument;
import edu.ou.buildingqueryservice.data.pojo.request.room.RoomFindDetailRequest;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
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
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<String, RoomDocument> roomFindBySlugRepository;
    private final IBaseRepository<Integer, OwnerHistoryDocument> ownerHistoryFindByRoomIdRepository;
    private final IBaseRepository<Integer, PriceTagDocument> priceTagFindByIdRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ValidValidation validValidation;

    /**
     * validate room detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, RoomFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "room parameters"
            );
        }
    }

    /**
     * Find room detail
     *
     * @param request request
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final RoomFindDetailRequest roomFindDetailRequest = (RoomFindDetailRequest) request;

        final RoomDocument roomDocument = roomFindBySlugRepository.execute(roomFindDetailRequest.getSlug());

        if (Objects.isNull(roomDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "room",
                    "room slug",
                    roomFindDetailRequest.getSlug()
            );
        }
        final OwnerHistoryDocument ownerHistory = ownerHistoryFindByRoomIdRepository.execute(roomDocument.getOId());
        if (Objects.nonNull(ownerHistory)) {
            final Object owner = rabbitTemplate.convertSendAndReceive(
                    UserFindDetailByIdQueueE.EXCHANGE,
                    UserFindDetailByIdQueueE.ROUTING_KEY,
                    ownerHistory.getOwnerId());

            ownerHistory.setOwnerInfo((Map<String, Object>) owner);

            roomDocument.setOwner(ownerHistory);
        }
        final PriceTagDocument priceTag = priceTagFindByIdRepository.execute(roomDocument.getPriceTagId());
        roomDocument.setPrice(priceTag);

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        roomDocument,
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest input) {
        // do nothing
    }
}
