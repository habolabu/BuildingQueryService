package edu.ou.buildingqueryservice.service.parkingType;

import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ParkingTypeDocument;
import edu.ou.coreservice.common.constant.Message;
import edu.ou.coreservice.data.pojo.request.base.IBaseRequest;
import edu.ou.coreservice.data.pojo.response.base.IBaseResponse;
import edu.ou.coreservice.data.pojo.response.impl.SuccessPojo;
import edu.ou.coreservice.data.pojo.response.impl.SuccessResponse;
import edu.ou.coreservice.repository.base.IBaseRepository;
import edu.ou.coreservice.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingTypeFindAllWithoutPaginationService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<ParkingTypeDocument>> parkingTypeFindAllRepository;

    @Override
    protected void preExecute(IBaseRequest request) {
        // do nothing
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
        return new SuccessResponse<>(
                new SuccessPojo<>(
                        parkingTypeFindAllRepository.execute(new Query()),
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
