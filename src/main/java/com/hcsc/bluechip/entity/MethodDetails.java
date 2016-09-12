package com.hcsc.bluechip.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="method_details")
public class MethodDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter	@Setter	
	private Long id;
	
	@Column(nullable=false,unique=true)
	@Getter	@Setter	
	private String methodName;
	
	@Column(nullable=false,length = 65535,columnDefinition="Text")
	@Getter	@Setter	
	private String details;
	

}
