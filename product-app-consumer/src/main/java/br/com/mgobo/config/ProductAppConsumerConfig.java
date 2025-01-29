package br.com.mgobo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static br.com.mgobo.config.SettingsBroker.*;

@Configuration
public class ProductAppConsumerConfig {

     @Bean
     public Queue queue()  {
          return new Queue(QUEUE_NAME, true, false, false);
     }

     @Bean
     public DirectExchange directExchange()  {
          return new DirectExchange(EXCHANGE_NAME);
     }

     @Bean
     public Binding binding(Queue queue, DirectExchange directExchange)  {
          return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY);
     }
}
