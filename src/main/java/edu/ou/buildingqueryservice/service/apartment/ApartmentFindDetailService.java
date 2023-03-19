package edu.ou.buildingqueryservice.service.apartment;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ApartmentDocument;
import edu.ou.buildingqueryservice.data.pojo.request.apartment.ApartmentFindDetailRequest;
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
public class ApartmentFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<String, ApartmentDocument> apartmentFindBySlugRepository;
    private final ValidValidation validValidation;

    /**
     * validate apartment detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, ApartmentFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "apartment parameters"
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
        final ApartmentFindDetailRequest apartmentFindDetailRequest = (ApartmentFindDetailRequest) request;

        final ApartmentDocument apartmentDocument = apartmentFindBySlugRepository
                .execute(apartmentFindDetailRequest.getSlug());

        if (Objects.isNull(apartmentDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "apartment",
                    "apartment slug",
                    apartmentFindDetailRequest.getSlug()
            );
        }

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        apartmentDocument,
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
