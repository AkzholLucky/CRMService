package com.backend.PhoneVerify.models;

import lombok.*;
import javax.persistence.*;

@Table(name = "people")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "number")
    private String number;

    @Column(name = "password")
    private String password;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "is_enabled")
    private boolean isEnabled;
}
