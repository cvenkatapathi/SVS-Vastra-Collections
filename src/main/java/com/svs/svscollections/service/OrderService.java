package com.svs.svscollections.service;

import com.svs.svscollections.model.Order;
import com.svs.svscollections.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Order order) {

        // If order date is not provided, automatically set today's date
        if (order.getOrderDate() == null) {
            order.setOrderDate(LocalDate.now());
        }

        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public boolean updateStatus(int id, String status) {

        if (!isValidStatus(status)) {
            return false;
        }

        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return false;
        }

        order.setStatus(status);
        orderRepository.save(order);

        return true;
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    private boolean isValidStatus(String status) {

        return "Placed".equalsIgnoreCase(status)
                || "Pending".equalsIgnoreCase(status)
                || "Delivered".equalsIgnoreCase(status)
                || "Cancelled".equalsIgnoreCase(status);
    }


    // Monthly Order Count

    public Map<String, Integer> getMonthlyOrderCount() {

        Map<String, Integer> monthlyOrders = createMonthlyOrderMap();

        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {

            if (order.getOrderDate() == null) {
                continue;
            }

            Month month = order.getOrderDate().getMonth();

            String monthName = month.name();

            monthlyOrders.put(
                    monthName,
                    monthlyOrders.get(monthName) + 1
            );
        }

        return monthlyOrders;
    }


    // Monthly Revenue

    public Map<String, Double> getMonthlyRevenue() {

        Map<String, Double> monthlyRevenue = createMonthlyRevenueMap();

        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {

            if (order.getOrderDate() == null) {
                continue;
            }

            Month month = order.getOrderDate().getMonth();

            String monthName = month.name();

            monthlyRevenue.put(
                    monthName,
                    monthlyRevenue.get(monthName) + order.getAmount()
            );
        }

        return monthlyRevenue;
    }


    // Create Monthly Order Map

    private Map<String, Integer> createMonthlyOrderMap() {

        Map<String, Integer> monthlyOrders = new LinkedHashMap<>();

        monthlyOrders.put("JANUARY", 0);
        monthlyOrders.put("FEBRUARY", 0);
        monthlyOrders.put("MARCH", 0);
        monthlyOrders.put("APRIL", 0);
        monthlyOrders.put("MAY", 0);
        monthlyOrders.put("JUNE", 0);
        monthlyOrders.put("JULY", 0);
        monthlyOrders.put("AUGUST", 0);
        monthlyOrders.put("SEPTEMBER", 0);
        monthlyOrders.put("OCTOBER", 0);
        monthlyOrders.put("NOVEMBER", 0);
        monthlyOrders.put("DECEMBER", 0);

        return monthlyOrders;
    }


    // Create Monthly Revenue Map

    private Map<String, Double> createMonthlyRevenueMap() {

        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();

        monthlyRevenue.put("JANUARY", 0.0);
        monthlyRevenue.put("FEBRUARY", 0.0);
        monthlyRevenue.put("MARCH", 0.0);
        monthlyRevenue.put("APRIL", 0.0);
        monthlyRevenue.put("MAY", 0.0);
        monthlyRevenue.put("JUNE", 0.0);
        monthlyRevenue.put("JULY", 0.0);
        monthlyRevenue.put("AUGUST", 0.0);
        monthlyRevenue.put("SEPTEMBER", 0.0);
        monthlyRevenue.put("OCTOBER", 0.0);
        monthlyRevenue.put("NOVEMBER", 0.0);
        monthlyRevenue.put("DECEMBER", 0.0);

        return monthlyRevenue;
    }
}