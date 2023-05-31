package edu.ou.buildingqueryservice.service.apartment;


import edu.ou.buildingqueryservice.common.constant.CodeStatus;
import edu.ou.buildingqueryservice.data.entity.ApartmentDocument;
import edu.ou.buildingqueryservice.data.entity.OwnerHistoryDocument;
import edu.ou.buildingqueryservice.data.entity.RoomDocument;
import edu.ou.buildingqueryservice.data.pojo.request.apartment.ApartmentFindAllWithParamsRequest;
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
public class ApartmentFindAllService extends BaseService<IBaseRequest, IBaseResponse> {
    private final IBaseRepository<Query, List<ApartmentDocument>> apartmentFindAllRepository;
    private final IBaseRepository<Query, Integer> apartmentGetPageAmountRepository;
    private final IBaseRepository<Integer, List<RoomDocument>> roomFindByApartmentIdRepository;
    private final IBaseRepository<List<Integer>, List<OwnerHistoryDocument>> ownerHistoryFindByRoomIdsRepository;
    private final ValidValidation validValidation;

    /**
     * Validate request
     *
     * @param request request
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected void preExecute(IBaseRequest request) {
        if (validValidation.isInValid(request, ApartmentFindAllWithParamsRequest.class)) {
            throw new BusinessException(
                    CodeStatus.INVALID_INPUT,
                    Message.Error.INVALID_INPUT,
                    "apartment parameters"
            );
        }
    }

    /**
     * Find all apartment
     *
     * @param request request
     * @return apartment list
     * @author Nguyen Trung Kien - OU
     */
    @Override
    protected IBaseResponse doExecute(IBaseRequest request) {
        final ApartmentFindAllWithParamsRequest apartmentFindAllWithParamsRequest =
                (ApartmentFindAllWithParamsRequest) request;

        final Query query = this.filter(apartmentFindAllWithParamsRequest);

        final List<ApartmentDocument> apartmentDocuments = apartmentFindAllRepository.execute(query);
        final int pageAmount = apartmentGetPageAmountRepository.execute(query.skip(0).limit(0));

        apartmentDocuments.forEach(apartmentDocument -> {
            final List<Integer> roomIds = roomFindByApartmentIdRepository
                    .execute(apartmentDocument.getOId())
                    .stream()
                    .map(RoomDocument::getOId)
                    .toList();
            final List<OwnerHistoryDocument> ownerHistoryDocuments = ownerHistoryFindByRoomIdsRepository.execute(roomIds);
            apartmentDocument.setTotalRoom(roomIds.size());
            apartmentDocument.setFullRoomAmount(ownerHistoryDocuments.size());
        });

        return new SuccessResponse<>(
                new SuccessPojo<>(
                        Map.of(
                                "data", apartmentDocuments,
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
     */
    private Query filter(ApartmentFindAllWithParamsRequest request) {
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

        if (Objects.nonNull(request.getAreaId())) {
            query.addCriteria(
                    Criteria.where("areaId")
                            .is(request.getAreaId())
            );
        }

        if (Objects.nonNull(request.getBFloorAmount())
                && Objects.nonNull(request.getEFloorAmount())) {
            query.addCriteria(
                    Criteria.where("floorAmount")
                            .gte(request.getBFloorAmount())
                            .lte(request.getEFloorAmount())
            );
        }

        query.skip(PaginationUtils.getSearchIndex(request.getPage()))
                .limit(PaginationUtils.getPageSize());

        return query;
    }

}
