package com.hcsc.bluechip.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.hcsc.bluechip.config.AppConfig;
import com.hcsc.bluechip.dto.MethodDetailsDto;
import com.hcsc.bluechip.service.MethodService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@ContextConfiguration(classes={AppConfig.class}, 
loader=AnnotationConfigContextLoader.class)
public class ServiceTest {

	
	@Autowired
	MethodService methodService;
	
	@Test
	public void saveMethodTest(){
		MethodDetailsDto reqObject=new MethodDetailsDto();
		reqObject.setMethodName("Bharath");
		reqObject.setDetails("Simbu");
		reqObject = methodService.saveMethodDetails(reqObject);
		System.out.println("Object" + reqObject.getDetails() + reqObject.getMethodName() + reqObject.getId());
	}
	
	@Test
	public void getMethodTest(){		
		List<MethodDetailsDto> returnList = methodService.getMethodName("Bharath");
		System.out.println("Object" + returnList.get(0).getMethodName());
	}
}
