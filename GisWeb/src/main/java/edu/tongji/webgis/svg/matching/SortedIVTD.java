package edu.tongji.webgis.svg.matching;

import java.util.Vector;

import edu.tongji.webgis.svg.ivtd.IVTDGen;
/*
 * 此类的作用在于将SVG的ivtd解析树重新排序，使遍历顺序变为dfs深度优先 [1]/[1]/[1] --> [1]/[2]
 */

public class SortedIVTD {

	private IVTDGen SVG;

	// total nums of Macro node
	private int size;
	
	public Vector<String> elementData = new Vector<String>();
	
	public Vector<String> XPath = new Vector<String>();
	
	public Vector<Integer> sorted = new Vector<Integer>();

	public SortedIVTD(IVTDGen SVG, Vector<String> Es) {
		this.SVG = SVG;
		size = SVG.getLCCount();
		this.elementData = Es;
	}
	
	public void initial(){
		for(int i = 0; i< size; i++){
			String tempXPath = SVG.getPosition(i);
			if(XPath.contains(tempXPath)){
				continue;
			}
			XPath.add(tempXPath);
			sorted.add(i);
			trackChildren(tempXPath);
		}
	}
	
	public void trackChildren(String oldXPath){
		for(int k=0; k<SVG.getLCCount(); k++){
			String potentialChildXPath = SVG.getPosition(k);
			if (potentialChildXPath.contains(oldXPath)
					&& potentialChildXPath.length() > oldXPath.length()&& numsOfChar(potentialChildXPath,"]") == 
							(numsOfChar(oldXPath,"]")+1)) {
				XPath.add(potentialChildXPath);
				sorted.add(k);
				trackChildren(potentialChildXPath);
			}
		}
	}
	
	public int numsOfChar(String str, String ch){
		int num = 0;
		for(int index=0 ; index<str.length();index++){
			if(str.substring(index,index+1).equals(ch)){
				num++;
			}
		}
		return num;
	}
	
}
