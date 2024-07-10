package com.api.aluimoveis.service.impl;

import com.api.aluimoveis.dto.PropertyDto;
import com.api.aluimoveis.dto.PropertySearchDto;
import com.api.aluimoveis.dto.PropertyUpdateDto;
import com.api.aluimoveis.dto.UserDto;
import com.api.aluimoveis.entity.Property;
import com.api.aluimoveis.entity.User;
import com.api.aluimoveis.handler.BusinessException;
import com.api.aluimoveis.repository.PropertyRepository;
import com.api.aluimoveis.service.PropertyService;
import com.api.aluimoveis.service.UserService;
import com.api.aluimoveis.utility.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Util util;

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }

    @Override
    public Property createProperty(PropertyDto property) {
        Optional<User> owner = userService.findById(property.getOwnerId());
        if (owner.isEmpty()) {
            throw new BusinessException("Owner user not found");
        }
        ArrayList<String> images = new ArrayList<>();
        if (property.getImages() != null) {
            for (MultipartFile a : property.getImages()) {
                String url = util.uploadFile(a);
                images.add(url);
            }
        }

        return propertyRepository.save(property.toEntity(images, owner.get()));
    }

    public Property updateProperty(Long id, PropertyUpdateDto propertyDto) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new BusinessException("Property not found"));

        if (propertyDto.getTitle() != null) {
            property.setTitle(propertyDto.getTitle());
        }

        if (propertyDto.getDescription() != null) {
            property.setDescription(propertyDto.getDescription());
        }

        if (propertyDto.getAddress() != null) {
            property.setAddress(propertyDto.getAddress());
        }

        if (propertyDto.getImages() != null) {
            for (String img : property.getImages()) {//deletando as imagens atuais
                this.util.deleteFile(img);
            }
            ArrayList<String> images = new ArrayList<>();
            for (MultipartFile file : propertyDto.getImages()) {//fazendo upload das novas imagens
                String url = this.util.uploadFile(file);
                images.add(url);
            }
            property.setImages(images);
        }
        if (propertyDto.isAvailable() != null) {
            property.setAvailable(propertyDto.isAvailable());
        }

        return propertyRepository.save(property);
    }

    @Override
    public String deleteProperty(Long id) {
        var property = this.propertyRepository.findById(id);
        if (property.isEmpty()) {
            return "Property with id " + id + " not  found";
        }
        for (String img : property.get().getImages()) {
            var success = this.util.deleteFile(img);
            if (!success) {
                return "error when deleting file in azure blob";
            }
        }
        propertyRepository.deleteById(id);
        return "Property deleted successfully";
    }

    @Override
    public List<Property> searchProperties(PropertySearchDto searchDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> query = criteriaBuilder.createQuery(Property.class);
        Root<Property> root = query.from(Property.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchDto.getTitle() != null && !searchDto.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + searchDto.getTitle() + "%"));
        }

        if (searchDto.getAddress() != null && !searchDto.getAddress().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("address"), "%" + searchDto.getAddress() + "%"));
        }

        if (searchDto.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchDto.getMinPrice()));
        }

        if (searchDto.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchDto.getMaxPrice()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
