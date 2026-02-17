package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/orders/*")
public class OrderServlet extends HttpServlet {

    private final Map<Integer, Order> orders = new ConcurrentHashMap<>();

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Create order from JSON
        Order order = mapper.readValue(req.getInputStream(), Order.class);
        orders.put(order.getId(), order);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Return all orders
            mapper.writeValue(resp.getOutputStream(), orders.values());
            return;
        }

        try {
            int id = extractIdFromPath(pathInfo);
            Order order = orders.get(id);

            if (order != null) {
                mapper.writeValue(resp.getOutputStream(), order);
            } else {
                writeJsonError(resp, HttpServletResponse.SC_NOT_FOUND, "Order not found");
            }
        } catch (NumberFormatException e) {
            writeJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Update order by id from JSON body
        Order updated = mapper.readValue(req.getInputStream(), Order.class);

        if (orders.containsKey(updated.getId())) {
            orders.put(updated.getId(), updated);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            writeJsonError(resp, HttpServletResponse.SC_NOT_FOUND, "Order not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            writeJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing order ID");
            return;
        }

        try {
            int id = extractIdFromPath(pathInfo);
            Order removed = orders.remove(id);

            if (removed != null) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 — deleted
            } else {
                writeJsonError(resp, HttpServletResponse.SC_NOT_FOUND, "Order not found");
            }
        } catch (NumberFormatException e) {
            writeJsonError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        }
    }

    private int extractIdFromPath(String pathInfo) throws NumberFormatException {
        // pathInfo like "/123"
        return Integer.parseInt(pathInfo.substring(1));
    }

    private void writeJsonError(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}
