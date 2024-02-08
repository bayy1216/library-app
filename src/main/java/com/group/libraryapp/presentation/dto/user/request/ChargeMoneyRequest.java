package com.group.libraryapp.presentation.dto.user.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class ChargeMoneyRequest {
    @Positive(message = "금액은 0보다 커야 합니다.")
    @NotNull(message = "금액은 필수입니다.")
    private Integer money;
}
