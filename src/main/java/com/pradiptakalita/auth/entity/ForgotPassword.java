package com.pradiptakalita.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pradiptakalita.auth.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "forgot_password")
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private Integer otp;

    @NotNull
    @Column(nullable = false)
    private Date expirationTime;

    @JsonIgnore
    private int attempts;

    @OneToOne
    private User user;

}
