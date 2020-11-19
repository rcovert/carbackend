package com.arc.cardemo.domain;

import java.time.Instant;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;

import com.arc.cardemo.messaging.MyEventPublisherBean;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditListener {
	
	@Autowired
	MyEventPublisherBean myEventPublisherBean;
	
	@PostPersist
	private void beforeAnySave(Object object) { 
		myEventPublisherBean.sendMsg("Change data event: POST @ ");
	}
    
	@PostUpdate
	private void beforeAnyUpdate(Object object) { 
		myEventPublisherBean.sendMsg("Change data event: PUT @ ");
	}
    
    @PostRemove
    private void beforeAnyDelete(Object object) { 
		//log.info("change event happening on JPA...");
		String data = "Change data event: DELETE @ ";
		myEventPublisherBean.sendMsg("Change data event: DELETE @ ");
	}
    
}

