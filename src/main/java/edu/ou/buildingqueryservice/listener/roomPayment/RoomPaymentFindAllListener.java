package edu.ou.buildingqueryservice.listener.roomPayment;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ou.buildingqueryservice.data.entity.OwnerHistoryDocument;
import edu.ou.buildingqueryservice.data.entity.PriceTagDocument;
import edu.ou.buildingqueryservice.data.entity.RoomDocument;
import edu.ou.buildingqueryservice.data.pojo.response.roomPayment.RoomPaymentResponse;
import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.payment.external.payment.RoomPaymentFindAllQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RoomPaymentFindAllListener implements IBaseListener<Object, Object> {
    private final IBaseRepository<Query, List<RoomDocument>> roomFindAllRepository;
    private final IBaseRepository<Integer, PriceTagDocument> priceTagFindByIdRepository;
    private final IBaseRepository<Integer, OwnerHistoryDocument> ownerHistoryFindByRoomIdRepository;
    private final ObjectMapper objectMapper;

    /**
     * Get room detail
     *
     * @param input input of task
     * @return list of room detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = RoomPaymentFindAllQueueE.QUEUE)
    public Object execute(Object input) {
        final List<Map> roomPaymentDetails = new ArrayList<>();
        roomFindAllRepository
                .execute(new Query())
                .forEach(roomDocument -> {
                    final PriceTagDocument priceTag =
                            priceTagFindByIdRepository.execute(roomDocument.getPriceTagId());
                    final OwnerHistoryDocument ownerHistory =
                            ownerHistoryFindByRoomIdRepository.execute(roomDocument.getOId());

                    roomPaymentDetails.add(objectMapper.convertValue(
                            new RoomPaymentResponse()
                                    .setRoomId(roomDocument.getOId())
                                    .setJoinDate(ownerHistory.getJoinDate())
                                    .setOwnerId(ownerHistory.getOwnerId())
                                    .setPricePerDay(priceTag.getPricePerDay()),
                            Map.class
                    ));
                });
        return roomPaymentDetails;
    }
}
