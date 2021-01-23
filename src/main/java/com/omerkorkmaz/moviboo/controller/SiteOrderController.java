package com.omerkorkmaz.moviboo.controller;

import com.omerkorkmaz.moviboo.exception.MovibooException;
import com.omerkorkmaz.moviboo.model.*;
import com.omerkorkmaz.moviboo.service.CustomerService;
import com.omerkorkmaz.moviboo.service.EmailService;
import com.omerkorkmaz.moviboo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SiteOrderController extends SiteBaseController
{

	@Autowired private CustomerService customerService;
	@Autowired protected OrderService orderService;
	@Autowired protected EmailService emailService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Order";
	}

	@RequestMapping(value="/orders", method=RequestMethod.POST)
	public String placeOrder(@Valid @ModelAttribute("order") OrderDTO order,
			BindingResult result, Model model, HttpServletRequest request)
	{
		Cart cart = getOrCreateCart(request);
		if (result.hasErrors()) {
			model.addAttribute("cart", cart);
			return "checkout";
        }
		
		Order newOrder = new Order();
		
		String email = getCurrentUser().getCustomer().getEmail();
		Customer customer = customerService.getCustomerByEmail(email);
		newOrder.setCustomer(customer);

		Address address = new Address();
		address.setStreet(order.getStreet());
		address.setNumber(order.getNumber());
		address.setCity(order.getCity());
		address.setState(order.getState());
		address.setZipCode(order.getZipCode());
		address.setCountry(order.getCountry());
		
		newOrder.setDeliveryAddress(address);
		
		Address billingAddress = new Address();
		billingAddress.setStreet(order.getStreet());
		billingAddress.setNumber(order.getNumber());
		billingAddress.setCity(order.getCity());
		billingAddress.setState(order.getState());
		billingAddress.setZipCode(order.getZipCode());
		billingAddress.setCountry(order.getCountry());
		
		newOrder.setBillingAddress(billingAddress);
		
		Set<OrderItem> orderItems = new HashSet<OrderItem>();
		List<LineItem> lineItems = cart.getItems();
		for (LineItem lineItem : lineItems)
		{
			OrderItem item = new OrderItem();
			item.setProduct(lineItem.getProduct());
			item.setQuantity(lineItem.getQuantity());
			item.setPrice(lineItem.getProduct().getPrice());
			item.setOrder(newOrder);
			orderItems.add(item);
		}
		
		newOrder.setOrderItems(orderItems);
		
		Payment payment = new Payment();
		payment.setCreditNumber(order.getCreditNumber());
		
		newOrder.setPayment(payment);
		Order savedOrder = orderService.createOrder(newOrder);
		
		this.sendOrderConfirmationEmail(savedOrder);
		
		request.getSession().removeAttribute("CART_KEY");
		return "redirect:orderconfirmation?orderNumber="+savedOrder.getOrderNumber();
	}
	
	protected void sendOrderConfirmationEmail(Order order)
	{
		try {
			emailService.sendEmail(order.getCustomer().getEmail(), 
					"QuilCartCart - Order Confirmation", 
					"Your order has been placed successfully.\n"
					+ "Order Number : "+order.getOrderNumber());
		} catch (MovibooException e) {
			logger.error(e);
		}
	}
	
	@RequestMapping(value="/orderconfirmation", method=RequestMethod.GET)
	public String showOrderConfirmation(@RequestParam(value="orderNumber")String orderNumber, Model model)
	{
		Order order = orderService.getOrder(orderNumber);
		model.addAttribute("order", order);
		return "orderconfirmation";
	}
	

	@RequestMapping(value="/orders/{orderNumber}", method=RequestMethod.GET)
	public String viewOrder(@PathVariable(value="orderNumber")String orderNumber, Model model)
	{
		Order order = orderService.getOrder(orderNumber);
		model.addAttribute("order", order);
		return "view_order";
	}
}
