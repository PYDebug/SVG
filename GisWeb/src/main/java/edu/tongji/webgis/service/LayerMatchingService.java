/**
 * 
 */
package edu.tongji.webgis.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import edu.tongji.webgis.svg.matching.MatchingAlgorithm;
import edu.tongji.webgis.svg.merge.SVGMerger;

/**
 * @author Administrator
 *
 */
public class LayerMatchingService {
	public String mapFolder;
	
	public LayerMatchingService(String mapFolder){
		this.mapFolder = mapFolder;
	}
	/**
	 * Matching Service for two timestamp version SVG maps
	 * @param mapName		The name of the map
	 * @param firstTS		The timestamp of the older map
	 * @param secondTS		The timestamp of the newer map
	 * @param algorithm     The matching algorithm to be used
	 * @return
	 */
	public int MatchSVG(String layer, String firstTS, String secondTS, MatchingAlgorithm algorithm){
		String firstFileLocation = "/" + layer +"/1_1/" + firstTS + ".svg";
		String secondFileLocation = "/" + layer +"/1_1/" + secondTS + ".svg";
		String outputLocation = "/" + layer +"/1_1/" + secondTS + "_" + firstTS + ".xml";
		try {
			//read first SVG map
			File f1 = new File(mapFolder,firstFileLocation);
			FileInputStream fis1 = new FileInputStream(f1);
			//byte[] b1 = new byte[(int) f1.length()];
			//fis1.read(b1);
			
			InputStreamReader isr1 = new InputStreamReader(fis1, "UTF-8");  
            StringBuffer sbread1 = new StringBuffer();  
            while (isr1.ready()) {  
                sbread1.append((char) isr1.read());  
            }  
            isr1.close(); 
			
			String fileContent1 = sbread1.toString();
			
			fis1.close();
			//read second SVG map
			File f2 = new File(mapFolder,secondFileLocation);
			FileInputStream fis2 = new FileInputStream(f2);
			//byte[] b2 = new byte[(int) f2.length()];
			//fis2.read(b2);
				
			InputStreamReader isr2 = new InputStreamReader(fis2, "UTF-8");  
            StringBuffer sbread2 = new StringBuffer();  
            while (isr2.ready()) {  
                sbread2.append((char) isr2.read());  
            }  
            isr2.close(); 
            fis2.close();
            
			//get the update script
			String fileContent2 = sbread2.toString();
			
			String script = algorithm.Matching(fileContent1, fileContent2);
			//System.out.println(script);
			//save file
			File scriptFile = new File(mapFolder,outputLocation);
			scriptFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(scriptFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); 
			//System.out.println(script);
		    osw.write(script); 
		    osw.flush(); 
			
			
			//fos.write(script.getBytes());
			fos.close();
			System.out.println("added at:"+ mapFolder);
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			return 0;
		}
		
		
	}
	
	/**
	 * Merge the SVG map
	 * @param mapName	the name of the map
	 * @param fromTS	from the base version
	 * @param toTS		to the version
	 * @return			status code			1:success
	 * 										0:failed
	 */
	public int MergeSVG(String layer, String fromTS, String toTS){
		
		try {
			String sourceMapLocation = mapFolder+"/" + layer +"/1_1/"+ fromTS + ".svg";
			String diffScriptLocation = mapFolder+"/" + layer +"/1_1/" + toTS + "_" + fromTS + ".xml";
			
			String outputLocation = mapFolder+"/"+"/" + layer +"/1_1/"+ toTS + ".svg";
			SVGMerger merger = new SVGMerger();
			merger.mergeSVG(sourceMapLocation, diffScriptLocation,outputLocation);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	/**
	 * @return
	 */
}
