package com.mns.cda.locmnsback.dto;

import java.time.LocalDate;

public record LoanDto(
        int id,
        LocalDate startDate,
        LocalDate endDate,
        String loanStatus,
        int equipmentId,
        String equipmentName,
        int appUserId,
        String name,
        String firstName
) {
}
