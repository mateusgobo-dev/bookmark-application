package br.com.mgobo.broker;

import br.com.mgobo.api.jdbc.BookmarkProductCustomerService;
import br.com.mgobo.dto.BookmarkProductCustomerDto;
import br.com.mgobo.parser.BookmarkProductCustomerDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static br.com.mgobo.config.SettingsBroker.QUEUE_NAME;

@Component
public class BookmarkProductConsumer {

    private final Logger logger = LoggerFactory.getLogger(BookmarkProductConsumer.class);

    private final BookmarkProductCustomerService bookmarkProductCustomerService;
    public BookmarkProductConsumer(BookmarkProductCustomerService bookmarkProductCustomerService) {
        this.bookmarkProductCustomerService = bookmarkProductCustomerService;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void bookmarkProductSave(String message) {
        try {
            BookmarkProductCustomerDto bookmarkProductCustomerDto = (BookmarkProductCustomerDto) BookmarkProductCustomerDeserializer.toObject.deserialize(message, BookmarkProductCustomerDto.class);
            String msg = this.bookmarkProductCustomerService.save(bookmarkProductCustomerDto);
            logger.info(msg);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
