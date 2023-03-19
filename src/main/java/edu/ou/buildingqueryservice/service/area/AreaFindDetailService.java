package edu.ou.buildingqueryservice.service.area;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.AreaDocument;
import edu.ou.buildingqueryservice.data.pojo.request.area.AreaFindDetailRequest;
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
public class AreaFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<String, AreaDocument> areaFindBySlugRepository;
    private final ValidValidation validValidation;

    /**
     * validate apartment detail request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, AreaFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "area parameters"
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
        final AreaFindDetailRequest areaFindDetailRequest = (AreaFindDetailRequest) request;

        final AreaDocument areaDocument = areaFindBySlugRepository
                .execute(areaFindDetailRequest.getSlug());

        if (Objects.isNull(areaDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "area",
                    "area slug",
                    areaFindDetailRequest.getSlug()
            );
        }

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        areaDocument,
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
