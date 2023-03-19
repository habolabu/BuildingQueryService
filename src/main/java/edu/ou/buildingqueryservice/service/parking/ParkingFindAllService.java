package edu.ou.buildingqueryservice.service.parking;


import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ParkingDocument;
import edu.ou.buildingqueryservice.data.pojo.request.parking.ParkingFindAllWithParamsRequest;
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
public class ParkingFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<ParkingDocument>> parkingFindAllRepository;
    private final IBaseRepository<Query, Integer> parkingGetPageAmountRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, ParkingFindAllWithParamsRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking parameters"
            );
        }
    }

    /**
     * Find all parking
     *
     * @param request request
     * @return parking list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final ParkingFindAllWithParamsRequest parkingFindAllWithParamsRequest =
                (ParkingFindAllWithParamsRequest) request;

        final Query query = this.filter(parkingFindAllWithParamsRequest);

        final List<ParkingDocument> parkingDocuments = parkingFindAllRepository.execute(query);
        final int pageAmount = parkingGetPageAmountRepository.execute(query.skip(0).limit(0));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", parkingDocuments,
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
    private Query filter(ParkingFindAllWithParamsRequest request) {
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

        if (Objects.nonNull(request.getApartmentId())) {
            query.addCriteria(
                    Criteria.where("apartmentId")
                            .is(request.getApartmentId())
            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
