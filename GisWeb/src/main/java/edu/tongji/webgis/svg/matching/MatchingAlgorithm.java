package edu.tongji.webgis.svg.matching;

public interface MatchingAlgorithm {
	/**
	 * 
	 * @param svg1		the first version of SVG
	 * @param svg2		the second version of SVG
	 * @param output	the result script
	 * @return			whether the algorithm finishes
	 * 					0:failed
	 * 					1:success
	 */
	public String Matching(String svg1, String svg2);
}
