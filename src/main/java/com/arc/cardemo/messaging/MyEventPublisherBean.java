package com.arc.cardemo.messaging;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyEventPublisherBean implements ApplicationEventPublisherAware {
	ApplicationEventPublisher publisher;

    public void sendMsg (String msg) {
        publisher.publishEvent(new MyEvent(msg));
    }

    @Override
    public void setApplicationEventPublisher (
                        ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
    
}
