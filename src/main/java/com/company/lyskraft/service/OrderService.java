package com.company.lyskraft.service;

import com.company.lyskraft.constant.OrderStatus;
import com.company.lyskraft.entity.Order;
import com.company.lyskraft.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<Order> getAllOrdersByCompanyId(long currentCompanyId, Pageable page) {
        return orderRepository.findAllByEnquiryEnquiryCompanyIdOrQuoteQuoteCompanyId(currentCompanyId,
                currentCompanyId, page);
    }

    public Order updateOrder(Order orderDetails) throws Exception {
        Optional<Order> order = orderRepository.findById(orderDetails.getId());
        if (order.isPresent()) {
            if (order.get().getStatus() == OrderStatus.Delivered) {
                throw new Exception("Order already Delivered");
            }
            if (orderDetails.getStatus() != null) {
                order.get().setStatus(orderDetails.getStatus());
            }
            if (orderDetails.getPaymentTerms() != null) {
                order.get().setPaymentTerms(orderDetails.getPaymentTerms());
            }
            if (orderDetails.getTransportationTerms() != null) {
                order.get().setTransportationTerms(orderDetails.getTransportationTerms());
            }
            return orderRepository.save(order.get());
        }
        throw new Exception("Order not present");
    }

    public Page<Order> getAllOrders(Pageable page) {
        return orderRepository.findAll(page);
    }
}