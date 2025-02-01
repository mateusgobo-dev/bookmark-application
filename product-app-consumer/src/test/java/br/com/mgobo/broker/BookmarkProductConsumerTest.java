package br.com.mgobo.broker;

import br.com.mgobo.BaseIntegratedTest;
import br.com.mgobo.dto.BookmarkProductCustomerDto;
import br.com.mgobo.parser.BookmarkProductCustomerDeserializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLOutput;

import static br.com.mgobo.config.ProductAppConsumerConfig.*;
import static br.com.mgobo.parser.BookmarkProductCustomerDeserializer.*;

@ComponentScan(value = "br.com.mgobo*")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookmarkProductConsumerTest extends BaseIntegratedTest {

    private static final RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.8.0-management"));
    private static RabbitTemplate rabbitTemplate;

    @Autowired
    private BookmarkProductConsumer bookmarkProductConsumer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String bookmarkTable = "CREATE TABLE bookmark_product (\n" +
            "    id BIGSERIAL PRIMARY KEY,\n" +
            "    user_id BIGINT NOT NULL,\n" +
            "    product_id BIGINT NOT NULL\n" +
            ")";
    private final String bookmarkSequence = "CREATE SEQUENCE bookmark_product_seq START 1";
    private final String bookmarkAlterTable = "ALTER TABLE bookmark_product ALTER COLUMN id SET DEFAULT nextval('bookmark_product_seq')";

    @BeforeAll
    public static void setup() {
        postgreSQLContainer.start();
        rabbitMQContainer.start();
        setupQueues();
    }

    @AfterAll
    public static void stopRabbitMQContainer() {
        postgreSQLContainer.stop();
        rabbitMQContainer.stop();
    }

    private  static void setupQueues() {
        try {
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
            connectionFactory.setHost(rabbitMQContainer.getContainerIpAddress());
            connectionFactory.setPort(rabbitMQContainer.getFirstMappedPort());

            rabbitTemplate = new RabbitTemplate(connectionFactory);
            RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
            Queue q = new Queue(QUEUE_NAME, true, false, false);
            Queue qEmail = new Queue(QUEUE_NAME_MAIL, true, false, false);
            DirectExchange d = new DirectExchange(EXCHANGE_NAME);
            Binding binding = BindingBuilder.bind(q).to(d).with(ROUTING_KEY);
            Binding bindingEmail = BindingBuilder.bind(qEmail).to(d).with(ROUTING_KEY_MAIL);

            rabbitAdmin.declareQueue(q);
            rabbitAdmin.declareQueue(qEmail);
            rabbitAdmin.declareExchange(d);
            rabbitAdmin.declareBinding(binding);
            rabbitAdmin.declareBinding(bindingEmail);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void consumerMessageTest() {
        Assertions.assertThat(rabbitMQContainer.isRunning()).isTrue();
        System.out.println("RabbitMQ Host: " + rabbitMQContainer.getHost());
        System.out.println("AMQP Port: " + rabbitMQContainer.getAmqpPort());
        System.out.println("Management Port: " + rabbitMQContainer.getHttpPort());

        this.jdbcTemplate.execute(bookmarkTable);
        this.jdbcTemplate.execute(bookmarkSequence);
        this.jdbcTemplate.execute(bookmarkAlterTable);

        BookmarkProductCustomerDto bookmarkProductCustomerDto = new BookmarkProductCustomerDto(1l,1l, "mateusgobo@gmail.com", true);
        String value = toJson.apply(bookmarkProductCustomerDto);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, value);
        String receivedMessage = (String) rabbitTemplate.receiveAndConvert(QUEUE_NAME);
        System.out.println(receivedMessage);
    }
}
