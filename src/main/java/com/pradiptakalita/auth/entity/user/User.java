package com.pradiptakalita.auth.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pradiptakalita.auth.entity.RefreshToken;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "First name is required.")
    @Size(max = 30, message = "First name can not longer than 30 letters.")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 30, message = "Last name can not longer than 30 letters.")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Username is required")
    @Column(nullable = false,unique = true)
    @Size(max = 30,message = "Public user name can not hold more than 30 characters.")
    private String username;

    @Email(message = "Email address is not valid.")
    @NotBlank(message = "Email is required")
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password is required.")
    @Size(min = 8,message = "Password should be at least 8 characters long.")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername(){
        return this.email;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
