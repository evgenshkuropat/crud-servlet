package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderServletTest {

    private OrderServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        servlet = new OrderServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    @Test
    void testDoPostCreatesOrder() throws Exception {
        Order order = new Order(1, LocalDate.now(), 50.0, List.of(new Product(1, "Coffee", 50.0)));
        String json = mapper.writeValueAsString(order);

        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(json));

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }
}