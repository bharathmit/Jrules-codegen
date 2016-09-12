package com.hcsc.bluechip.dto;

import java.io.Serializable;


import lombok.Getter;
import lombok.Setter;

public class MethodDetailsDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	@Setter	
	private Long id;
	
	@Getter	@Setter	
	private String methodName;
	
	@Getter	@Setter	
	private String details;
	
	@Getter	@Setter	
	private String searchString;
	
	@Getter	@Setter	
	private Boolean selected;
	
	
}
