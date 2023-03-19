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

@Repository
@RequiredArgsConstructor
public class ParkingFindBySlugRepository extends BaseRepository<String, ParkingDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate parking slug
     *
     * @param parkingSlug parking slug
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(String parkingSlug) {
        if (validValidation.isInValid(parkingSlug)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking slug"
            );
        }
    }

    /**
     * Find parking detail
     *
     * @param parkingSlug parking slug
     * @return parking detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected ParkingDocument doExecute(String parkingSlug) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("slug")
                                .is(parkingSlug)
                ),
                ParkingDocument.class
        );
    }

    @Override
    protected void postExecute(String input) {
        // do nothing
    }
}
