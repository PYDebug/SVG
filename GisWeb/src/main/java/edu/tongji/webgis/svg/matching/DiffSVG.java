/**
 * 
 */
package edu.tongji.webgis.svg.matching;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import edu.tongji.webgis.svg.ivtd.IVTDGen;

/**
 * @author Tony Yin Algorithm SVG_DiffX in the prototype version of the project
 */
public class DiffSVG implements MatchingAlgorithm {
	/**
	 * The result script to be generated
	 */
	public String script = "";
	
	public String diffScript = "";

	public SvgOneChangeLog changeLog;
	
	public boolean[] isTraversed;
	
	public SortedIVTD temp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.tongji.webgis.svg.matching.MatchingAlgorithm#Matching(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public String Matching(String svg1, String svg2) {
		// TODO Auto-generated method stub
		// Convert svg1 to IVTDGen
		byte[] b = svg1.getBytes();
		IVTDGen ivg = new IVTDGen();
		ivg.setDoc(b);
		ivg.parse();
		// Convert svg2 to IVTDGen
		byte[] b2 = svg2.getBytes();
		IVTDGen ivg2 = new IVTDGen();
		ivg2.setDoc(b2);
		ivg2.parse();

		this.script += "<?xml version='1.0' encoding='UTF-8'?>\n<root xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n";
        this.diffScript += "<?xml version='1.0' encoding='UTF-8'?>\n<root xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n";
		SVG_Script(ivg, ivg2, SVG_Match(ivg, ivg2));
		this.script += "\n</root>\n";
		this.diffScript +="\n</root>\n";
		// System.out.print(this.script);
		return this.diffScript;

	}

	/**
	 * 
	 * @param fn1
	 *            File location of SVG1
	 * @param fn2
	 *            File location of SVG2
	 * @param xml
	 *            File location of the output file
	 */
	public void GenXML(String fn1, String fn2, String xml) {
		try {
			FileInputStream fis;
			File f = new File(fn1);
			fis = new FileInputStream(f);
			byte[] b = new byte[(int) f.length()];
			fis.read(b);
			fis.close();
			IVTDGen ivg = new IVTDGen();
			ivg.setDoc(b);
			ivg.parse();

			FileInputStream fis2;
			File f2 = new File(fn2);
			fis2 = new FileInputStream(f2);
			byte[] b2 = new byte[(int) f2.length()];
			fis2.read(b2);
			fis2.close();
			IVTDGen ivg2 = new IVTDGen();
			ivg2.setDoc(b2);
			ivg2.parse();
			this.script += "<?xml version='1.0' encoding='UTF-8'?>\n<root xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n";
			SVG_Script(ivg, ivg2, SVG_Match(ivg, ivg2));
			this.script += "\n</root>\n";
			File xmlfile = new File(xml);
			xmlfile.createNewFile();
			FileOutputStream fos = new FileOutputStream(xmlfile);
			fos.write(this.script.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, Integer> SVG_Match(IVTDGen SVG1, IVTDGen SVG2) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		// Map<String,Integer> E1 = new HashMap<String, Integer>();
		Vector<String> E1_s = new Vector<String>();
		Vector<Integer> E1_i = new Vector<Integer>();
		Vector<String> E2_s = new Vector<String>();
		Vector<Integer> E2_i = new Vector<Integer>();
		// vendor edit: Map<String,Integer> E2 = new HashMap<String, Integer>();
		Map<Integer, String> E2 = new HashMap<Integer, String>();
		int hha = 0;
		for (int j = 0; j < SVG1.getLCCount(); j++) {
			String str = "";
			int VTD[][] = SVG1.getVTD();
			int LC[][] = SVG1.getLC();
			str += new String(SVG1.getB(), VTD[LC[j][0]][3], VTD[LC[j][0]][2]);
			for (int i = LC[j][0] + 1;; i++) {
				if (i == SVG1.getVTDLength() || VTD[i][0] == 0) {
					break;
				} else {
					str += new String(SVG1.getB(), VTD[i][3], VTD[i][2]);
				}
			} // for i
			E1_s.add(str);
			//System.out.println(str+" "+SVG1.getPosition(j));
			E1_i.add(LC[j][0]);
		} // for j
		int jakiveri = 0;
		for (int j = 0; j < SVG2.getLCCount(); j++) {
			String str = "";
			int VTD[][] = SVG2.getVTD();
			int LC[][] = SVG2.getLC();
			str += new String(SVG2.getB(), VTD[LC[j][0]][3], VTD[LC[j][0]][2]);
			for (int i = LC[j][0] + 1;; i++) {
				if (i == SVG2.getVTDLength() || VTD[i][0] == 0) {
					break;
				} else {
					str += new String(SVG2.getB(), VTD[i][3], VTD[i][2]);
				}
			} // for i
			//if (E2.containsKey(LC[j][0]))
			//	jakiveri++;
			// vendor edit:E2.put(str, LC[j][0]);
			// E2.put(LC[j][0],str);
			E2_s.add(str);
			E2_i.add(LC[j][0]);
		} // for j
		//System.out.println(jakiveri);
		for (int i = 0; i < SVG1.getLCCount(); i++) {
			//System.out.println(i);
			int VTD[][] = SVG1.getVTD();
			int LC[][] = SVG1.getLC();
			if (map.containsKey(LC[i][0])) {
				continue;
			} // 方法是拿走一个后删除一个 避免相同
			ArrayList<Integer> tempArray = new ArrayList<Integer>();
			for (int p = 0; p < SVG2.getLCCount(); p++) {
				if (map.containsValue(E2_i.get(p))) {
					continue;
				}
				if (E2_s.get(p).equals(E1_s.get(i))) {
					tempArray.add(p);
				}
			}
			Map<Integer, Integer> tempMap = new HashMap<Integer, Integer>();
			for (int y = 0; y < tempArray.size(); y++) {
				Map<Integer, Integer> mMap = new HashMap<Integer, Integer>();
				// 假设每个节点的孩子最多有30个 实际上满足
				mMap = matchFragment(i, tempArray.get(y), map, mMap, SVG1, SVG2, E1_s, E2_s);
				if(mMap.size()>tempMap.size())tempMap = mMap;
			}
			map.putAll(tempMap);
//			if (E2_s.contains(E1_s.get(i))) {
//				// map.put(E1_i.get(i),getFirstKeyByValue(E1_s.get(i), E2_s));
//				// E2_i.remove(getFirstKeyByValue(E1_s.get(i), E2_s));
//				// E2_s.remove(getFirstKeyByValue(E1_s.get(i), E2_s));
//			}
		}
		temp = new SortedIVTD(SVG2,E2_s);
		temp.initial();
		return map;
	}

	public void SVG_Script(IVTDGen SVG1, IVTDGen SVG2, Map<Integer, Integer> map) {
		this.changeLog = new SvgOneChangeLog(SVG1);
		int moveNum = 1;
		for (int j = 0; j < SVG1.getLCCount(); j++) {
			int VTD[][] = SVG1.getVTD();
			int LC[][] = SVG1.getLC();
			if (!map.containsKey(LC[j][0])) {// delete
				String str = "";
				str += new String(SVG1.getB(), VTD[LC[j][0]][3], VTD[LC[j][0]][2]);
				for (int i = LC[j][0] + 1;; i++) {
					if (i == SVG1.getVTDLength() || VTD[i][0] == 0) {
						break;
					} else {
						str += " " + new String(SVG1.getB(), VTD[i][3], VTD[i][2]);
					}
				} // for i
					// script +="<delete XPath='"+SVG1.getPosition(j)+"'>\n"+
					// str+"\n</delete>\n";//将要删除的节点也输出
				String tempXPath = changeLog.getChangedPosition(SVG1.getPosition(j));
				script += "<delete XPath='" + tempXPath + "'/>\n";
				diffScript += "<delete XPath='" + SVG1.getPosition(j) + "'/>\n";
				int tempChildNums = changeLog.childNums.get(SVG1.getPosition(j));
				if(tempChildNums > 0){
					String tempStr = getFirstChild(SVG1.getPosition(j));
					tempChildNums = changeLog.childNums.get(tempStr);
				} else{
					tempChildNums = 0;
					//tempChildNums = getNextBrotherChildNums(SVG1.getPosition(j));
				}
				changeLog.childNodesNum.add(tempChildNums);
//				if(tempXPath.equals("/svg")){
//					tempXPath = tempXPath + "[1]";
//				}
				changeLog.addChangeLog(2, tempXPath);
				// script +="<delete XPath='"+SVG1.getPosition(j)+"'/>\n";
			}
		}
		//isTraversed = new boolean[SVG2.getLCCount()];
		for (int replace = 0; replace < SVG2.getLCCount(); replace++) {
			int VTD[][] = SVG2.getVTD();
			int LC[][] = SVG2.getLC();
			int j = temp.sorted.get(replace);
			int x = 0;
			if (!map.containsValue(LC[j][0])) {// insert
				String str = "<";
				str += new String(SVG2.getB(), VTD[LC[j][0]][3], VTD[LC[j][0]][2]);
				for (int i = LC[j][0] + 1;; i++) {
					if (i == SVG2.getVTDLength() || VTD[i][0] == 0) {
						str += "/>";
						break;
					} else if (VTD[i][0] == 2) {
						str += " " + new String(SVG2.getB(), VTD[i][3], VTD[i][2]);
					} else if (VTD[i][0] == 5) {
						str += ">" + new String(SVG2.getB(), VTD[i][3], VTD[i][2]);
						str += "</" + new String(SVG2.getB(), VTD[LC[j][0]][3], VTD[LC[j][0]][2]) + ">";
						break;
					}
				} // for i
				String tempXPath = SVG2.getPosition(j);
				// tempXPath =
				// changeLog.getChangedPosition(SVG2.getPosition(j));
				script += "<insert XPath='" + tempXPath + "'>\n" + str + "\n</insert>\n";
				diffScript += "<insert XPath='" + tempXPath + "'>\n" + str + "\n</insert>\n";
				changeLog.addChangeLog(1, tempXPath);
				changeLog.childNodesNum.add(1);
				// script +="<insert XPath='"+SVG2.getPosition(j)+"'>\n"+
				// str+"\n</insert>\n";
			} else {// move
				Iterator iter = map.entrySet().iterator();
				Map.Entry entry;
				/*
				 * Vendor edit by jaki while (iter.hasNext()) { entry =
				 * (Map.Entry) iter.next(); if(j == (Integer)entry.getValue()){
				 * x = (Integer)entry.getKey(); break; } }//while
				 */
				while (iter.hasNext()) {
					entry = (Map.Entry) iter.next();
					if (LC[j][0] == (Integer) entry.getValue()) {
						for (x = 0; x < SVG1.getLCCount(); x++) {
							// if (LC[x][0] == (Integer) entry.getKey()) {
							if (SVG1.getLC()[x][0] == (Integer) entry.getKey()) {
								break;
							}
						}
					}
				}

				if (x < SVG1.getLC().length) {
					if (!(SVG1.getLC()[x][2] >= 0 && SVG2.getLC()[j][2] >= 0)) {
						continue;
					}
					/* 如下是否必要？ */
					// else if(map.get(SVG1.getLC()[x][2]) !=
					// SVG2.getLC()[j][2]){
					// script +="<move
					// toXPath=\'"+SVG2.getPosition(j)+"\'></move>\n";
					//// System.out.println("<move
					// toXPath=\'"+SVG2.getPosition(j)+"\'></move>");
					// }
					else if (!changeLog.getChangedPosition(SVG1.getPosition(x)).equals(SVG2.getPosition(j))) {
						// else
						// if(!SVG1.getPosition(x).equals(SVG2.getPosition(j))){
						/* Start..输出节点 */
						String str = "<";
						str += new String(SVG2.getB(), VTD[LC[j][0]][3], VTD[LC[j][0]][2]);
						for (int i = LC[j][0] + 1;; i++) {
							if (i == SVG2.getVTDLength() || VTD[i][0] == 0) {
								str += "/>";
								break;
							} else if (VTD[i][0] == 2) {
								str += " " + new String(SVG2.getB(), VTD[i][3], VTD[i][2]);
							} else if (VTD[i][0] == 5) {
								str += ">" + new String(SVG2.getB(), VTD[i][3], VTD[i][2]);
								str += "</" + new String(SVG2.getB(), VTD[LC[j][0]][3], VTD[LC[j][0]][2]) + ">";
								break;
							}
						} // for i
						/* End..输出节点 */
						String tempXPath1 = changeLog.getChangedPosition(SVG1.getPosition(x));
						int childCount = 0;
						// changeLog.addChangeLog(2, tempXPath1);
						// for (int k = x; k < SVG1.getLCCount(); k++) {
						// String potentialChildXPath = SVG1.getPosition(k);
						// if (potentialChildXPath.contains(tempXPath1) &&
						// potentialChildXPath.length() > tempXPath1.length()) {
						// childCount++;
						// //
						// changeLog.adaptSVGTreeChanges(childCount,potentialChildXPath);
						// changeLog.addChangeLog(2, potentialChildXPath);
						// changeLog.addChangeLog(1,
						// changeLog.getChangedPosition(potentialChildXPath));
						// }
						// }
						String tempXPath2 = SVG2.getPosition(j);
						// script +="<move
						// XPath=\'"+SVG2.getPosition(j)+"\'>\n"+str+"\n</move>\n";
						script += "<move FromXPath=\'" + tempXPath1 + "\'" + " ToXPath=\'" + tempXPath2 + "\'>\n" + str
								+ "\n</move>\n";
						diffScript += "<move FromXPath=\'" + SVG1.getPosition(x) + "\'" + " ToXPath=\'" + tempXPath2 + "\'>\n" + str
								+ "\n</move>\n";
						changeLog.addChangeLog(2, tempXPath1);
						int tempChildNums = changeLog.childNums.get(SVG1.getPosition(x));
//						if (tempChildNums > 0) {
//							String tempStr = getFirstChild(SVG1.getPosition(x));
//							tempChildNums = changeLog.childNums.get(tempStr);
//						} else {
//							tempChildNums = getNextBrotherChildNums(SVG1.getPosition(x));
//						}
						changeLog.childNodesNum.add(tempChildNums);
						changeLog.addChangeLog(1, tempXPath2);
						changeLog.childNodesNum.add(1);
						//System.out.println(moveNum++);
						if(moveNum == 374){
							int a = 23;
						}
						// script +="<move
						// FromXPath=\'"+SVG1.getPosition(x)+"\'"
						// +"
						// ToXPath=\'"+SVG2.getPosition(j)+"\'>\n"+str+"\n</move>\n";
					}
				} else {
					System.out.println(x + " " + j + SVG1.getLC().length);
				}
			}
		}
		// delete position
	}

	public static int getFirstKeyByValue(String value, Vector<String> array) {
		int intKey = 0;
		for (int i = 0; i < array.size(); i++) {
			String tvalue = array.get(i);
			if (tvalue.equals(value)) {
				intKey = i;
				break;
			}
		}
		return intKey;
	}

	public Map<Integer,Integer> matchFragment(int posX, Integer posY, Map<Integer,Integer> map, Map<Integer,Integer> mMap,
			IVTDGen SVG1,IVTDGen SVG2, Vector<String> E1_s, Vector<String> E2_s){
		int LCposX = SVG1.getLC()[posX][0];
		int LCposY = SVG2.getLC()[posY][0];
		if(!map.containsKey(LCposX)&&!map.containsValue(LCposY)&&E1_s.get(posX).equals(E2_s.get(posY))){
			mMap.put(LCposX, LCposY);
    		int childCountSVG1 = 0;
    		String oldXPath = SVG1.getPosition(posX);
    		Vector<Integer> childPosOfSVG1 = new Vector<Integer>();
    		for(int k=0; k<SVG1.getLCCount(); k++){
    			String potentialChildXPath = SVG1.getPosition(k);
				if (potentialChildXPath.contains(oldXPath)
						&& potentialChildXPath.length() > oldXPath.length()&& numsOfChar(potentialChildXPath,"]") == 
								(numsOfChar(oldXPath,"]")+1)) {
					childPosOfSVG1.add(k);
					childCountSVG1++;
				}
    		}
    		
    		int childCountSVG2 = 0;
    		oldXPath = SVG2.getPosition(posY);
    		Vector<Integer> childPosOfSVG2 = new Vector<Integer>();
    		for(int k=0; k<SVG2.getLCCount(); k++){
    			String potentialChildXPath = SVG2.getPosition(k);
				if (potentialChildXPath.contains(oldXPath)
						&& potentialChildXPath.length() > oldXPath.length()&& numsOfChar(potentialChildXPath,"]") == 
								(numsOfChar(oldXPath,"]")+1)) {
					childPosOfSVG2.add(k);
					childCountSVG2++;
				}
    		}
    		
    		int childCount = childCountSVG1 <= childCountSVG2 ? 1 : 2;
    		if(childCount == 1){
    			for(int i = 0;i<childCountSVG1;i++){
    				matchFragment(childPosOfSVG1.get(i),childPosOfSVG2.get(i),map,mMap,SVG1,SVG2,E1_s,E2_s);
    			}
    		}else{
    			for(int i = 0;i<childCountSVG2;i++){
    			  matchFragment(childPosOfSVG2.get(i),childPosOfSVG1.get(i),map,mMap,SVG1,SVG2,E1_s,E2_s);
    			}
    		}
		}
		return mMap;
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
	
	public int getNextBrotherChildNums(String str){
		int index = changeLog.getFinalLayerIndex(str);
		String nextBrotherIndex = str.substring(0,str.lastIndexOf('*')+1) + '[' + (index+1) + ']';
		if(changeLog.childNums.containsKey(nextBrotherIndex)){
			return changeLog.childNums.get(nextBrotherIndex);
		}else{
			return 0;
		}
	}
	
	public String getFirstChild(String str){
		str = str + "/*[1]"; 
		return str;
	}
}
