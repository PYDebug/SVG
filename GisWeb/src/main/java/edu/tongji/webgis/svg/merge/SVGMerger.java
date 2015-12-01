/**
 * 
 */
package edu.tongji.webgis.svg.merge;

import java.util.HashMap;


/**
 * @author Administrator
 *
 */
public class SVGMerger {
	/**
	 * Merge the SVG map by the context of differcneXML file
	 * @param originSVG		the location of orignal SVG map
	 * @param diffXML		the location of diffXML map
	 * @return				the location of temp merged SVG map
	 * @throws Exception 
	 */
	public void mergeSVG(String originSVG, String diffXML, String toSVG) throws Exception
	{
		DeltaXMLInfo info=new DeltaXMLInfo(diffXML, false);
		HashMap hm=new HashMap();
		hm=info.getHashMap();
		XMLMerger merger = new XMLMerger(originSVG, false, toSVG);
		merger.modifySourceFile(hm);
	}
}
