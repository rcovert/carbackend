package com.arc.cardemo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arc.cardemo.messaging.MyEventPublisherBean;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ExampleFilter implements Filter {
	
	@Autowired
	MyEventPublisherBean myEventPublisherBean;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		log.info("from filter: " + ((HttpServletRequest) servletRequest).getMethod());
		String data = ((HttpServletRequest) servletRequest).getMethod();
		myEventPublisherBean.sendMsg("To Listener:  Method received: " + data);

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
	}
}

