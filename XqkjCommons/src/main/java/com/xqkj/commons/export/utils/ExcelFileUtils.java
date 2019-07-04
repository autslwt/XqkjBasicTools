package com.xqkj.commons.export.utils;

import com.xqkj.commons.constant.ExcelFileContants;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.util.UUID;

public class ExcelFileUtils {
	/**
	 * 
	 * @author liwentao
	 * @param filePath
	 * @param fileName
	 * @param extName
	 * @return
	 * @date 2017年11月13日
	 */
	public static File createFile(String filePath, String fileName, String extName) {
		String tmpExtName = extName;
		String folder = filePath;
		String tmpFileName = fileName;

		if (StringUtils.isBlank(folder)) {
			folder = System.getProperty("java.io.tmpdir");
		}
		if (StringUtils.isBlank(tmpFileName)) {
			tmpFileName = UUID.randomUUID().toString().replace("-","");
		}

		String fileSep = File.separator;
		if (!folder.endsWith(fileSep)) {
			folder = folder + fileSep;
		}
		String fullFileName=folder+tmpFileName;
		if(StringUtils.isNotBlank(tmpExtName)){
			fullFileName+="."+tmpExtName;
		}
		File file = new File(fullFileName);
		return file;
	}
	
	public static String getSimpleFileName(File file){
		String realFileName = file.getName();
		int index = realFileName.lastIndexOf(".");
		if (index > 0) {
			return realFileName.substring(0, index);
		}
		return realFileName;
	}

	public static Workbook createWorkbook(String extName) {
		if (StringUtils.isBlank(extName)) {
			extName = ExcelFileContants.EXCEL_2007_EXTNAME;
		}
		if (extName.equals(ExcelFileContants.EXCEL_2007_EXTNAME)) {
			return new SXSSFWorkbook();
		}
		return new HSSFWorkbook();
	}
	
}
