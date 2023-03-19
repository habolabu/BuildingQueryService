package edu.ou.buildingqueryservice.listener.parkingType;

import edu.ou.buildingqueryservice.data.entity.ParkingTypeDocument;
import edu.ou.buildingqueryservice.data.entity.PriceTagDocument;
import edu.ou.coreservice.listener.IBaseListener;
import edu.ou.coreservice.queue.building.external.parkingType.ParkingTypeFindPricePerDayByIdQueueE;
import edu.ou.coreservice.repository.base.IBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingTypeFindPricePerDayByIdListener implements IBaseListener<Integer, Object> {
    private final IBaseRepository<Integer, ParkingTypeDocument> parkingTypeFindByIdRepository;
    private final IBaseRepository<Integer, PriceTagDocument> priceTagFindByIdRepository;

    /**
     * Get price per day of parking type
     *
     * @param parkingTypeId parking type id
     * @return price per day
     * @author Nguyen Trung Kien - OU
     */
    @Override
    @RabbitListener(queues = ParkingTypeFindPricePerDayByIdQueueE.QUEUE)
    public Object execute(Integer parkingTypeId) {
        final ParkingTypeDocument parkingType = parkingTypeFindByIdRepository.execute(parkingTypeId);
        final PriceTagDocument priceTag = priceTagFindByIdRepository.execute(parkingType.getPriceTagId());

        return priceTag.getPricePerDay();
    }
}
