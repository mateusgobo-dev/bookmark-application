package br.com.mgobo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductAppProducerConfig {

    public static final String EXCHANGE_NAME = "bookmark-product-exchange";
    public static final String QUEUE_NAME = "bookmark-product-queue";
    public static final String ROUTING_KEY = "bookmark-product";


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
