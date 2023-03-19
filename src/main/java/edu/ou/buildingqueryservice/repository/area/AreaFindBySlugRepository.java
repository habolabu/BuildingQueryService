package edu.ou.buildingqueryservice.repository.area;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.AreaDocument;
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
public class AreaFindBySlugRepository extends BaseRepository<String, AreaDocument> {
    private final MongoTemplate mongoTemplate;
    private final ValidValidation validValidation;

    /**
     * Validate area slug
     *
     * @param areaSlug area slug
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(String areaSlug) {
        if (validValidation.isInValid(areaSlug)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "area slug"
            );
        }
    }

    /**
     * Find area detail
     *
     * @param areaDocument area slug
     * @return area detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected AreaDocument doExecute(String areaDocument) {
        return mongoTemplate.findOne(
                new Query(
                        Criteria.where("slug")
                                .is(areaDocument)
                ),
                AreaDocument.class
        );
    }

    @Override
    protected void postExecute(String input) {
        // do nothing
    }
}
