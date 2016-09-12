package com.hcsc.bluechip.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcsc.bluechip.dto.MethodDetailsDto;
import com.hcsc.bluechip.entity.MethodDetails;
import com.hcsc.bluechip.repo.MethodDetailsJPA;
import com.hcsc.bluechip.utils.ModelEntityMapper;

@Service
public class MethodService {

	 @Autowired
	 MethodDetailsJPA methodDetailsJPA;
	 
	public MethodDetailsDto saveMethodDetails(MethodDetailsDto reqObject){
		MethodDetails entityObj=(MethodDetails) ModelEntityMapper.converObjectToPoJo(reqObject, MethodDetails.class);
		methodDetailsJPA.saveAndFlush(entityObj);
		reqObject.setId(entityObj.getId());
		return reqObject;
	}
	
	
	public List<MethodDetailsDto> getMethodName(String methodName) {
		List<MethodDetails> entityList = methodDetailsJPA.findByMethodName(methodName);
		List<MethodDetailsDto> returnList = new ArrayList<MethodDetailsDto>();
		for (MethodDetails entity : entityList) {
			MethodDetailsDto methodDto = (MethodDetailsDto) ModelEntityMapper.converObjectToPoJo(entity,
					MethodDetailsDto.class);
			returnList.add(methodDto);
		}
		return returnList;
	}


	public List<MethodDetailsDto> getWildCardMethodNames(String methodName) {
		// TODO Auto-generated method stub
		List<MethodDetails> entityList = methodDetailsJPA.findByMethodNameContaining(methodName);
		List<MethodDetailsDto> returnList = new ArrayList<MethodDetailsDto>();
		for (MethodDetails entity : entityList) {
			MethodDetailsDto methodDto = (MethodDetailsDto) ModelEntityMapper.converObjectToPoJo(entity,
					MethodDetailsDto.class);
			returnList.add(methodDto);
		}
		return returnList;
	}
	
}
