package edu.ou.buildingqueryservice.listener.parkingType;

import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.building.external.parkingType.ParkingTypeCheckExistByIdQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingTypeCheckExistByIdListener implements IBaseListener<Integer, Object> {
    private final IBaseRepository<Integer, Boolean> parkingTypeCheckExistByIdRepository;


    /**
     * Check parking exist by id
     *
     * @param parkingTypeId parkingTypeId
     * @return room list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = ParkingTypeCheckExistByIdQueueE.QUEUE)
    public Object execute(Integer parkingTypeId) {
        return parkingTypeCheckExistByIdRepository.execute(parkingTypeId);
    }
}
