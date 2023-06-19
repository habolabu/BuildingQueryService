package edu.ou.buildingqueryservice.service.area;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.AreaDocument;
import edu.ou.buildingqueryservice.data.pojo.request.area.AreaFindAllRequest;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.util.PaginationUtils;
import edu.ou.coreservice.common.util.SlugUtils;
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
public class AreaFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<AreaDocument>> areaFindAllRepository;
    private final IBaseRepository<Query, Integer> areaGetPageAmountRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request input of task
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, AreaFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "area parameters"
            );
        }
    }

    /**
     * Find all area
     *
     * @param request request
     * @return area list
     * v
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final AreaFindAllRequest areaFindAllRequest = (AreaFindAllRequest) request;

        final Query query = this.filter(areaFindAllRequest);

        final List<AreaDocument> areaDocuments = areaFindAllRepository.execute(query);
        final int pageAmount = areaGetPageAmountRepository.execute(query.skip(0).limit(0));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", areaDocuments,
                                "totalPage", PaginationUtils.getPageAmount(pageAmount)
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
    private Query filter(AreaFindAllRequest request) {
        final Query query = new Query();

        if (Objects.nonNull(request.getName())) {
            query.addCriteria(
                    Criteria.where("slug")
                            .regex(
                                    SlugUtils.createSlug(request.getName()),
                                    "i"
                            )
            );
        }

        if (Objects.nonNull(request.getAddress())) {
            query.addCriteria(
                    Criteria.where("address")
                            .regex(
                                    request.getAddress(),
                                    "i"
                            )
            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());
        return query;
    }
}
