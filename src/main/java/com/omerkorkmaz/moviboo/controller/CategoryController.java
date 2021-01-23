package com.omerkorkmaz.moviboo.controller;

import com.omerkorkmaz.moviboo.model.Category;
import com.omerkorkmaz.moviboo.security.SecurityUtil;
import com.omerkorkmaz.moviboo.service.ProductService;
import com.omerkorkmaz.moviboo.validators.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@Secured(SecurityUtil.MANAGE_CATEGORIES)
public class CategoryController extends AdminBaseController
{
	private static final String viewPrefix = "categories/";
	
	@Autowired
	private ProductService productService;
	
	@Autowired private CategoryValidator categoryValidator;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Categories";
	}
	
	@RequestMapping(value="/categories", method=RequestMethod.GET)
	public String listCategories(Model model) {
		List<Category> list = productService.getAllCategories();
		model.addAttribute("categories",list);
		return viewPrefix+"categories";
	}
	
	@RequestMapping(value="/categories/new", method=RequestMethod.GET)
	public String createCategoryForm(Model model) {
		Category category = new Category();
		model.addAttribute("category",category);
		
		return viewPrefix+"create_category";
	}

	@RequestMapping(value="/categories", method=RequestMethod.POST)
	public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		categoryValidator.validate(category, result);
		if(result.hasErrors()){
			return viewPrefix+"create_category";
		}
		Category persistedCategory = productService.createCategory(category);
		logger.debug("Created new category with id : {} and name : {}", persistedCategory.getCategoryId(), persistedCategory.getName());
		redirectAttributes.addFlashAttribute("info", "Category created successfully");
		return "redirect:/categories";
	}
	
	@RequestMapping(value="/categories/{id}", method=RequestMethod.GET)
	public String editCategoryForm(@PathVariable Integer id, Model model) {
		Category category = productService.getCategoryById(id);
		model.addAttribute("category",category);
		return viewPrefix+"edit_category";
	}
	
	@RequestMapping(value="/categories/{id}", method=RequestMethod.POST)
	public String updateCategory(Category category, Model model, RedirectAttributes redirectAttributes) {
		Category persistedCategory = productService.updateCategory(category);
		logger.debug("Updated category with id : {} and name : {}", persistedCategory.getCategoryId(), persistedCategory.getName());
		redirectAttributes.addFlashAttribute("info", "Category updated successfully");
		return "redirect:/categories";
	}

}
