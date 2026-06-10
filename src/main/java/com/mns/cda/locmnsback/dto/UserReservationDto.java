package com.mns.cda.locmnsback.dto;

import java.time.LocalDate;

public record UserReservationDto(Integer loanId, Integer equipmentId, Integer equipmentTypeId, String equipmentName, LocalDate startDate, LocalDate endDate, String loanStatus) {
}
