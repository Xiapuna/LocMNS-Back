package com.mns.cda.locmnsback.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    protected LocalDate startDate;

    @Column
    protected LocalDate realEndDate;

    @Column
    @NotNull
    protected LocalDate endDate;

    @ManyToOne
    protected AppUser appUser;

    @ManyToOne
    @JsonIgnoreProperties("loans")
    protected Equipment equipment;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<LoanHistory> history = new ArrayList<>();

}
