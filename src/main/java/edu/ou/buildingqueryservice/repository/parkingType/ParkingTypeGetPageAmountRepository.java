package edu.ou.buildingqueryservice.repository.parkingType;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ParkingTypeDocument;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParkingTypeGetPageAmountRepository extends BaseRepository<Query, Integer> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate query
     *
     * @param query query
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Query query) {
        if (validValidation.isInValid(query)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking type get page amount query"
            );
        }
    }

    /**
     * Get page amount
     *
     * @param query query
     * @return page amount
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected Integer doExecute(Query query) {
        return Math.toIntExact(
                mongoTemplate.count(
                        query,
                        ParkingTypeDocument.class
                )
        );
    }

    @Override
    protected void postExecute(Query input) {
        // do nothing
    }
}
