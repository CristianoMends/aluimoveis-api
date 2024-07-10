package com.api.aluimoveis.dto;

import com.api.aluimoveis.entity.Property;
import com.api.aluimoveis.entity.User;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PropertyDto {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @Positive(message = "Price must be positive")
    private float price;

    private boolean available;

    private List<@Pattern(regexp = "image/jpeg", message = "Only JPG images are allowed") MultipartFile> images;

    @NotNull(message = "Owner ID is mandatory")
    private Long ownerId;

    public Property toEntity(List<String> images, User owner) {
        return new Property(title, description, address, price, available, images, owner);
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public PropertyDto(String title, String description, String address, float price, boolean available, List<@Pattern(regexp = "image/jpeg", message = "Only JPG images are allowed") MultipartFile> images, Long ownerId) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.price = price;
        this.available = available;
        this.images = images;
        this.ownerId = ownerId;
    }
}
