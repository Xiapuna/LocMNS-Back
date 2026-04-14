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
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Model {

    public interface OnUpdate {};
    public interface OnCreate {};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(length = 100)
    @Length(min = 1, max = 100, groups = {OnUpdate.class, OnCreate.class})
    @NotBlank
    protected String name;

    @Column(length = 500)
    @Length (min = 1, max = 500)
    @NotBlank
    protected String description;
}
