package com.api.aluimoveis.dto;

public class PropertySearchDto {
    private String title;
    private String address;
    private Float minPrice;
    private Float maxPrice;

    // Construtores, getters e setters
    public PropertySearchDto() {}

    public PropertySearchDto(String title, String address, Float minPrice, Float maxPrice) {
        this.title = title;
        this.address = address;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }
}
