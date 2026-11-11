package com.hesappcepte.financearea.mapper;

import com.hesappcepte.areamember.AreaMember;
import com.hesappcepte.financearea.FinanceArea;
import com.hesappcepte.financearea.dto.FinanceAreaResponse;
import org.springframework.stereotype.Component;

@Component
public class FinanceAreaMapper {

    public FinanceAreaResponse toResponse(
            FinanceArea area,
            AreaMember membership
    ) {

        FinanceAreaResponse response = new FinanceAreaResponse(
                area.getId(),
                area.getName(),
                area.getAreaType(),
                area.getSharingType(),
                area.getBaseCurrency(),
                area.getStatus(),
                membership.getRole(),
                area.getCreatedAt(),
                area.getUpdatedAt()
        );
        return response;
    }
}
