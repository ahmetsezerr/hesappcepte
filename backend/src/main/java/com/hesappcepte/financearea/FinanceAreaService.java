package com.hesappcepte.financearea;

import com.hesappcepte.areamember.AreaMember;
import com.hesappcepte.areamember.AreaMemberRepository;
import com.hesappcepte.areamember.AreaMemberRole;
import com.hesappcepte.areamember.AreaMemberStatus;
import com.hesappcepte.common.exception.BusinessException;
import com.hesappcepte.common.exception.ErrorCode;
import com.hesappcepte.financearea.dto.CreateFinanceAreaRequest;
import com.hesappcepte.financearea.dto.FinanceAreaResponse;
import com.hesappcepte.financearea.dto.UpdateFinanceAreaRequest;
import com.hesappcepte.financearea.mapper.FinanceAreaMapper;
import com.hesappcepte.user.User;
import com.hesappcepte.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hesappcepte.financearea.FinanceAreaStatus.ACTIVE;

@Service
public class FinanceAreaService {

    private final FinanceAreaRepository financeAreaRepository;
    private final AreaMemberRepository areaMemberRepository;
    private final UserRepository userRepository;
    private final FinanceAreaMapper financeAreaMapper;

    public FinanceAreaService(FinanceAreaRepository financeAreaRepository, AreaMemberRepository areaMemberRepository, UserRepository userRepository, FinanceAreaMapper financeAreaMapper) {
        this.financeAreaRepository = financeAreaRepository;
        this.areaMemberRepository = areaMemberRepository;
        this.userRepository = userRepository;
        this.financeAreaMapper = financeAreaMapper;
    }

    @Transactional
    public FinanceAreaResponse createFinanceArea
            (CreateFinanceAreaRequest request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.USER_NOT_FOUND));

        FinanceArea financeArea = new FinanceArea(
                request.name(),
                request.areaType(),
                request.sharingType(),
                request.baseCurrency(),
                user
        );

        FinanceArea savedArea = financeAreaRepository.save(financeArea);

        AreaMember areaMember = new AreaMember(
                savedArea,
                user,
                AreaMemberRole.OWNER,
                user
        );

        AreaMember savedMembership = areaMemberRepository.save(areaMember);

        return financeAreaMapper.toResponse(
                savedArea,
                savedMembership
        );
    }

    @Transactional(readOnly = true)
    public List<FinanceAreaResponse> getFinanceAreas(Long userId) {

        List<AreaMember> memberships =
                areaMemberRepository.findByUser_IdAndStatus(
                        userId,
                        AreaMemberStatus.ACTIVE
                );

        List<FinanceAreaResponse> responses = new ArrayList<>();

        for (AreaMember member : memberships) {

            FinanceArea area = member.getFinanceArea();

            if (area.getStatus() == FinanceAreaStatus.ACTIVE) {

                FinanceAreaResponse response = financeAreaMapper.toResponse(area, member);

                responses.add(response);
            }
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public FinanceAreaResponse getFinanceAreaById(Long userId,Long financeAreaId ){

        AreaMember member
                = areaMemberRepository.findByUser_IdAndFinanceArea_IdAndStatus
                (userId,financeAreaId,AreaMemberStatus.ACTIVE)
                .orElseThrow(()->new BusinessException(ErrorCode.FINANCEAREA_NOT_FOUND));

        FinanceArea area = member.getFinanceArea();

        FinanceAreaResponse response =financeAreaMapper.toResponse(area,member);

        return response;
    }

    @Transactional
    public FinanceAreaResponse updateFinanceArea(Long userId, Long financeAreaId, UpdateFinanceAreaRequest request){

        AreaMember areaMember
                = areaMemberRepository.findByUser_IdAndFinanceArea_IdAndStatus(userId,financeAreaId,AreaMemberStatus.ACTIVE)
                .orElseThrow(()->new BusinessException(ErrorCode.FINANCEAREA_NOT_FOUND));


        if(areaMember.getRole() != AreaMemberRole.OWNER){
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        FinanceArea financeArea = areaMember.getFinanceArea();

        if(financeArea.getStatus()!=ACTIVE){
            throw new BusinessException(ErrorCode.FINANCEAREA_ARCHIVED);
        }
        financeArea.rename(request.name().trim());

        FinanceAreaResponse response = financeAreaMapper.toResponse(financeArea,areaMember);

        return response;
    }

    @Transactional
    public FinanceAreaResponse archiveFinanceArea(Long userId,Long financeAreaId){

        AreaMember areaMember
                = areaMemberRepository.findByUser_IdAndFinanceArea_IdAndStatus(userId,financeAreaId,AreaMemberStatus.ACTIVE)
                .orElseThrow(()->new BusinessException(ErrorCode.FINANCEAREA_NOT_FOUND));

        if(areaMember.getRole() !=AreaMemberRole.OWNER){
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        FinanceArea area = areaMember.getFinanceArea();

        area.archive();

        FinanceAreaResponse response = financeAreaMapper.toResponse(area,areaMember);

        return response;

    }

    @Transactional
    public  FinanceAreaResponse restoreFinanceArea(Long userId, Long financeAreaId){

        AreaMember areaMember
                = areaMemberRepository.findByUser_IdAndFinanceArea_IdAndStatus(userId,financeAreaId,AreaMemberStatus.ACTIVE)
                .orElseThrow(()->new BusinessException (ErrorCode.FINANCEAREA_NOT_FOUND));

        if (areaMember.getRole() != AreaMemberRole.OWNER){
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        FinanceArea area = areaMember.getFinanceArea();

        area.activate();

        FinanceAreaResponse response = financeAreaMapper.toResponse(area,areaMember);

        return response;

    }

}