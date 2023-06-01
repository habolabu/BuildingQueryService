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

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OwnerHistoryFindByRoomIdsRepository extends BaseRepository<List<Integer>, List<OwnerHistoryDocument>> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate owner history id
     *
     * @param ownerHistoryIds owner history ids
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(List<Integer> ownerHistoryIds) {
        if (validValidation.isInValid(ownerHistoryIds)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "owner history identities"
            );
        }
    }

    /**
     * Find owner history detail
     *
     * @param roomIds room ids
     * @return owner history detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<OwnerHistoryDocument> doExecute(List<Integer> roomIds) {
        return mongoTemplate.find(
                new Query(
                        Criteria.where("roomId")
                                .in(roomIds)
                ),
                OwnerHistoryDocument.class
        );
    }

    @Override
    protected void postExecute(List<Integer> input) {
        // do nothing
    }
}

