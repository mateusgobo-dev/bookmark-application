package br.com.mgobo.broker;

import br.com.mgobo.api.jdbc.BookmarkProductCustomerService;
import br.com.mgobo.dto.BookmarkProductCustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static br.com.mgobo.config.ProductAppConsumerConfig.*;
import static br.com.mgobo.parser.BookmarkProductCustomerDeserializer.toObject;

@Component
public class BookmarkProductConsumer {

    private final Logger logger = LoggerFactory.getLogger(BookmarkProductConsumer.class);
    private final RabbitTemplate rabbitTemplate;

    private final BookmarkProductCustomerService bookmarkProductCustomerService;
    public BookmarkProductConsumer(BookmarkProductCustomerService bookmarkProductCustomerService, RabbitTemplate rabbitTemplate) {
        this.bookmarkProductCustomerService = bookmarkProductCustomerService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void bookmarkProductSave(String message) {
        try {
            String msg = "";
            BookmarkProductCustomerDto bookmarkProductCustomerDto = (BookmarkProductCustomerDto) toObject.deserialize(message, BookmarkProductCustomerDto.class);
            if(Objects.nonNull(bookmarkProductCustomerDto)) {
                if (bookmarkProductCustomerDto.add()) {
                    msg = this.bookmarkProductCustomerService.save(bookmarkProductCustomerDto);
                    this.rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_MAIL, message);
                }else{
                    msg = this.bookmarkProductCustomerService.remove(bookmarkProductCustomerDto);
                }
            }
            logger.info(msg);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @RabbitListener(queues = QUEUE_NAME_MAIL)
    public void bookmarkProductMail(String message) {
        try {
            BookmarkProductCustomerDto bookmarkProductCustomerDto = (BookmarkProductCustomerDto) toObject.deserialize(message, BookmarkProductCustomerDto.class);
            if(Objects.nonNull(bookmarkProductCustomerDto)) {
                this.bookmarkProductCustomerService.sendEmail(bookmarkProductCustomerDto);
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
