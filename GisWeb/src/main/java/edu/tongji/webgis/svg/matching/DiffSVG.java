/**
 * 
 */
package edu.tongji.webgis.svg.matching;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import edu.tongji.webgis.svg.ivtd.IVTDGen;

/**
 * @author Tony Yin
 *	Algorithm SVG_DiffX in the prototype version of the project 
 */
public class DiffSVG implements MatchingAlgorithm {
	/**
	 * The result script to be generated
	 */
	public String script = "";
	
	/* (non-Javadoc)
	 * @see edu.tongji.webgis.svg.matching.MatchingAlgorithm#Matching(java.lang.String, java.lang.String, java.lang.String)
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
		
		this.script+="<?xml version='1.0' encoding='UTF-8'?>\n<root xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n";
		SVG_Script(ivg,ivg2, SVG_Match(ivg, ivg2));
		this.script+="\n</root>\n";
		//System.out.print(this.script);
		return this.script;

	}
	
	/**
	 * 
	 * @param fn1 File location of SVG1
	 * @param fn2 File location of SVG2
	 * @param xml File location of the output file
	 */
	public void GenXML(String fn1,String fn2,String xml){
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
			this.script+="<?xml version='1.0' encoding='UTF-8'?>\n<root xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n";
			SVG_Script(ivg,ivg2, SVG_Match(ivg, ivg2));
			this.script+="\n</root>\n";
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
	public Map<Integer,Integer> SVG_Match (IVTDGen SVG1,IVTDGen SVG2){
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
//		Map<String,Integer> E1 = new HashMap<String, Integer>();
		Vector<String> E1_s = new Vector<String>();
		Vector<Integer> E1_i = new Vector<Integer>();
		Map<String,Integer> E2 = new HashMap<String, Integer>();
		for(int j=0;j<SVG1.getLCCount();j++){
			String str = "";
			int VTD[][]=SVG1.getVTD();
			int LC[][]=SVG1.getLC();
			str +=new String(SVG1.getB(),VTD[LC[j][0]][3],VTD[LC[j][0]][2]);
      		for (int i = LC[j][0] + 1;; i++) {
      			if (i == SVG1.getVTDLength() || VTD[i][0] == 0) {
      				break;
      			} else {
      				str+=new String(SVG1.getB(),VTD[i][3],VTD[i][2]);
      			}
      		}//for i
      		E1_s.add(str);
      		E1_i.add(LC[j][0]);
		}//for j
		for(int j=0;j<SVG2.getLCCount();j++){
			String str = "";
			int VTD[][]=SVG2.getVTD();
			int LC[][]=SVG2.getLC();
			str +=new String(SVG2.getB(),VTD[LC[j][0]][3],VTD[LC[j][0]][2]);
      		for (int i = LC[j][0] + 1;; i++) {
      			if (i == SVG2.getVTDLength() || VTD[i][0] == 0) {
      				break;
      			} else {
      				str+=new String(SVG2.getB(),VTD[i][3],VTD[i][2]);
      			}
      		}//for i
      		E2.put(str, LC[j][0]);
		}//for j
		for(int i=0;i<SVG1.getLCCount();i++){
			int VTD[][]=SVG1.getVTD();
			int LC[][]=SVG1.getLC();
			if(map.containsKey(LC[i][0])){
				continue;
			}
			if(E2.containsKey(E1_s.get(i))){
				map.put(E1_i.get(i),E2.get(E1_s.get(i)));
			}
		}
		return map;
	}
	public void  SVG_Script(IVTDGen SVG1,IVTDGen SVG2,Map<Integer,Integer> map){
		for(int j=0;j<SVG2.getLCCount();j++){
			int VTD[][]=SVG2.getVTD();
			int LC[][]=SVG2.getLC();
			int x=0;
			if(!map.containsValue(LC[j][0])){//insert
				String str="<";
				str +=new String(SVG2.getB(),VTD[LC[j][0]][3],VTD[LC[j][0]][2]);
	      		for (int i = LC[j][0] + 1;; i++) {
	      			if (i == SVG2.getVTDLength() || VTD[i][0] == 0) {
	      				str +="/>";
	      				break;
	      			} else if(VTD[i][0] == 2){
	      				str+=" "+new String(SVG2.getB(),VTD[i][3],VTD[i][2]);
	      			}else if(VTD[i][0] == 5){
	      				str+=">"+new String(SVG2.getB(),VTD[i][3],VTD[i][2]);
	      				str +="</"+new String(SVG2.getB(),VTD[LC[j][0]][3],VTD[LC[j][0]][2])+">";
	      				break;
	      			}
	      		}//for i
	      		
	      		script +="<insert XPath='"+SVG2.getPosition(j)+"'>\n"+ str+"\n</insert>\n";
			}else{//move
				Iterator iter = map.entrySet().iterator(); 
				Map.Entry entry;
				while (iter.hasNext()) { 
				    entry = (Map.Entry) iter.next(); 
				    if(j == (Integer)entry.getValue()){
				    	x = (Integer)entry.getKey();
				    	break;
				    }
				}//while
				
				if(x<SVG1.getLC().length){
				if(!(SVG1.getLC()[x][2]>=0 && SVG2.getLC()[j][2]>=0)){
					continue;
				}
				/*如下是否必要？*/
//				else if(map.get(SVG1.getLC()[x][2]) != SVG2.getLC()[j][2]){
//					script +="<move toXPath=\'"+SVG2.getPosition(j)+"\'></move>\n";
////					System.out.println("<move toXPath=\'"+SVG2.getPosition(j)+"\'></move>");
//				}
				else if(!SVG1.getPosition(x).equals(SVG2.getPosition(j))){
					/*Start..输出节点*/
					String str="<";
					str +=new String(SVG2.getB(),VTD[LC[j][0]][3],VTD[LC[j][0]][2]);
		      		for (int i = LC[j][0] + 1;; i++) {
		      			if (i == SVG2.getVTDLength() || VTD[i][0] == 0) {
		      				str +="/>";
		      				break;
		      			} else if(VTD[i][0] == 2){
		      				str+=" "+new String(SVG2.getB(),VTD[i][3],VTD[i][2]);
		      			}else if(VTD[i][0] == 5){
		      				str+=">"+new String(SVG2.getB(),VTD[i][3],VTD[i][2]);
		      				str +="</"+new String(SVG2.getB(),VTD[LC[j][0]][3],VTD[LC[j][0]][2])+">";
		      				break;
		      			}
		      		}//for i
		      		/*End..输出节点*/
//					script +="<move XPath=\'"+SVG2.getPosition(j)+"\'>\n"+str+"\n</move>\n";
					script +="<move FromXPath=\'"+SVG1.getPosition(x)+"\'"
					            +" ToXPath=\'"+SVG2.getPosition(j)+"\'>\n"+str+"\n</move>\n";
				}
				}else{
					System.out.println(x+" "+j+SVG1.getLC().length);
				}
			}
		}
		for(int j=0;j<SVG1.getLCCount();j++){
			int VTD[][]=SVG1.getVTD();
			int LC[][]=SVG1.getLC();
			if(!map.containsKey(LC[j][0])){//delete
				String str="";
				str +=new String(SVG1.getB(),VTD[LC[j][0]][3],VTD[LC[j][0]][2]);
	      		for (int i = LC[j][0] + 1;; i++) {
	      			if (i == SVG1.getVTDLength() || VTD[i][0] == 0) {
	      				break;
	      			} else {
	      				str+=" "+new String(SVG1.getB(),VTD[i][3],VTD[i][2]);
	      			}
	      		}//for i
//	      		script +="<delete XPath='"+SVG1.getPosition(j)+"'>\n"+ str+"\n</delete>\n";//将要删除的节点也输出
	      		script +="<delete XPath='"+SVG1.getPosition(j)+"'/>\n";
			}
		}
	}
}
