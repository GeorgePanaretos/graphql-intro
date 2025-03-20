package com.graphql.intro;

import com.graphql.intro.controller.CustomerController;
import com.graphql.intro.data.Customer;
import com.graphql.intro.data.CustomerInput;
import com.graphql.intro.repo.CustomerRepository;
import com.graphql.intro.response.GraphQLResponse;
import com.graphql.intro.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<Customer> customers = (List<Customer>) customerController.customers();  // FIXED: No getData()

        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("John", customers.get(0).getFirstName());

        verify(customerRepository, times(1)).findAll();
    }

//    @Test
//    void testGetCustomerById() {
//        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
//
//        CompletableFuture<Customer> response = customerController.customerById(1L);
//
//        assertNotNull(response);
//        assertTrue(response.isSuccess());
//        assertNotNull(response.getData());
//        assertEquals("John", response.getData().getFirstName());
//
//        verify(customerRepository, times(1)).findById(1L);
//    }

    @Test
    void testAddCustomer() {
        CustomerInput input = new CustomerInput();
        input.setFirstName("Jane");
        input.setLastName("Doe");
        input.setEmail("jane.doe@example.com");

        Customer newCustomer = new Customer();
        newCustomer.setId(2L);
        newCustomer.setFirstName("Jane");
        newCustomer.setLastName("Doe");
        newCustomer.setEmail("jane.doe@example.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

        GraphQLResponse<Customer> response = customerController.addCustomer(input);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("Jane", response.getData().getFirstName());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer() {
        CustomerInput input = new CustomerInput();
        input.setFirstName("Updated");
        input.setLastName("Customer");
        input.setEmail("updated.customer@example.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        GraphQLResponse<Customer> response = customerController.updateCustomer(1L, input.getCustomerEntity());

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
        assertEquals("Updated", response.getData().getFirstName());

        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).deleteById(1L);

        GraphQLResponse<Boolean> response = customerController.deleteCustomer(1L);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(Boolean.TRUE, response.getData());

        verify(customerRepository, times(1)).deleteById(1L);
    }
}