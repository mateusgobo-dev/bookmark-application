package br.com.mgobo.api.parser;

import br.com.mgobo.api.entities.Product;
import br.com.mgobo.web.dto.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.util.function.Function;

import static br.com.mgobo.web.mapper.ProductMapper.INSTANCE;

@FunctionalInterface
public interface ProductDeserialize {
    String parse(Product product, Logger logger);

    ProductDeserialize productToJson = (product, logger) -> {
        try {
            return new ObjectMapper().writeValueAsString(product);
        }catch (JsonProcessingException e){
           logger.error(e.getMessage());
           throw new RuntimeException(e);
        }
    };
    Function<ProductDto, Product> deserialize = productDto -> INSTANCE.toProduct(productDto);
}
