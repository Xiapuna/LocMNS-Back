package com.mns.cda.locmnsback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor    // Créer un constructeur avec tous les attributs
@NoArgsConstructor
@Entity
public class Location {

    public interface OnUpdate {
    }

    ;

    public interface OnCreate {
    }

    ;

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation
    protected Integer id;

    @Column(length = 100)
    @Length(min = 1, max = 100, groups = {OnUpdate.class, OnCreate.class})
    @NotBlank
    protected String name;
}