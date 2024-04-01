package com.hritvik.CarbonCellAssignment.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SecurityBean {

    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;

}