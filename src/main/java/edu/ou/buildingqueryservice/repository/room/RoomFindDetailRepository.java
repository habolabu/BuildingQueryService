package edu.ou.buildingqueryservice.repository.room;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.RoomDocument;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomFindDetailRepository extends BaseRepository<String, RoomDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate room slug
     *
     * @param roomSlug input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(String roomSlug) {
        if (validValidation.isInValid(roomSlug)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "room slug"
            );
        }
    }

    /**
     * Find room detail
     *
     * @param roomSlug room slug
     * @return room detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected RoomDocument doExecute(String roomSlug) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("slug")
                                .is(roomSlug)
                ),
                RoomDocument.class
        );
    }

    @Override
    protected void postExecute(String input) {

    }
}
