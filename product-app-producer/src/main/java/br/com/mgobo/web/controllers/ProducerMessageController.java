package br.com.mgobo.web.controllers;

import br.com.mgobo.parser.BookmarkProductCustomerDeserializer;
import br.com.mgobo.web.controllers.dto.BookmarkProductCustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static br.com.mgobo.config.ProductAppProducerConfig.*;
import static br.com.mgobo.parser.BookmarkProductCustomerDeserializer.*;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/v1/producer")
public class ProducerMessageController {

    private final RabbitTemplate rabbitTemplate;

    public ProducerMessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody String message) {
        try {
            BookmarkProductCustomerDto bookmarkProductCustomerDto  = (BookmarkProductCustomerDto) toObject.deserialize(message, BookmarkProductCustomerDto.class);
            this.rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
            return ResponseEntity.created(new URI("/api/v1/bookmark/customer/%s".formatted(bookmarkProductCustomerDto.idCustomer()))).body("Favorito armazenado com sucesso!"
                    .formatted(bookmarkProductCustomerDto.idProduct(),bookmarkProductCustomerDto.idCustomer()));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
