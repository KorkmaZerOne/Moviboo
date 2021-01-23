package com.omerkorkmaz.moviboo.model;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "FirstName is required")
    private String firstName;

    @NotNull(message = "LastName is required")
    private String lastName;

    @NotNull(message = "EmailId is required")
    @Email
    private String emailId;

    @NotNull(message = "Phone is required")
    private String phone;

    @NotNull(message = "Street is required")
    private String street;

    @NotNull(message = "Number is required")
    private String number;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "State is required")
    private String state;

    @NotNull(message = "ZipCode is required")
    private String zipCode;

    @NotNull(message = "Country is required")
    private String country;

    @NotNull(message = "FirstName is required")
    private String billingFirstName;

    @NotNull(message = "LastName is required")
    private String billingLastName;

    @NotNull(message = "Address Line1 is required")
    private String billingAddressLine1;
    private String billingAddressLine2;
    @NotNull(message = "City is required")
    private String billingCity;
    @NotNull(message = "State is required")
    private String billingState;
    @NotNull(message = "ZipCode is required")
    private String billingZipCode;
    @NotNull(message = "Country is required")
    private String billingCountry;
    @NotNull(message = "Credit Number is required")
    private String creditNumber;

}
