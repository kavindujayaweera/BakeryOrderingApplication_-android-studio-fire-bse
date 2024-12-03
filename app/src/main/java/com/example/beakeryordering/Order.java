package com.example.beakeryordering;

public class Order {
    private String productId;
    private int quantity;
    private String orderStatus;

    public Order(String productId, int quantity, String orderStatus) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
