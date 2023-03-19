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
public class PriceTagFindByIdRepository extends BaseRepository<Integer, PriceTagDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate price tag id
     *
     * @param priceTagId price tag id
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Integer priceTagId) {
        if (validValidation.isInValid(priceTagId)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "price tag identity"
            );
        }
    }

    /**
     * Find price tag by id
     *
     * @param priceTagId price tag id
     * @return price tag document
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected PriceTagDocument doExecute(Integer priceTagId) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("oId")
                                .is(priceTagId)
                ),
                PriceTagDocument.class
        );
    }

    @Override
    protected void postExecute(Integer input) {
        // do nothing
    }
}
