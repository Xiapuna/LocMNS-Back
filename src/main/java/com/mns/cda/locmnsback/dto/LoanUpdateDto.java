package com.mns.cda.locmnsback.dto;

import java.time.LocalDate;

public record LoanUpdateDto(
        LocalDate startDate,
        LocalDate endDate
) {
}
