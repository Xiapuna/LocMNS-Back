package com.mns.cda.locmnsback.dto;

import java.time.LocalDate;

public record LoanCalendarDto(LocalDate startDate, LocalDate endDate) {
}
