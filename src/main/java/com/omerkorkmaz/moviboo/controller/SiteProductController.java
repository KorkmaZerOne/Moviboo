package com.omerkorkmaz.moviboo.controller;

import com.omerkorkmaz.moviboo.model.Product;
import com.omerkorkmaz.moviboo.service.ProductService;
import com.omerkorkmaz.moviboo.web.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class SiteProductController extends SiteBaseController
{	
	@Autowired
	private ProductService productService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Product";
	}	
	
	@RequestMapping("/products/{sku}")
	public String product(@PathVariable String sku, Model model)
	{
		Product product = productService.getProductBySku(sku);
		model.addAttribute("product", product);
		return "product";
	}
	
	@RequestMapping("/products")
	public String searchProducts(@RequestParam(name="q", defaultValue="") String query, Model model)
	{
		List<Product> products = productService.searchProducts(query);
		model.addAttribute("products", products);
		return "products";
	}

	@RequestMapping(value="/products/images/{productId}", method=RequestMethod.GET)
	public void showProductImage(@PathVariable String productId, HttpServletRequest request, HttpServletResponse response) {
		try {
			FileSystemResource file = new FileSystemResource(WebUtils.IMAGES_DIR +productId+".jpg");
			response.setContentType("image/jpg");
			org.apache.commons.io.IOUtils.copy(file.getInputStream(), response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}	      
	}
}
