
package com.hesappcepte.financearea.dto;

import com.hesappcepte.financearea.FinanceAreaType;
import com.hesappcepte.financearea.SharingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateFinanceAreaRequest(

        @NotBlank
        @Size(min = 2, max = 80)
        String name,

        @NotNull
        FinanceAreaType areaType,

        @NotNull
        SharingType sharingType,

        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}$")
        String baseCurrency

) {
}
