package com.mns.cda.locmnsback.dto;

import java.time.LocalDate;

public record LoanHistoryDto(Integer loanId ,LocalDate dateChangement, String loanState) {
}
