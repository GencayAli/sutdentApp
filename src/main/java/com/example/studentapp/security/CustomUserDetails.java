package com.example.studentapp.security;

import com.example.studentapp.model.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Student student;

    public CustomUserDetails(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Roller eklemek istersen burada tanımlarsın
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return student.getEmail();
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
