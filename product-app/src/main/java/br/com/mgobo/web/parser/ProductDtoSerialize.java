package br.com.mgobo.web.parser;

import br.com.mgobo.api.entities.Product;
import br.com.mgobo.web.dto.ProductDto;
import br.com.mgobo.web.mapper.ProductMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.util.function.Function;

import static br.com.mgobo.web.mapper.ProductMapper.INSTANCE;

@FunctionalInterface
public interface ProductDtoSerialize {
    String parse(ProductDto productDto, Logger logger);

    ProductDtoSerialize productDtoToJson = (productDto, logger) -> {
        try {
            return new ObjectMapper().writeValueAsString(productDto);
        }catch (JsonProcessingException e){
           logger.error(e.getMessage());
           throw new RuntimeException(e);
        }
    };
    Function<Product, ProductDto> serialize = product -> INSTANCE.toDto(product);
}
