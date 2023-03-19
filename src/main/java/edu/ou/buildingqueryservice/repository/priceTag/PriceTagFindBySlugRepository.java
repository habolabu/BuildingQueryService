package edu.ou.buildingqueryservice.repository.priceTag;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.PriceTagDocument;
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
public class PriceTagFindBySlugRepository extends BaseRepository<String, PriceTagDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate price tag slug
     *
     * @param priceTagSlug price tag slug
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(String priceTagSlug) {
        if (validValidation.isInValid(priceTagSlug)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "price tag slug"
            );
        }
    }

    /**
     * Find price tag by slug
     *
     * @param priceTagSlug price tag slug
     * @return price tag document
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected PriceTagDocument doExecute(String priceTagSlug) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("slug")
                                .is(priceTagSlug)
                ),
                PriceTagDocument.class
        );
    }

    @Override
    protected void postExecute(String areaSlug) {
        // do nothing
    }
}
