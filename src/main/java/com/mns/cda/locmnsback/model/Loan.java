package com.mns.cda.locmnsback.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mns.cda.locmnsback.enums.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column
    @NotNull
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @JsonFormat(pattern = "dd-MM-yyyy")
    protected LocalDate startDate;

    @Column
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @JsonFormat(pattern = "dd-MM-yyyy")
    protected LocalDate realEndDate;

    @Column
    @NotNull
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    @JsonFormat(pattern = "dd-MM-yyyy")
    protected LocalDate endDate;

    @Column
    @Enumerated(EnumType.STRING)
    protected LoanStatus loanStatus = LoanStatus.VALIDATED;

    @ManyToOne
    protected AppUser appUser;

    @ManyToOne
    @JsonIgnoreProperties("loans")
    protected Equipment equipment;
}
