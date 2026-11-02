package com.hesappcepte.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountUpdateRequest(
        @NotBlank
        @Size(min = 2, max = 80)
        String name
) {}
