package com.xqkj.commons.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xqkj.commons.exceptions.BizException;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;


public class ZipFileUtils {

	private static final int BUFFER_SIZE = 1024;

	public static void zipFileList(List<File> fileList, File zipfile, Boolean delListFile) throws BizException {
		byte[] buf = new byte[BUFFER_SIZE];
		ZipOutputStream out = null;
		BufferedInputStream bin = null;
		try {
			// ZipOutputStream类：完成文件或文件夹的压缩
			out = new ZipOutputStream(new FileOutputStream(zipfile));
			for (File file : fileList) {
				bin = new BufferedInputStream(new FileInputStream(file));
				out.putNextEntry(new ZipEntry(file.getName()));
				int len;
				while ((len = bin.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				bin.close();
				bin = null;
			}
			//
			if (delListFile != null && delListFile) {
				for (File file : fileList) {
					if(file.exists()){
						file.delete();
					}
				}
			}
		} catch (IOException ex) {
			throw new BizException(ex);
		} finally {
			IOUtil.closeIOStream(out, bin);
		}
	}

	public static void main(String[] args) {
		try {
			List<File> fileList = new ArrayList<>();
			File f1 = new File("/Users/lwt-mac/Documents/e7fd7a0b-1df9-44be-94b3-114f57ca31cb-1.xlsx");
			File f2 = new File("/Users/lwt-mac/Documents/e7fd7a0b-1df9-44be-94b3-114f57ca31cb-2.xlsx");
			File f3 = new File("/Users/lwt-mac/Documents/e7fd7a0b-1df9-44be-94b3-114f57ca31cb-3.xlsx");
			fileList.add(f1);
			fileList.add(f2);
			fileList.add(f3);
			File zipFile = new File("/Users/lwt-mac/Documents/ziptest-8.zip");
			ZipFileUtils.zipFileList(fileList, zipFile,false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
