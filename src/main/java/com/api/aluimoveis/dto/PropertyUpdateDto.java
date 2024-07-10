package com.api.aluimoveis.dto;

import com.api.aluimoveis.entity.Property;
import com.api.aluimoveis.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PropertyUpdateDto {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @Positive(message = "Price must be positive")
    private float price;

    private Boolean available;

    private List<@Pattern(regexp = "image/jpeg", message = "Only JPG images are allowed") MultipartFile> images;

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public PropertyUpdateDto(String title, String description, String address, float price, Boolean available, List<MultipartFile> images) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.price = price;
        this.available = available;
        this.images = images;
    }
    public PropertyUpdateDto(){}
}
