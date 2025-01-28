package br.com.mgobo.web.mapper;

import br.com.mgobo.api.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    br.com.mgobo.web.dto.ProductDto toDto(Product product);
    Product toProduct(br.com.mgobo.web.dto.ProductDto productDto);
}
