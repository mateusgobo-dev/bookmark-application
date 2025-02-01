package br.com.mgobo.config;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;

@Configuration
public class ProductAppConsumerConfig {

     public static final String EXCHANGE_NAME = "bookmark-product-exchange";
     public static final String QUEUE_NAME = "bookmark-product-queue";
     public static final String QUEUE_NAME_MAIL = "bookmark-product-queue-email";
     public static final String ROUTING_KEY = "bookmark-product";
     public static final String ROUTING_KEY_MAIL = "bookmark-product-mail";

     private RabbitAdmin rabbitAdmin;
     private final RabbitTemplate rabbitTemplate;
     public ProductAppConsumerConfig(RabbitTemplate rabbitTemplate) {
          this.rabbitTemplate = rabbitTemplate;
     }

     @EventListener(ApplicationStartingEvent.class)
     public void init(ApplicationStartedEvent event) {
          Queue q = this.queue();
          Queue qEmail = this.queueEmail();
          DirectExchange d = this.directExchange();
          Binding binding = BindingBuilder.bind(q).to(d).with(ROUTING_KEY);
          Binding bindingEmail = BindingBuilder.bind(qEmail).to(d).with(ROUTING_KEY_MAIL);

          this.rabbitAdmin = rabbitAdmin();
          this.rabbitAdmin.declareQueue(q);
          this.rabbitAdmin.declareQueue(qEmail);
          this.rabbitAdmin.declareExchange(d);
          this.rabbitAdmin.declareBinding(binding);
          this.rabbitAdmin.declareBinding(bindingEmail);
          LoggerFactory.getLogger(ProductAppConsumerConfig.class).info("Starting product app consumer starting at %s...".formatted(event.getTimeTaken().getSeconds()));
     }

     @Bean
     public RabbitAdmin rabbitAdmin() {
          return new RabbitAdmin(this.rabbitTemplate.getConnectionFactory());
     }

     @Bean
     public Queue queue()  {
          try {
               return new Queue(QUEUE_NAME, true, false, false);
          }catch (Exception e){
               throw new RuntimeException(e);
          }
     }

     @Bean
     public Queue queueEmail()  {
          try {
               return new Queue(QUEUE_NAME_MAIL, true, false, false);
          }catch (Exception e){
               throw new RuntimeException(e);
          }
     }

     @Bean
     public DirectExchange directExchange()  {
          try {
               return new DirectExchange(EXCHANGE_NAME);
          }catch (Exception e){
               throw new RuntimeException(e);
          }
     }
}
