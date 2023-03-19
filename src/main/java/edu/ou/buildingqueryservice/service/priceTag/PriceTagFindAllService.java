package edu.ou.buildingqueryservice.service.priceTag;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.PriceTagDocument;
import edu.ou.buildingqueryservice.data.pojo.request.priceTag.PriceTagFindAllRequest;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.util.PaginationUtils;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PriceTagFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<PriceTagDocument>> priceTagFindAllRepository;
    private final IBaseRepository<Query, Integer> priceTagGetPageAmountRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, PriceTagFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "price tag parameters"
            );
        }
    }

    /**
     * Find all price tag
     *
     * @param request request
     * @return price tag list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final PriceTagFindAllRequest priceTagFindAllRequest = (PriceTagFindAllRequest) request;

        final Query query = this.filter(priceTagFindAllRequest);

        final List<PriceTagDocument> priceTagDocuments = priceTagFindAllRepository.execute(query);
        final int pageAmount = priceTagGetPageAmountRepository.execute(query.skip(0).limit(0));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", priceTagDocuments,
                                "totalPage", pageAmount
                        ),
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest input) {
        // do nothing
    }

    /**
     * Filter response
     *
     * @param request request params
     * @author Nguyen Trung Kien - OU
     */
    private Query filter(PriceTagFindAllRequest request) {
        final Query query = new Query();
        if (Objects.nonNull(request.getName())) {
            query.addCriteria(
                    Criteria.where("name")
                            .regex(
                                    request.getName(),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getBPricePerDay())
                && Objects.nonNull(request.getEPricePerDay())) {
            query.addCriteria(
                    Criteria.where("pricePerDay")
                            .gte(request.getBPricePerDay())
                            .lte(request.getEPricePerDay())

            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
