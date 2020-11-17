package com.arc.cardemo.domain;

import java.time.Instant;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;

import com.arc.cardemo.messaging.MyEventPublisherBean;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditListener {
	
	@Autowired
	MyEventPublisherBean myEventPublisherBean;
	
	@PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyOperation(Object object) { 
		//log.info("change event happening on JPA...");
		String data = "Change data event @ " + Instant.now();
		myEventPublisherBean.sendMsg("To Listener:  Method received: " + data);
	}
    
}

