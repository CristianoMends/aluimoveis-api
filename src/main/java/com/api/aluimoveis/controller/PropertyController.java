package com.api.aluimoveis.controller;

import com.api.aluimoveis.dto.PropertyDto;
import com.api.aluimoveis.dto.PropertyUpdateDto;
import com.api.aluimoveis.dto.PropertyViewDto;
import com.api.aluimoveis.entity.Property;
import com.api.aluimoveis.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
@Tag(name = "Property Controller", description = "Contém endpoints para gerenciamento de propriedades")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    @Operation(summary = "Lista todas as propriedades", description = "Retorna uma lista de todas as propriedades")
    public ResponseEntity<List<PropertyViewDto>> getAllProperties() {
        List<PropertyViewDto> properties =
                 propertyService.getAllProperties()
                .stream()
                .map(this::propertyToView)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(properties);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtém uma propriedade por ID", description = "Retorna uma propriedade específica pelo seu ID")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        Property property = propertyService.getPropertyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(property);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Cria uma nova propriedade", description = "Cria uma nova propriedade", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> createProperty(@ModelAttribute @Valid PropertyDto property) {
        Property createdProperty = propertyService.createProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED + " : Property created successfully");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Atualiza uma propriedade", description = "Atualiza uma propriedade existente")
    public ResponseEntity<PropertyViewDto> updateProperty(@PathVariable Long id, @ModelAttribute @Valid PropertyUpdateDto property) {
        Property updatedProperty = propertyService.updateProperty(id, property);
        return ResponseEntity.status(HttpStatus.OK).body(propertyToView(updatedProperty));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma propriedade", description = "Deleta uma propriedade pelo seu ID")
    public ResponseEntity<String> deleteProperty(@PathVariable Long id) {
        var response = propertyService.deleteProperty(id);
        if (!response.equals("Property deleted successfully")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private PropertyViewDto propertyToView(Property p) {
        return new PropertyViewDto(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getAddress(),
                p.getPrice(),
                p.isAvailable(),
                p.getImages(),
                p.getOwner().getId()
        );
    }
}
