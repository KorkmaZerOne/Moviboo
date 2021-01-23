package com.omerkorkmaz.moviboo.controller;

import com.omerkorkmaz.moviboo.model.Cart;
import com.omerkorkmaz.moviboo.model.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
public class CheckoutController extends SiteBaseController
{

	@Override
	protected String getHeaderTitle()
	{
		return "Checkout";
	}

	@RequestMapping(value="/checkout", method=RequestMethod.GET)
	public String checkout(HttpServletRequest request, Model model)
	{
		OrderDTO order = new OrderDTO();
		model.addAttribute("order", order);
		Cart cart = getOrCreateCart(request);
		model.addAttribute("cart", cart);
		return "checkout";
	}
}
