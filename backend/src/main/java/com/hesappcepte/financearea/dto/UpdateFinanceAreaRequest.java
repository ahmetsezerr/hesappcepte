package com.hesappcepte.financearea.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateFinanceAreaRequest(
        @NotBlank
        @Size(min = 2, max = 80)
         String name
) {}
