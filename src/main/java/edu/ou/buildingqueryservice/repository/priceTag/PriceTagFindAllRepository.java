package edu.ou.buildingqueryservice.repository.priceTag;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.PriceTagDocument;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.repository.base.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PriceTagFindAllRepository extends BaseRepository<Query, List<PriceTagDocument>> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate input
     *
     * @param query input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(Query query) {
        if (validValidation.isInValid(query)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "price tag find all query"
            );
        }
    }


    /**
     * Find all price tag
     *
     * @param query query
     * @return list of price tag
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected List<PriceTagDocument> doExecute(Query query) {
        return mongoTemplate.find(
                query,
                PriceTagDocument.class
        );
    }

    @Override
    protected void postExecute(Query params) {
        // do nothing
    }
}
