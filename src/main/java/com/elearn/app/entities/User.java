package com.elearn.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    @Id
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    private String password;

    private String about;

    private boolean active;

    private boolean emailVerified;

    private boolean smsVerified;

    private Date createdAt;

    private String profileImagePath;

    private String recentOtp;

}
