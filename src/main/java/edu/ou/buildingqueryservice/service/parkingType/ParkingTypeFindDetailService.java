package edu.ou.buildingqueryservice.service.parkingType;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ParkingTypeDocument;
import edu.ou.buildingqueryservice.data.entity.PriceTagDocument;
import edu.ou.buildingqueryservice.data.pojo.request.parkingType.ParkingTypeFindDetailRequest;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.common.exception.BusinessException;
import edu.ou.coreservice.common.validate.ValidValidation;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParkingTypeFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<String, ParkingTypeDocument> parkingTypeFindBySlugRepository;
    private final IBaseRepository<Integer, PriceTagDocument> priceTagFindByIdRepository;
    private final ValidValidation validValidation;

    /**
     * validate parking type detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, ParkingTypeFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking type parameters"
            );
        }
    }

    /**
     * Find parking type detail
     *
     * @param request request
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final ParkingTypeFindDetailRequest parkingTypeFindDetailRequest = (ParkingTypeFindDetailRequest) request;

        final ParkingTypeDocument parkingTypeDocument = parkingTypeFindBySlugRepository
                .execute(parkingTypeFindDetailRequest.getSlug());

        if (Objects.isNull(parkingTypeDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "parking type",
                    "parking type slug",
                    parkingTypeFindDetailRequest.getSlug()
            );
        }

        final PriceTagDocument priceTag = priceTagFindByIdRepository.execute(parkingTypeDocument.getPriceTagId());
        parkingTypeDocument.setPriceTag(priceTag);

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        parkingTypeDocument,
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest request) {
        // do nothing
    }
}
