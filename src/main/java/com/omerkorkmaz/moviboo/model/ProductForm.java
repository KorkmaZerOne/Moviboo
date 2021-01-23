package com.omerkorkmaz.moviboo.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;


@Data
public class ProductForm 
{
	private int id;
	@NotNull
	private String sku;
	@NotNull
	private String name;
	private String description;
	@NotNull
	private double price;
	private String imageUrl;
	private MultipartFile image;
	private boolean disabled;
	@NotNull
	private int categoryId;
	@NotNull
	private int subCategoryId;

	public Product toProduct() {
		Product p = new Product();
		p.setProductId(id);
		p.setTitle(name);
		p.setDescription(description);
		p.setDisabled(disabled);
		p.setPrice(price);
		p.setSku(sku);
		Category category = new Category();
		category.setCategoryId(categoryId);
		p.setCategory(category );
		//p.setImageUrl(WebUtils.IMAGES_PREFIX+id+".jpg");
		return p;
	}
	
	public static ProductForm fromProduct(Product product)
	{
		ProductForm p = new ProductForm();
		p.setId(product.getProductId());
		p.setName(product.getTitle());
		p.setDescription(product.getDescription());
		p.setDisabled(product.isDisabled());
		p.setPrice(product.getPrice());
		p.setSku(product.getSku());
		p.setCategoryId(product.getCategory().getCategoryId());
		//p.setImageUrl(WebUtils.IMAGES_PREFIX+product.getId()+".jpg");
		return p;
	}
}
