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
	private String ruleID;

	@Getter
	@Setter
	private String author;

	@Getter
	@Setter
	private String ruleFile;

	@Getter
	@Setter
	private String description;

	@Getter
	@Setter
	private String rulePackage;

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

	public static void main(String arg[]) {
		CodeGenerator testObj = new CodeGenerator();
		testObj.ruleID = "MC123";
		testObj.author = "i346645";
		testObj.ruleFile = "Elig Amt Must be Entered when Interim Amt";
		testObj.description = "This method tests the rule MC186 and Populate Error Message";
		testObj.rulePackage = "ManualCalculation";

		ListModel obj = new ListModel();
		obj.column1 = "manualCalcType";
		obj.column2 = "majMedInEligAmt";
		obj.column3 = "majMedInterim";
		obj.column4 = "expectedErrorCode";

		testObj.cars = new ArrayList<ListModel>();
		testObj.cars.add(obj);

		testObj.fileGenerator();
	}

	public void fileGenerator() {
		csvFile();
		junitFile();
	}

	public void fileDowload() {
		String csvFileName = csvFile();
		String junitFileName = junitFile();
		String zipFile = "C:/Apps/archive" + (archiveFileCounter++) + ".zip";
		String[] srcFiles = { csvFileName, junitFileName };
		try {
			byte[] buffer = new byte[1024];
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (int i = 0; i < srcFiles.length; i++) {
				File srcFile = new File(srcFiles[i]);
				FileInputStream fis = new FileInputStream(srcFile);
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int length;
				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();
			response.setContentType("application/zip");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ zipFile + "\"");
			byte[] buf = new byte[1024];
			File file = new File(zipFile);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			OutputStream out = response.getOutputStream();
			response.setContentLength((int) length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int) length);
			}
			in.close();
			out.flush();
			out.close();
			context.responseComplete();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String csvFile() {
		String fileName = "";
		try {
			fileName = "C:/Apps/" + ruleID + (csvFileCounter++) + ".csv";
			FileWriter fileWriter = new FileWriter(fileName);

			for (int i = 0; i < cars.size(); i++) {
				fileWriter.append(cars.get(i).getDeatil());
				fileWriter.append("\n");
			}
			fileWriter.flush();
			fileWriter.close();

		} catch (Exception e) {
			log.error("CSV File Generator Failed !!", e);
		}
		return fileName;
	}

	public String junitFile() {
		String fileName = "";
		try {
			fileName = "C:/Apps/" + ruleID + (junitFileCounter++) + ".txt";

			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

			ArrayList<String> aList = new ArrayList(Arrays.asList(cars.get(0)
					.getDeatil().split(",")));

			System.out.println(aList.size());
			System.out.println(aList.toString());

			writer.write("/**");
			writer.newLine();
			writer.write("* RuleID: " + ruleID);
			writer.newLine();
			writer.write("*");
			writer.newLine();
			writer.write("* @author: " + author);
			writer.newLine();
			writer.write("*");
			writer.newLine();
			writer.write("* {@link " + ruleFile + ".brl}");
			writer.newLine();
			writer.write("*");
			writer.newLine();
			writer.write("* " + description);
			writer.newLine();
			writer.write("*/");
			writer.newLine();

			writer.write("@Test");
			writer.newLine();
			writer.write("@FileParameters(value =\"classpath:testData/"
					+ rulePackage + "/" + ruleID
					+ " .csv\", mapper = CsvWithHeaderMapper.class)");
			writer.newLine();
			writer.write("@TestDocumentation(requirement = \"" + ruleID
					+ "\", description = \"" + description
					+ "\", reference = \"" + aList.size()
					+ "\", expectations = \"" + description + "\")");
			writer.newLine();
			writer.write("public void " + ruleID + "( ");

			for (int i = 0; i < aList.size(); i++) {
				if (i < aList.size() - 1) {
					writer.write("String " + aList.get(i) + ", ");
				} else {
					writer.write("String " + aList.get(i));
				}
			}

			writer.write(") { ");
			writer.newLine();
			writer.newLine();
			writer.write("System.out.println(\" INPUT_VALUES FOR  " + ruleID
					+ " ===========================================\"); ");
			writer.newLine();
			writer.newLine();

			for (int i = 0; i < aList.size(); i++) {
				writer.write("System.out.println(\"" + aList.get(i) + " : \" +"
						+ aList.get(i) + ");");
				writer.newLine();
			}

			writer.newLine();
			writer.write("System.out.println(\" ===================================================================\"); ");
			writer.newLine();
			writer.newLine();

			writer.write("InsuranceClaim insuranceClaim = InsuranceClaimBuilderMock.builder().build();");
			writer.newLine();
			writer.newLine();

			writer.write("}");
			writer.newLine();

			writer.close();

		} catch (Exception e) {
			log.error("Junit File Generator Failed !!", e);
		}
		return fileName;
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

		public String getDeatil() {
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
				sb.append(column11);
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
