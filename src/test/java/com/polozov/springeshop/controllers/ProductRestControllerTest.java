package com.polozov.springeshop.controllers;

import com.polozov.springeshop.dto.ProductDto;
import com.polozov.springeshop.service.ProductService;
import com.polozov.springeshop.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private UserService userService;

    private ProductDto dto = new ProductDto(999L, "TestProduct", 999.99);

    @BeforeEach
    void setUp() {
        given(productService.getById(dto.getId())).willReturn(dto);
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/products/{id}", dto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(999)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("TestProduct")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(999.99)));

    }
}
