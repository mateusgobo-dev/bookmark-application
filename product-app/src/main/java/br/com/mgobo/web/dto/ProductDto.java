package br.com.mgobo.web.dto;

import java.io.Serializable;

public record ProductDto (
        Long id,
        String title,
        double price,
        String description,
        String category,
        String image,
        RatingDto rating
) implements Serializable {
    private final static  long serialVersionUID = 1L;
    public ProductDto{
        if(id == null) {id = null;}
    }
}
