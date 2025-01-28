package br.com.mgobo.web.dto;

public record RatingDto (Long id, double rate, int count, long productId) {
    public RatingDto{
        if(id == null){id = null;}
    }
}
