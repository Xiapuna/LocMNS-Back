package com.mns.cda.locmnsback.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoanState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column (nullable = false, unique = true)
    protected String name;

    @OneToMany(mappedBy = "loanState")
    @JsonIgnoreProperties("loanState")
    private List<LoanHistory> loanHistories;

}
