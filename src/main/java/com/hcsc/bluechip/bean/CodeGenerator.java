package com.hcsc.bluechip.bean;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.hcsc.bluechip.dto.MethodDetailsDto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@ManagedBean
@SessionScoped
@Log4j
public class CodeGenerator {

	/*
	 * @ManagedProperty(value="#{methodBean}")
	 * 
	 * @Getter @Setter MethodBean methodBean;
	 */

	public static int csvFileCounter = 0;
	public static int junitFileCounter = 0;
	public static int archiveFileCounter = 0;
	@Getter
	@Setter
	private int row;

	@Getter
	@Setter
	private int column;

	@Getter
	@Setter
	List<ColumnModel> columns;

	@Getter
	@Setter
	private List<ListModel> cars;

	public void createDynamicRows() {
		cars = new ArrayList<ListModel>();
		for (int i = 1; i <= row; i++) {
			cars.add(new ListModel());
		}

	}

	public void createDynamicColumns(
			List<MethodDetailsDto> filteredMethodNamesList) {

		List<String> columnHeaderList = new ArrayList<String>();
		columnHeaderList = retrieveColHdrList(filteredMethodNamesList);
		column = columnHeaderList.size();

		columns = new ArrayList<ColumnModel>();
		for (int i = 1; i <= column; i++) {
			columns.add(new ColumnModel(columnHeaderList.get(i - 1), "column"
					+ i));
		}

		
	}

	private List<String> retrieveColHdrList(
			List<MethodDetailsDto> filteredMethodNamesList) {
		List<String> returnList = new ArrayList<String>();
		try {
			String details = "";
			// Collection Not Empty
			if (CollectionUtils.isNotEmpty(filteredMethodNamesList)) {
				for (MethodDetailsDto MethodDetailsDto : filteredMethodNamesList) {
					// method Details String Not Empty
					if (StringUtils.isNotEmpty(MethodDetailsDto.getDetails())) {
						// Split the String --- java comments
						String[] list = MethodDetailsDto.getDetails().split(
								"\\/\\*");
						for (int i = 0; i < list.length; i++) {
							// Add the String -- java comments
							if (list[i].contains("*/")) {
								details = list[i].substring(0,
										list[i].indexOf("*/"));
								returnList.add(details);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}

	static public class ColumnModel implements Serializable {

		private static final long serialVersionUID = 1L;
		private String header;
		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}

	static public class ListModel {

		@Getter
		@Setter
		private String column1;
		@Getter
		@Setter
		private String column2;
		@Getter
		@Setter
		private String column3;
		@Getter
		@Setter
		private String column4;
		@Getter
		@Setter
		private String column5;
		@Getter
		@Setter
		private String column6;
		@Getter
		@Setter
		private String column7;
		@Getter
		@Setter
		private String column8;
		@Getter
		@Setter
		private String column9;
		@Getter
		@Setter
		private String column10;
		@Getter
		@Setter
		private String column11;
		@Getter
		@Setter
		private String column12;
		@Getter
		@Setter
		private String column13;
		@Getter
		@Setter
		private String column14;
		@Getter
		@Setter
		private String column15;
		@Getter
		@Setter
		private String column16;
		@Getter
		@Setter
		private String column17;
		@Getter
		@Setter
		private String column18;
		@Getter
		@Setter
		private String column19;
		@Getter
		@Setter
		private String column20;
		@Getter
		@Setter
		private String column21;
		@Getter
		@Setter
		private String column22;
		@Getter
		@Setter
		private String column23;
		@Getter
		@Setter
		private String column24;
		@Getter
		@Setter
		private String column25;

		public String getDetail() {
			
			StringBuilder sb = new StringBuilder();
			if (!StringUtils.isEmpty(column1))
				sb.append(column1);
			if (!StringUtils.isEmpty(column2))
				sb.append("," + column2);
			if (!StringUtils.isEmpty(column3))
				sb.append("," + column3);
			if (!StringUtils.isEmpty(column4))
				sb.append("," + column4);
			if (!StringUtils.isEmpty(column5))
				sb.append("," + column5);
			if (!StringUtils.isEmpty(column6))
				sb.append("," + column6);
			if (!StringUtils.isEmpty(column7))
				sb.append("," + column7);
			if (!StringUtils.isEmpty(column8))
				sb.append("," + column8);
			if (!StringUtils.isEmpty(column9))
				sb.append("," + column9);
			if (!StringUtils.isEmpty(column10))
				sb.append("," + column10);
			if (!StringUtils.isEmpty(column11))
				sb.append("," + column11);
			if (!StringUtils.isEmpty(column12))
				sb.append("," + column12);
			if (!StringUtils.isEmpty(column13))
				sb.append("," + column13);
			if (!StringUtils.isEmpty(column14))
				sb.append("," + column14);
			if (!StringUtils.isEmpty(column15))
				sb.append("," + column15);
			if (!StringUtils.isEmpty(column16))
				sb.append("," + column16);
			if (!StringUtils.isEmpty(column17))
				sb.append("," + column17);
			if (!StringUtils.isEmpty(column18))
				sb.append("," + column18);
			if (!StringUtils.isEmpty(column19))
				sb.append("," + column19);
			if (!StringUtils.isEmpty(column20))
				sb.append("," + column20);
			if (!StringUtils.isEmpty(column21))
				sb.append(column21);
			if (!StringUtils.isEmpty(column22))
				sb.append("," + column22);
			if (!StringUtils.isEmpty(column23))
				sb.append("," + column23);
			if (!StringUtils.isEmpty(column24))
				sb.append("," + column24);
			if (!StringUtils.isEmpty(column25))
				sb.append("," + column25);
			
			return sb.toString();
		}

	}

}
