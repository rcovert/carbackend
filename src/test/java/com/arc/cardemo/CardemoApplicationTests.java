package com.arc.cardemo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.arc.cardemo.web.CarController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CardemoApplicationTests {

	@Autowired
	private CarController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	public CardemoApplicationTests() {
		super();
	}

	@Test
	public void method() {
	       org.junit.Assert.assertTrue( new ArrayList().isEmpty() );
	    }


}
