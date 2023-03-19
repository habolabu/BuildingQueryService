package edu.ou.buildingqueryservice.repository.parking;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ParkingDocument;
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
public class ParkingCheckExistByIdRepository extends BaseRepository<Integer, Boolean> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate parking id
     *
     * @param parkingId parking id
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer parkingId) {
        if (validValidation.isInValid(parkingId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking identity"
            );
        }
    }

    /**
     * Check parking exist
     *
     * @param parkingId parking id
     * @return exist or not
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected Boolean doExecute(Integer parkingId) {
        return Objects.nonNull(
                mongoTemplate.findOne(
                        new Query(
                                Criteria.where("oId")
                                        .is(parkingId)
                        ),
                        ParkingDocument.class
                )
        );
    }

    @Override
    protected void postExecute(Integer input) {

    }
}
