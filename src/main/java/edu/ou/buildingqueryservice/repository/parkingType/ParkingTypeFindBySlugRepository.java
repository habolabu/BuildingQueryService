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

@Repository
@RequiredArgsConstructor
public class ParkingTypeFindBySlugRepository extends BaseRepository<String, ParkingTypeDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate parking type slug
     *
     * @param parkingTypeSlug parking type slug
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(String parkingTypeSlug) {
        if (validValidation.isInValid(parkingTypeSlug)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking type slug"
            );
        }
    }

    /**
     * Find parking type detail
     *
     * @param parkingTypeSlug parking type slug
     * @return parking type detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected ParkingTypeDocument doExecute(String parkingTypeSlug) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("slug")
                                .is(parkingTypeSlug)
                ),
                ParkingTypeDocument.class
        );
    }

    @Override
    protected void postExecute(String input) {
        // do nothing
    }
}
