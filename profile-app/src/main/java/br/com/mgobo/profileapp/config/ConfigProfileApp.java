package br.com.mgobo.profileapp.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@ComponentScan(value = "br.com.mgobo.profileapp.*")
public class ConfigProfileApp {
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("Application ready");
    }
}
