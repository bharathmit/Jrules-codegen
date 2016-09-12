package com.hcsc.bluechip.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcsc.bluechip.entity.MethodDetails;

public interface MethodDetailsJPA extends JpaRepository< MethodDetails, Long>{

	public List<MethodDetails> findByMethodName(String methodName);
	
	//@Query("select * from method_details md where md.methodName like: %?1%")
	 public List<MethodDetails> findByMethodNameContaining(String methodName);
}
