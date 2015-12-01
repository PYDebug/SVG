/**
 * 
 */
package edu.tongji.webgis.svg.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @author Administrator
 *
 */
public class CompressAlgorithm {
	public static String Gzip(String inputPath , String outputPath) {
	
		
			
		
	
		System.out.println("getting file:" + inputPath);
		File file = new File(inputPath);
		File outfile = new File(outputPath);
		try {
			FileInputStream is = new FileInputStream(file);
			FileOutputStream os = new FileOutputStream(outputPath);
			GZIPOutputStream gzout = new GZIPOutputStream(os); // 对输出的文件已GZIP流输出
			byte[] buf = new byte[1024];
			int num;
			while ((num = is.read(buf)) != -1) {
				gzout.write(buf, 0, num);
			}
			gzout.close();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
}
}