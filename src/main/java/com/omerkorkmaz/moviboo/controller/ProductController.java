package com.omerkorkmaz.moviboo.controller;

import com.omerkorkmaz.moviboo.exception.MovibooException;
import com.omerkorkmaz.moviboo.model.Category;
import com.omerkorkmaz.moviboo.model.Product;
import com.omerkorkmaz.moviboo.model.ProductForm;
import com.omerkorkmaz.moviboo.security.SecurityUtil;
import com.omerkorkmaz.moviboo.service.ProductService;
import com.omerkorkmaz.moviboo.validators.ProductFormValidator;
import com.omerkorkmaz.moviboo.web.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@Controller
@Secured(SecurityUtil.MANAGE_PRODUCTS)
public class ProductController extends AdminBaseController
{
	private static final String viewPrefix = "products/";
	@Autowired
	private ProductService productService;
	
	@Autowired private ProductFormValidator productFormValidator;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Products";
	}
	
	@ModelAttribute("categoriesList")
	public List<Category> categoriesList()
	{
		return productService.getAllCategories();
	}
	
	@RequestMapping(value="/products", method=RequestMethod.GET)
	public String listProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return viewPrefix+"products";
	}

	@RequestMapping(value="/products/new", method=RequestMethod.GET)
	public String createProductForm(Model model) {
		ProductForm product = new ProductForm();
		model.addAttribute("product",product);
		//model.addAttribute("categoriesList",catalogService.getAllCategories());
		return viewPrefix+"create_product";
	}

	@RequestMapping(value="/products", method=RequestMethod.POST)
	public String createProduct(@Valid @ModelAttribute("product") ProductForm productForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		productFormValidator.validate(productForm, result);
		if(result.hasErrors()){
			return viewPrefix+"create_product";
		}
		Product product = productForm.toProduct();
		Product persistedProduct = productService.createProduct(product);
		productForm.setId(product.getProductId());
		this.saveProductImageToDisk(productForm);
		logger.debug("Created new product with id : {} and name : {}", persistedProduct.getProductId(), persistedProduct.getTitle());
		redirectAttributes.addFlashAttribute("info", "Product created successfully");
		return "redirect:/products";
	}
	
	@RequestMapping(value="/products/{id}", method=RequestMethod.GET)
	public String editProductForm(@PathVariable Integer id, Model model) {
		Product product = productService.getProductById(id);
		model.addAttribute("product",ProductForm.fromProduct(product));
		//model.addAttribute("categoriesList",catalogService.getAllCategories());
		return viewPrefix+"edit_product";
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
	
	@RequestMapping(value="/products/{id}", method=RequestMethod.POST)
	public String updateProduct(@Valid @ModelAttribute("product") ProductForm productForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		productFormValidator.validate(productForm, result);
		if(result.hasErrors()){
			return viewPrefix+"edit_product";
		}
		Product product = productForm.toProduct();
		Product persistedProduct = productService.updateProduct(product);
		this.saveProductImageToDisk(productForm);
		logger.debug("Updated product with id : {} and name : {}", persistedProduct.getProductId(), persistedProduct.getTitle());
		redirectAttributes.addFlashAttribute("info", "Product updated successfully");
		return "redirect:/products";
	}
	
	private void saveProductImageToDisk(ProductForm productForm) {
		MultipartFile file = productForm.getImage();
		if (file!= null && !file.isEmpty()) {
			String name = WebUtils.IMAGES_DIR + productForm.getId() + ".jpg";
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				throw new MovibooException(e);
			}
		}
	}
}
