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

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class RoomCheckExistByIdRepository extends BaseRepository<Integer, Boolean> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate input
     *
     * @param roomId input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer roomId) {
        if (validValidation.isInValid(roomId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "room identity"
            );
        }
    }

    /**
     * Check room exist
     *
     * @param roomId room id
     * @return room detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected Boolean doExecute(Integer roomId) {
        return Objects.nonNull(
                mongoTemplate.findOne(
                        new Query(
                                Criteria.where("oId")
                                        .is(roomId)
                        ),
                        RoomDocument.class
                )
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
