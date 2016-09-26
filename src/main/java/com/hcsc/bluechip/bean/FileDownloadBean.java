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
public class FileDownloadBean {

	
	  @ManagedProperty(value="#{methodBean}")
	  @Getter @Setter 
	  MethodBean methodBean;
	  
	  @ManagedProperty(value="#{codeGenerator}")
	  @Getter @Setter
	CodeGenerator codeGenerator;
	  
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
	 

	public static int csvFileCounter = 0;
	public static int junitFileCounter = 0;
	public static int archiveFileCounter = 0;
	

	public String dummy(){
		
		this.setRuleID(ruleID);
		this.setAuthor(author);
		this.setRuleFile(ruleFile);
		this.setDescription(description);
		this.setRulePackage(rulePackage);
		
		System.out.println("Dummy");
		return "dummy";
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
			StringBuilder sbh = new StringBuilder();
			for(int j=0;j< codeGenerator.columns.size();j++){
				
				if (j==0){
					sbh.append(codeGenerator.columns.get(j).getHeader());
				}
				else{ 
					sbh.append("," + codeGenerator.columns.get(j).getHeader());
				}
				
			}
			fileWriter.append(sbh.toString());
			fileWriter.append("\n");
			
			for (int i = 0; i < codeGenerator.getCars().size(); i++) {
				
				fileWriter.append(codeGenerator.getCars().get(i).getDetail());
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

			ArrayList<String> aList = new ArrayList(Arrays.asList(codeGenerator.getCars().get(0).getDetail().split(",")));

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
			for(MethodDetailsDto mdd: methodBean.getFilteredMethodNamesList()){
				writer.write(mdd.getDetails());
				writer.newLine();
				writer.newLine();

				
			}
			
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

	



}
