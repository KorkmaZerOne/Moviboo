package com.omerkorkmaz.moviboo.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<LineItem> items;
    private Customer customer;
    private Address deliveryAddress;
    private Payment payment;

    public Cart() {
        items = new ArrayList<LineItem>();
        customer = new Customer();
        deliveryAddress = new Address();
        payment = new Payment();
    }

    public void addItem(Product product) {
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getSku().equals(product.getSku())) {
                lineItem.setQuantity(lineItem.getQuantity() + 1);
                return;
            }
        }
        LineItem item = new LineItem(product, 1);
        this.items.add(item);
    }

    public void updateItemQuantity(Product product, int quantity) {
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getSku().equals(product.getSku())) {
                lineItem.setQuantity(quantity);
            }
        }
    }

    public void removeItem(String sku) {
        LineItem item = null;
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getSku().equals(sku)) {
                item = lineItem;
                break;
            }
        }
        if (item != null) {
            items.remove(item);
        }
    }

    public void clearItems() {
        items = new ArrayList<LineItem>();
    }

    public int getItemCount() {
        int count = 0;
        for (LineItem lineItem : items) {
            count += lineItem.getQuantity();
        }
        return count;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        double amount = 0;
        for (LineItem lineItem : items) {
            amount = lineItem.getSubTotal();
            ;
        }
        return amount;
    }

}
