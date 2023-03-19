package edu.ou.buildingqueryservice.service.parkingSpace;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ParkingSpaceDocument;
import edu.ou.buildingqueryservice.data.entity.ParkingTypeDocument;
import edu.ou.buildingqueryservice.data.entity.PriceTagDocument;
import edu.ou.buildingqueryservice.data.pojo.request.parkingSpace.ParkingSpaceFindAllWithParamsRequest;
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
public class ParkingSpaceFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<ParkingSpaceDocument>> parkingSpaceFindAllRepository;
    private final IBaseRepository<Query, Integer> parkingSpaceGetPageAmountRepository;
    private final IBaseRepository<Integer, ParkingTypeDocument> parkingTypeFindByIdRepository;
    private final IBaseRepository<Integer, PriceTagDocument> priceTagFindByIdRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, ParkingSpaceFindAllWithParamsRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking space parameters"
            );
        }
    }

    /**
     * Find all parking space
     *
     * @param request request
     * @return parking space list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final ParkingSpaceFindAllWithParamsRequest parkingSpaceFindAllWithParamsRequest =
                (ParkingSpaceFindAllWithParamsRequest) request;

        final Query query = this.filter(parkingSpaceFindAllWithParamsRequest);

        final List<ParkingSpaceDocument> parkingSpaces = parkingSpaceFindAllRepository.execute(query);
        parkingSpaces.forEach(parkingSpace -> {
            final ParkingTypeDocument parkingType =
                    parkingTypeFindByIdRepository.execute(parkingSpace.getParkingTypeId());
            final PriceTagDocument priceTag = priceTagFindByIdRepository.execute(parkingType.getPriceTagId());
            parkingType.setPriceTag(priceTag);
            parkingSpace.setParkingType(parkingType);
        });

        final int pageAmount = parkingSpaceGetPageAmountRepository.execute(query.skip(0).limit(0));

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", parkingSpaces,
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
    private Query filter(ParkingSpaceFindAllWithParamsRequest request) {
        final Query query = new Query();

        if (Objects.nonNull(request.getParkingId())) {
            query.addCriteria(
                    Criteria.where("parkingId")
                            .is(request.getParkingId())
            );
        }

        if (Objects.nonNull(request.getBCapacity())
                && Objects.nonNull(request.getECapacity())) {
            query.addCriteria(
                    Criteria.where("capacity")
                            .gte(request.getBCapacity())
                            .lte(request.getECapacity())
            );
        }

        if (Objects.nonNull(request.getBAvailableSpace())
                && Objects.nonNull(request.getEAvailableSpace())) {
            query.addCriteria(
                    Criteria.where("availableSpace")
                            .gte(request.getBAvailableSpace())
                            .lte(request.getEAvailableSpace())
            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }
}
