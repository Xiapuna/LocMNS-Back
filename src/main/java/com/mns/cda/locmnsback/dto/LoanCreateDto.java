package com.mns.cda.locmnsback.dto;

import java.time.LocalDate;

public record LoanCreateDto(LocalDate startDate, LocalDate endDate, Integer equipmentId, Integer appUserId) {
}
