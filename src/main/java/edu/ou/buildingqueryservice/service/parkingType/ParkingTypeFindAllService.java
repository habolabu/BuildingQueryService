package edu.ou.buildingqueryservice.service.parkingType;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ParkingTypeDocument;
import edu.ou.buildingqueryservice.data.pojo.request.parkingType.ParkingTypeFindAllRequest;
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
public class ParkingTypeFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<ParkingTypeDocument>> parkingTypeFindAllRepository;
    private final IBaseRepository<Query, Integer> parkingTypeGetPageAmountRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, ParkingTypeFindAllRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking type parameters"
            );
        }
    }

    /**
     * Find all parking type
     *
     * @param request request
     * @return parking type list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final ParkingTypeFindAllRequest parkingTypeFindAllRequest = (ParkingTypeFindAllRequest) request;

        final Query query = this.filter(parkingTypeFindAllRequest);

        final List<ParkingTypeDocument> parkingTypeDocuments = parkingTypeFindAllRepository.execute(query);
        final int pageAmount = parkingTypeGetPageAmountRepository.execute(query.skip(0).limit(0));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", parkingTypeDocuments,
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
    private Query filter(ParkingTypeFindAllRequest request) {
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

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
