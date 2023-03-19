package edu.ou.buildingqueryservice.listener.room;

import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.building.external.room.RoomCheckExistByIdQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomCheckExistByIdListener implements IBaseListener<Integer, Object> {
    private final IBaseRepository<Integer, Boolean> roomCheckExistByIdRepository;

    /**
     * Check room exist by id
     *
     * @param roomId roomId
     * @return room list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = RoomCheckExistByIdQueueE.QUEUE)
    public Object execute(Integer roomId) {
        return roomCheckExistByIdRepository.execute(roomId);
    }
}
