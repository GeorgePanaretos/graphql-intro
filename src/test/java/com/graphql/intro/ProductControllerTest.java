package com.graphql.intro;

import com.graphql.intro.controller.ProductController;
import com.graphql.intro.data.Product;
import com.graphql.intro.data.ProductInput;
import com.graphql.intro.repo.ProductRepository;
import com.graphql.intro.response.GraphQLResponse;
import com.graphql.intro.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId("MWBLU20");
        product.setName("Mineral Water");
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> products = productController.products();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Mineral Water", products.get(0).getName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById("MWBLU20")).thenReturn(Optional.of(product));

        Product response = productController.productById("MWBLU20");

       // assertTrue(response.equals());
        assertEquals("Mineral Water", response.getName());

        verify(productRepository, times(1)).findById("MWBLU20");
    }

    @Test
    void testAddProduct() {
        ProductInput input = new ProductInput();
        input.setName("Mineral Water");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        GraphQLResponse<Product> response = productController.addProduct(input);

        assertTrue(response.isSuccess());
        assertEquals("Mineral Water", response.getData().getName());

        verify(productRepository, times(1)).save(any(Product.class));
    }
}
