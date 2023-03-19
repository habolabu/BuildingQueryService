package edu.ou.buildingqueryservice.listener.parking;

import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.building.external.parking.ParkingCheckExistByIdQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingCheckExistByIdListener implements IBaseListener<Integer, Object> {
    private final IBaseRepository<Integer, Boolean> parkingCheckExistByIdRepository;

    /**
     * Check parking exist by id
     *
     * @param parkingId parkingId
     * @return room list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = ParkingCheckExistByIdQueueE.QUEUE)
    public Object execute(Integer parkingId) {
        return parkingCheckExistByIdRepository.execute(parkingId);
    }
}
