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

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomFindByApartmentIdRepository extends BaseRepository<Integer, List<RoomDocument>> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate input
     *
     * @param apartmentId input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer apartmentId) {
        if (validValidation.isInValid(apartmentId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "apartment id"
            );
        }
    }

    /**
     * Find all room
     *
     * @param apartmentId apartmentId
     * @return list of room
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<RoomDocument> doExecute(Integer apartmentId) {
        return mongoTemplate.find(
                new Query(
                        Criteria
                                .where("apartmentId")
                                .is(apartmentId)
                ),
                RoomDocument.class
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
