package com.fafasoft.flow.util;

import java.io.BufferedInputStream;  
import java.io.ByteArrayOutputStream;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.util.zip.CRC32;  
import java.util.zip.CheckedOutputStream;  
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

  
public class ZipCompressor {  
	private static final int BUFFER = 8192;  
    private static ZipCompressor zipCompressor = null;

	static {
		zipCompressor = new ZipCompressor();
	}

	public static ZipCompressor getInstance() {
		return zipCompressor;
	}

	private ZipCompressor() {

	}
    private File zipFile;  
  
    public ZipCompressor(String pathName) {  
        zipFile = new File(pathName);  
    } 
    
	public  final byte[] compress(String[] args) {
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			for (int i = 0; i < args.length; i++) {
				ZipEntry zipEntry = new ZipEntry("\"" + i + "\"");
				zout.putNextEntry(zipEntry);
				zout.write(args[0].getBytes());
				zout.closeEntry();
			}

			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressed;
	}
	
    public void compress(String srcPathName) {  
        File file = new File(srcPathName);  
        if (!file.exists())  
            throw new RuntimeException(srcPathName + "不存在！");  
        try {  
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);  
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,  
                    new CRC32());  
            ZipOutputStream out = new ZipOutputStream(cos);  
            String basedir = "";  
            compressFile(file, out, basedir);  
            out.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
   
  
    /** 压缩一个文件 */  
    private void compressFile(File file, ZipOutputStream out, String basedir) {  
        if (!file.exists()) {  
            return;  
        }  
        try {  
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));  
            ZipEntry entry = new ZipEntry(file.getName());  
            out.putNextEntry(entry);  
            int count;  
            byte data[] = new byte[BUFFER];  
            while ((count = bis.read(data, 0, BUFFER)) != -1) {  
                out.write(data, 0, count);  
            }  
            bis.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
}  