package com.api.aluimoveis.service;

import com.api.aluimoveis.dto.PropertyDto;
import com.api.aluimoveis.dto.PropertySearchDto;
import com.api.aluimoveis.dto.PropertyUpdateDto;
import com.api.aluimoveis.entity.Property;

import java.util.List;

public interface PropertyService {
    public List<Property> getAllProperties();

    public Property getPropertyById(Long id);

    public Property createProperty(PropertyDto property);

    public Property updateProperty(Long id, PropertyUpdateDto property);

    public String deleteProperty(Long id);

    List<Property> searchProperties(PropertySearchDto searchDto);

}
