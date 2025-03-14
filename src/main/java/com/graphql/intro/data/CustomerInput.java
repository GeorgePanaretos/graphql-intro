package com.graphql.intro.data;

import lombok.Data;

@Data
public class CustomerInput {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public Customer getCustomerEntity(){
        Customer customer = new Customer();
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setEmail(this.email);
        customer.setPhoneNumber(this.phoneNumber);
        customer.setAddress(this.address);
        customer.setCity(this.city);
        customer.setState(this.state);
        customer.setZipCode(this.zipCode);
        return customer;
    }
}
