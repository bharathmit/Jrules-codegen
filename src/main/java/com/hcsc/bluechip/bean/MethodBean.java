package com.hcsc.bluechip.bean;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.html.HtmlDataTable;

import org.apache.commons.collections.CollectionUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import com.hcsc.bluechip.dto.MethodDetailsDto;
import com.hcsc.bluechip.entity.MethodDetails;
import com.hcsc.bluechip.service.MethodService;



@ManagedBean
@SessionScoped
@Log4j
public class MethodBean {

	
	
	
	@ManagedProperty(value="#{methodService}")
	@Getter @Setter
	MethodService methodService;
	
	@Getter @Setter
	MethodDetailsDto reqObject=new MethodDetailsDto();
	
	@Getter @Setter
	private List<MethodDetailsDto> methodNamesList =new ArrayList<MethodDetailsDto>();
	
	/*@Getter @Setter
	private Set<MethodDetailsDto> filteredMethodNamesSet =new LinkedHashSet<MethodDetailsDto>();*/
	
	@Getter @Setter
	private List<MethodDetailsDto> filteredMethodNamesList =new ArrayList<MethodDetailsDto>();
	
	@Getter @Setter
	private List<MethodDetailsDto> tempList =new ArrayList<MethodDetailsDto>();
	
	@Getter @Setter
	private HtmlDataTable dataTable;
	
	@Getter @Setter
	private Boolean renderCheck = null;
	
	public String saveMethod(){
		methodService.saveMethodDetails(reqObject);
		reqObject=new MethodDetailsDto();
		return "Save Completed";
	}
	
	public String findMethod(){
		
		List<MethodDetailsDto> list=methodService.getMethodName(reqObject.getMethodName());		
		if(!CollectionUtils.isEmpty(list)){
			reqObject=list.get(0);
		}
		return "Method found";
	}
	
	public String wildCardSearchMethod(){
		
		List<MethodDetailsDto> list=methodService.getWildCardMethodNames(reqObject.getSearchString());
		methodNamesList= new ArrayList<MethodDetailsDto>();
		methodNamesList.addAll(list);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getMethodName());
		}
		if(!CollectionUtils.isEmpty(list)){
			reqObject=list.get(0);
		}
		return "Method found";
	}
	
	public List<MethodDetailsDto> addMethodToList(){
		
		List<MethodDetailsDto> list=methodService.getMethodName(reqObject.getMethodName());		
		if(!CollectionUtils.isEmpty(list)){
			methodNamesList.addAll(list);
		}
		return methodNamesList;
	}
	
		public String filterMethodName(){
		
			boolean flag=false;
			if (filteredMethodNamesList.isEmpty()) {
				filteredMethodNamesList.addAll(tempList);
				tempList.removeAll(tempList);
			} else {
				for (MethodDetailsDto tempListObject : tempList) {
					flag=false;
					for(MethodDetailsDto filterListObject: filteredMethodNamesList){
						if(tempListObject.getId()==filterListObject.getId()){
							flag=true;
						}
					}
					if(!flag){
						filteredMethodNamesList.add(tempListObject);
					}
				}
				
				tempList.removeAll(tempList);
				
			}
			renderCheck=false;
			
			
			
		return "Method found";
	}
		
		public String deleteMethodName(){
			
			if (!filteredMethodNamesList.isEmpty()) {
				
				filteredMethodNamesList.removeAll(tempList);
				tempList.removeAll(tempList);
				renderCheck=false;
				if(filteredMethodNamesList.isEmpty()){
					renderCheck=null;
				}
			}
			
		return "Method deleted";
	}
		public void onRowSelect(SelectEvent event) {
	        reqObject=(MethodDetailsDto) event.getObject();
	        renderCheck=true;
	    }
	 
	    public void onRowUnselect(UnselectEvent event) {
	        /*FacesMessage msg = new FacesMessage("Car Unselected", ((Car) event.getObject()).getId());
	        FacesContext.getCurrentInstance().addMessage(null, msg);*/
	    	reqObject=new MethodDetailsDto();
	    	renderCheck=false;
	    }

}
