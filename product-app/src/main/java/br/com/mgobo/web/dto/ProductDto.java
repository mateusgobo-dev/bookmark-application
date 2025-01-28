package br.com.mgobo.web.dto;

public record ProductDto(
        Long id,
        String title,
        double price,
        String description,
        String category,
        String image,
        RatingDto rating
) {
    public ProductDto{
        if(id == null) {id = null;}
    }
}
