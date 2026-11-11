
package com.hesappcepte.financearea.dto;

import com.hesappcepte.areamember.AreaMemberRole;
import com.hesappcepte.financearea.FinanceAreaStatus;
import com.hesappcepte.financearea.FinanceAreaType;
import com.hesappcepte.financearea.SharingType;

import java.time.LocalDateTime;

public record FinanceAreaResponse(
        Long id,
        String name,
        FinanceAreaType areaType,
        SharingType sharingType,
        String baseCurrency,
        FinanceAreaStatus status,
        AreaMemberRole myRole,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
