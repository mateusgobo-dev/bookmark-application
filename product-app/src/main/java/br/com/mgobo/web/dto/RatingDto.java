package br.com.mgobo.web.dto;

import java.io.Serializable;

public record RatingDto (Long id, double rate, int count, long productId) implements Serializable {
    private static final long serialVersionUID = 1L;
    public RatingDto{
        if(id == null){id = null;}
    }
}
