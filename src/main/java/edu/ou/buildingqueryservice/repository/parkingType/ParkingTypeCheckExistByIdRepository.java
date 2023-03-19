package edu.ou.buildingqueryservice.repository.parkingType;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ParkingTypeDocument;
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
public class ParkingTypeCheckExistByIdRepository extends BaseRepository<Integer, Boolean> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate input
     *
     * @param parkingTypeId parking type id
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer parkingTypeId) {
        if (validValidation.isInValid(parkingTypeId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking type identity"
            );
        }
    }

    /**
     * Check parking type exist
     *
     * @param parkingTypeId parking type id
     * @return exist or not
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected Boolean doExecute(Integer parkingTypeId) {
        return Objects.nonNull(
                mongoTemplate.findOne(
                        new Query(
                                Criteria.where("oId")
                                        .is(parkingTypeId)
                        ),
                        ParkingTypeDocument.class
                )
        );
    }

    @Override
    protected void postExecute(Integer input) {

    }
}
