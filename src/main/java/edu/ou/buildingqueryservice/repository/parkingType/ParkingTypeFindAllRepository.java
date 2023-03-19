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

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParkingTypeFindAllRepository extends BaseRepository<Query, List<ParkingTypeDocument>> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate input
     *
     * @param query input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Query query) {
        if (validValidation.isInValid(query)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking type find all query"
            );
        }
    }

    /**
     * Find all parking type
     *
     * @param query filter
     * @return list of parking type
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<ParkingTypeDocument> doExecute(Query query) {
        return mongoTemplate.find(
                query,
                ParkingTypeDocument.class
        );
    }

    @Override
    protected void postExecute(Query input) {
        // do nothing
    }
}
