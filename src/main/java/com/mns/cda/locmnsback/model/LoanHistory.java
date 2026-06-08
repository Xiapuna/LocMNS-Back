package com.mns.cda.locmnsback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected LocalDateTime dateChangement;

    @ManyToOne (optional = false)
    @JoinColumn(name = "loan_id")
    protected Loan loan;

    @ManyToOne
    @JoinColumn(name = "loan_state_id")
    private LoanState loanState;
}
