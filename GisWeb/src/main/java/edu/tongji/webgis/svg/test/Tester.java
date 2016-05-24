/**
 * 
 */
package edu.tongji.webgis.svg.test;

import edu.tongji.webgis.svg.matching.DiffSVG;
import edu.tongji.webgis.svg.service.MatchingService;

/**
 * @author Administrator
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatchingService service = new MatchingService();
		service.MatchSVG("/Users/lijiechu/Downloads/circle2.svg", 
				"/Users/lijiechu/Downloads/circle1.svg", 
				"/Users/lijiechu/Downloads/result.xml", new DiffSVG());
	}

}
