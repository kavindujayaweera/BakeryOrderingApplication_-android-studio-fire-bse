package com.example.beakeryordering;

public class Feedback {
    private String productId;
    private int rating;
    private String comment;

    public Feedback(String productId, int rating, String comment) {
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getProductId() {
        return productId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
