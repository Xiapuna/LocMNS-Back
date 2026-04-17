package com.mns.cda.locmnsback.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmnsback.view.AppUserView;
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
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(length = 500)
    @Length(min = 1, max = 500)
    @NotBlank
    protected String name;

    @Column(length = 100)
    @Length(min = 1, max = 100)
    @NotBlank
    protected String condition;

    @ManyToOne (optional = false)
    protected Location location;
}
