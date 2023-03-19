package edu.ou.buildingqueryservice.service.parking;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ParkingDocument;
import edu.ou.buildingqueryservice.data.pojo.request.parking.ParkingFindDetailRequest;
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
public class ParkingFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<String, ParkingDocument> parkingFindBySlugRepository;
    private final ValidValidation validValidation;

    /**
     * validate parking detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, ParkingFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "parking parameters"
            );
        }
    }

    /**
     * Find apartment detail
     *
     * @param request request
     * @return response
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final ParkingFindDetailRequest parkingFindDetailRequest = (ParkingFindDetailRequest) request;

        final ParkingDocument parkingDocument = parkingFindBySlugRepository
                .execute(parkingFindDetailRequest.getSlug());

        if (Objects.isNull(parkingDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "parking",
                    "parking slug",
                    parkingFindDetailRequest.getSlug()
            );
        }

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        parkingDocument,
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
