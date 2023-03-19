package edu.ou.buildingqueryservice.repository.ownerHistory;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.OwnerHistoryDocument;
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
public class OwnerHistoryFindByRoomIdRepository extends BaseRepository<Integer, OwnerHistoryDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate owner history id
     *
     * @param ownerHistoryId owner history id
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer ownerHistoryId) {
        if (validValidation.isInValid(ownerHistoryId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "owner history identity"
            );
        }
    }

    /**
     * Find owner history detail
     *
     * @param roomId room id
     * @return owner history detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected OwnerHistoryDocument doExecute(Integer roomId) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("roomId")
                                .is(roomId)
                ),
                OwnerHistoryDocument.class
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
