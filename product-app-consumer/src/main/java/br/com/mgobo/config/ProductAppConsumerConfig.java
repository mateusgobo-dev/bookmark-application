package br.com.mgobo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import static br.com.mgobo.config.SettingsBroker.*;

@Configuration
public class ProductAppConsumerConfig {

     public static final String EXCHANGE_NAME = "bookmark-product-exchange";
     public static final String QUEUE_NAME = "bookmark-product-queue";
     public static final String ROUTING_KEY = "bookmark-product";

     private RabbitAdmin rabbitAdmin;
     private final RabbitTemplate rabbitTemplate;
     public ProductAppConsumerConfig(RabbitTemplate rabbitTemplate) {
          this.rabbitTemplate = rabbitTemplate;
     }

     @EventListener(ApplicationReadyEvent.class)
     public void init() {
          Queue q = this.queue();
          DirectExchange d = this.directExchange();
          Binding binding = this.binding(q, d);

          this.rabbitAdmin = rabbitAdmin();
          this.rabbitAdmin.declareQueue(q);
          this.rabbitAdmin.declareExchange(d);
          this.rabbitAdmin.declareBinding(binding);
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
     public DirectExchange directExchange()  {
          try {
               return new DirectExchange(EXCHANGE_NAME);
          }catch (Exception e){
               throw new RuntimeException(e);
          }
     }

     @Bean
     public Binding binding(Queue queue, DirectExchange directExchange)  {
          try {
               return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY);
          }catch (Exception e){
               throw new RuntimeException(e);
          }
     }
}
