package com.arc.cardemo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LoggingHelper {
	private static final Logger logger = LoggerFactory.getLogger(LoggingHelper.class);
	
	
	public void logData(String data) {
		logger.info(data);
	}

}
