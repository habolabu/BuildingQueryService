package edu.ou.buildingqueryservice.service.priceTag;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.PriceTagDocument;
import edu.ou.buildingqueryservice.data.pojo.request.priceTag.PriceTagFindDetailRequest;
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
public class PriceTagFindDetailService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<String, PriceTagDocument> priceTagFindBySlugRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, PriceTagFindDetailRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "price tag parameters"
            );
        }
    }

    /**
     * Find price tag detail
     *
     * @param request request
     * @return price tag detail
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final PriceTagFindDetailRequest priceTagDetailRequest = (PriceTagFindDetailRequest) request;

        final PriceTagDocument priceTagDocument =
                priceTagFindBySlugRepository.execute(priceTagDetailRequest.getSlug());

        if (Objects.isNull(priceTagDocument)) {
            throw new BusinessException(
                    CodeStatus.NOT_FOUND,
                    Message.Error.NOT_FOUND,
                    "price tag",
                    "price tag slug",
                    priceTagDetailRequest.getSlug()
            );
        }

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        priceTagDocument,
                        CodeStatus.SUCCESS,
                        Message.Success.SUCCESSFUL
                )
        );
    }

    @Override
    protected void postExecute(IBaseRequest input) {
        // do nothing
    }
}
