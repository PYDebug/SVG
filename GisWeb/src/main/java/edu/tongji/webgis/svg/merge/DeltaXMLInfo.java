/**
 * 
 */
package edu.tongji.webgis.svg.merge;
import java.util.ArrayList;
import java.util.HashMap;

import com.ximpleware.*;

import edu.tongji.webgis.svg.vtd.VTD;
/**
 * @author Administrator
 *
 */


public class DeltaXMLInfo {
	
	private String _deltaFile;
	private boolean _ns;
	
	public DeltaXMLInfo(String deltaFile,boolean ns) {
		this._deltaFile=deltaFile;
		this._ns=ns;
	}
	
	public HashMap getHashMap()throws Exception
	{
		HashMap<String,ArrayList> hm=new HashMap<String,ArrayList>();
		hm=this.getDeltaInfo();
		hm=this.modifyDeltaInfo(hm);
		return hm;
	}
	
	private HashMap getDeltaInfo() throws Exception
	{
		VTD vtd=new VTD();
		VTDNav vn2=vtd.getVTDNavForSpecificFile(this._deltaFile, this._ns);
		ArrayList<Integer> index_list=new ArrayList<Integer>();
		ArrayList<String> op_list=new ArrayList<String>();
		ArrayList<String> xpath_list=new ArrayList<String>();
		ArrayList<String> element_list=new ArrayList<String>();
		HashMap<String,ArrayList> hm=new HashMap<String,ArrayList>();
		if(vn2.toElement(VTDNav.ROOT))
		{
			for (int i = 0; i < vn2.getTokenCount(); i++) {
				/**
				if(vn2.toString(i).equals("insert")||vn2.toString(i).equals("move")||vn2.toString(i).equals("delete"))
				{
					index_list.add(i);
					op_list.add(vn2.toString(i));
					element_list.add(vn2.toString(i+3));
					xpath_list.add(vn2.toString(i+2));
				}*/
				/*唐学波修改:move操作必须有两个XPath否则无法正确找到要移动的节点
				 * ，一个是FromXPath即要移动节点原来的位置，另一个是ToXPath即要移动节点最终位置*/
				if(vn2.toString(i).equals("insert")||vn2.toString(i).equals("delete"))
				{
					index_list.add(i);
					op_list.add(vn2.toString(i));
					element_list.add(vn2.toString(i+3));
					xpath_list.add(vn2.toString(i+2));
				}else if(vn2.toString(i).equals("move")){
					index_list.add(i);
					op_list.add(vn2.toString(i));
					element_list.add(vn2.toString(i+5));
					xpath_list.add(vn2.toString(i+4));//ToXPath
					xpath_list.add(vn2.toString(i+2));//FromXPath
				}
			}
		}
		hm.put("index_list", index_list);
		hm.put("op_list", op_list);
		hm.put("element_list", element_list);
		hm.put("xpath_list", xpath_list);
		return hm;
	}
	
	private HashMap modifyDeltaInfo(HashMap hm) throws Exception
	{
		VTD vtd=new VTD();
		VTDNav vn2=vtd.getVTDNavForSpecificFile(this._deltaFile, this._ns);
		ArrayList<Integer> index_list=(ArrayList<Integer>)hm.get("index_list");
		ArrayList<String> temp_list=new ArrayList<String>();
		if(vn2.toElement(VTDNav.ROOT))
		{
			for (int i = 0; i < index_list.size(); i++) {
				if(i==0)
				{
					if(vn2.toElement(VTDNav.FC))
					{
						if(vn2.toElement(VTDNav.FC))
						{
							long l = vn2.getElementFragment();
							int len = (int) (l >> 32);
							int offset = (int) l;
							temp_list.add(new String(vtd.readFileToByte(this._deltaFile),offset, len));
//							System.out.println(new String(vtd.readFileToByte(this._deltaFile),offset, len));
						}
						else
						{
							temp_list.add("none");
							System.out.println("this element '"+vn2.toString(vn2.getCurrentIndex())+"' has no child");
						}
					}
					else
					{
						temp_list.add("none");
						System.out.println("this element '"+vn2.toString(vn2.getCurrentIndex())+"' has no child");
					}
				}
				if(i>0)
				{
					if(vn2.toElement(VTDNav.P))
					{
						if(vn2.toElement(VTDNav.NS))
						{
							if(vn2.toElement(VTDNav.FC))
							{
								long l = vn2.getElementFragment();
								int len = (int) (l >> 32);
								int offset = (int) l;
								temp_list.add(new String(vtd.readFileToByte(this._deltaFile),offset, len));
//								System.out.println(new String(vtd.readFileToByte(this._deltaFile),offset, len));
							}
							else
							{
								temp_list.add("none");
								System.out.println("this element '"+vn2.toString(vn2.getCurrentIndex())+"' has no child");
							}
						}
						else
						{
							temp_list.add("none");
							System.out.println("this element '"+vn2.toString(vn2.getCurrentIndex())+"' has no sibling");
						}
					}else{
						temp_list.add("none");
						System.out.println("this element '"+vn2.toString(vn2.getCurrentIndex())+"' has no sibling");
					}
				}
			}
		}
		hm.put("fragment_list", temp_list);
		return hm;
	}
}
