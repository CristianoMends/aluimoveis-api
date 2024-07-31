package com.api.aluimoveis.controller;

import com.api.aluimoveis.dto.PropertyDto;
import com.api.aluimoveis.dto.PropertySearchDto;
import com.api.aluimoveis.dto.PropertyViewDto;
import com.api.aluimoveis.dto.UserViewDto;
import com.api.aluimoveis.entity.Property;
import com.api.aluimoveis.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
@Tag(name = "Post Controller", description = "Contém endpoints para visualização de publicações")
public class PostController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("/search")
    @Operation(summary = "Listar todos as publicações", description = "Retorna uma lista de todas as publicações")
    public ResponseEntity<List<PropertyViewDto>> searchProperties(@ModelAttribute PropertySearchDto searchDto) {
        var properties
                = propertyService
                .searchProperties(searchDto)
                .stream()
                .map(this::propertyToView)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(properties);
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
                p.getOwner().getId(),
                p.getCreationDate()
        );
    }


}
