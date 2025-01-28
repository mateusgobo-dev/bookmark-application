package br.com.mgobo.api.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@ComponentScan(value = "br.com.mgobo.*")
@Configuration
public class ConfigProductApp {

    @EventListener(classes = ApplicationStartedEvent.class)
    public void applicationStarted(ApplicationStartedEvent event) {
        System.out.println("Product app has been started at %s seconds".formatted(event.getTimeTaken().getSeconds()));
    }
}
