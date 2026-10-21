package com.hesappcepte.account.dto;

import com.hesappcepte.account.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AccountCreateRequest(

        @NotBlank
        @Size(min = 2, max = 80)
        String name,

        @NotNull
        AccountType accountType,

        @NotBlank
        @Size(min=3,max = 3)
        String currency,

        @NotNull
        @PositiveOrZero
        BigDecimal initialBalance)
{
}
