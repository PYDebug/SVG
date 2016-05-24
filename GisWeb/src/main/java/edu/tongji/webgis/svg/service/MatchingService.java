/**
 * 
 */
package edu.tongji.webgis.svg.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import edu.tongji.webgis.svg.matching.MatchingAlgorithm;
import edu.tongji.webgis.svg.merge.SVGMerger;

/**
 * @author Tony Yin
 * This class is the implementation of Matching Services for SVG or X3D(Future Work) in the project
 */
public class MatchingService {
	/**
	 * Matching Service for two timestamp version SVG maps
	 * @param mapName		The name of the map
	 * @param firstTS		The timestamp of the older map
	 * @param secondTS		The timestamp of the newer map
	 * @param algorithm     The matching algorithm to be used
	 * @return
	 */
	public int MatchSVG(String firstFileLocation,String secondFileLocation, String outputLocation, MatchingAlgorithm algorithm){
		try {
			//read first SVG map
			File f1 = new File(firstFileLocation);
			FileInputStream fis1 = new FileInputStream(f1);
			byte[] b1 = new byte[(int) f1.length()];
			fis1.read(b1);
			fis1.close();
			
			String fileContent1 = new String(b1);
			
			//read second SVG map
			File f2 = new File(secondFileLocation);
			FileInputStream fis2 = new FileInputStream(f2);
			byte[] b2 = new byte[(int) f2.length()];
			fis2.read(b2);
			fis2.close();
			
			//get the update script
			String fileContent2 = new String(b2);
			
			String script = algorithm.Matching(fileContent1, fileContent2);
			//System.out.println(script);
			//save file
			File scriptFile = new File(outputLocation);
			scriptFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(scriptFile);
			fos.write(script.getBytes());
			fos.close();
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
	public int MergeSVG(String sourceFileLocation, String diffScriptLocation, String outputLocation){
		
		try {
			SVGMerger merger = new SVGMerger();
			merger.mergeSVG(sourceFileLocation, diffScriptLocation,outputLocation);
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
