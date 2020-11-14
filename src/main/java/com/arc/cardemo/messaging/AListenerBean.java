package com.arc.cardemo.messaging;

import org.springframework.context.event.EventListener;

public class AListenerBean {
	
	@EventListener
    public void onMyEvent (MyEvent event) {
        System.out.println("event received: " + event.getMsg());

    }
}
