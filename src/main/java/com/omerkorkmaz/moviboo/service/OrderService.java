package com.omerkorkmaz.moviboo.service;

import com.omerkorkmaz.moviboo.data.OrderRepository;
import com.omerkorkmaz.moviboo.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class OrderService
{
	private static final CLogger logger = CLogger.getLogger(OrderService.class);
	
	@Autowired EmailService emailService;
	@Autowired
	OrderRepository orderRepository;
	
	public Order createOrder(Order order)
	{
		//order.setOrderNumber(UUID.randomUUID().toString());
		order.setOrderNumber(String.valueOf(System.currentTimeMillis()));
		Order savedOrder = orderRepository.save(order);
		logger.info("New order created. Order Number : {}", savedOrder.getOrderNumber());
		return savedOrder;
	}
	
	public Order getOrder(String orderNumber)
	{
		return orderRepository.findByOrderNumber(orderNumber);
	}

	public List<Order> getAllOrders()
	{
		//Sort sort = new Sort(Direction.DESC, "createdOn");
		return orderRepository.findAll();
	}

	public Order updateOrder(Order order) {
		Order o = getOrder(order.getOrderNumber());
		o.setStatus(order.getStatus());
		Order savedOrder = orderRepository.save(o);
		return savedOrder;
	}
	
	
}
