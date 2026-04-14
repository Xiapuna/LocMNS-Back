package com.mns.cda.locmnsback.model;

import com.mns.cda.locmnsback.dao.UserDao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(length = 100)
    @Length(min = 1, max = 100)
    @NotBlank
    protected String firstname;

    @Column(length = 100)
    @Length(min = 1, max = 100)
    @NotBlank
    protected String name;

    @Column(length = 100)
    @Length(min = 1, max = 100)
    @NotBlank
    protected String email;

    @Column(length = 100)
    @Length(min = 1, max = 100)
    @NotBlank
    protected String password;
}
