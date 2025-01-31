package br.com.mgobo.api.config;

import br.com.mgobo.api.entities.Product;
import br.com.mgobo.api.repositories.ProductRepository;
import br.com.mgobo.web.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.List;

import static br.com.mgobo.api.parser.ProductDeserialize.deserialize;

@Slf4j
@Primary
@ComponentScan(value = "br.com.mgobo.*")
@Configuration
@RequiredArgsConstructor
public class ConfigProductApp {

    private final ProductRepository productRepository;
    private final Path path = Path.of(System.getProperty("user.dir").concat("/product-app"), "src", "test", "resources", "products");

    @EventListener(classes = ApplicationStartedEvent.class)
    public void applicationStarted(ApplicationStartedEvent event) throws Exception {
        System.out.println("Product app has been started at %s seconds".formatted(event.getTimeTaken().getSeconds()));
        File file = new File(path.toFile(), "productsDto.dat");
        try (ObjectInputStream iis = new ObjectInputStream(new FileInputStream(file))) {
            if(this.productRepository.findAll().isEmpty()) {
                List<ProductDto> values = (List<ProductDto>) iis.readObject();
                values.stream().forEach(p -> {
                    Product product = deserialize.apply(p);
                    product.setId(null);
                    this.productRepository.saveAndFlush(product);
                });
            }else{
                log.info("Lista de produtos já carregada...");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
