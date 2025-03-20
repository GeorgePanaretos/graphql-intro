package com.graphql.intro;

import com.graphql.intro.controller.OrderController;
import com.graphql.intro.data.Order;
import com.graphql.intro.data.OrderInput;
import com.graphql.intro.repo.OrderRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private OrderController orderController;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId("ORD123");
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> orders = orderController.orders();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("ORD123", orders.get(0).getId());

        verify(orderRepository, times(1)).findAll();
    }

    /*@Test
    void testGetOrderById() {
        when(orderRepository.findById("ORD123")).thenReturn(Optional.of(order));

        GraphQLResponse<Order> response = orderController.orderById("ORD123");

        assertTrue(response.isSuccess());
        assertEquals("ORD123", response.getData().getId());

        verify(orderRepository, times(1)).findById("ORD123");
    }*/

    @Test
    void testAddOrder() {
        OrderInput input = new OrderInput();
        input.setCustomerId(1L);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        GraphQLResponse<Order> response = orderController.addOrder(input);

        assertTrue(response.isSuccess());
        assertEquals("ORD123", response.getData().getId());

        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
