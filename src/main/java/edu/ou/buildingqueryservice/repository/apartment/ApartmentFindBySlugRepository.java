package edu.ou.buildingqueryservice.repository.apartment;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ApartmentDocument;
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
public class ApartmentFindBySlugRepository extends BaseRepository<String, ApartmentDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate apartment slug
     *
     * @param apartmentSlug apartment slug
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(String apartmentSlug) {
        if (validValidation.isInValid(apartmentSlug)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "apartment slug"
            );
        }
    }

    /**
     * Find apartment detail
     *
     * @param apartmentSlug apartment slug
     * @return apartment detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected ApartmentDocument doExecute(String apartmentSlug) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("slug")
                                .is(apartmentSlug)
                ),
                ApartmentDocument.class
        );
    }

    @Override
    protected void postExecute(String input) {
        // do nothing
    }
}
