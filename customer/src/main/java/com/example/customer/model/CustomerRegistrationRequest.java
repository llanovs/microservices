package com.example.customer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CustomerRegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
}
