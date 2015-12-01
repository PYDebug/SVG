/**
 * 
 */
package edu.tongji.webgis.svg.merge;

import java.util.ArrayList;
import java.util.HashMap;

import com.ximpleware.AutoPilot;
import com.ximpleware.VTDNav;
import com.ximpleware.XMLModifier;

import edu.tongji.webgis.svg.vtd.VTD;


/**
 * @author Administrator
 *
 */
public class XMLMerger {
	private String _sourceFile=null;
	private boolean _source=false;
	private String _targetFile=null;

	
	private VTD _vtd=null;
	private byte[] _byte=null;
	private VTDNav _s_pointer=null;
	private AutoPilot _ap=null;
	private XMLModifier _modifier=null;
	
	
	private int _num=0;
	private int _num2=0;
	
	public XMLMerger(String sourceFile,boolean source) {
		this._sourceFile=sourceFile;
		this._source=source;
		try {
			if((this._sourceFile!=null)&&(!this._sourceFile.equals("")))
			{
				this.beginning(this._sourceFile, this._source);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public XMLMerger(String sourceFile,boolean source,String targetFile) {
		this._sourceFile=sourceFile;
		this._source=source;
		try {
			if((this._sourceFile!=null)&&(!this._sourceFile.equals("")))
			{
				this.beginning(this._sourceFile, this._source);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this._targetFile=targetFile;
	}
	
	public void beginning(String sourceFile,boolean ns)throws Exception
	{
		this._vtd=new VTD();
		this._byte=this._vtd.readFileToByte(sourceFile);
		this._s_pointer=this._vtd.getVTDNavForSpecificFile(sourceFile, ns);
		this._ap=this._vtd.getAutoPilot(_s_pointer);
		this._modifier=new XMLModifier();
		this._modifier.bind(_s_pointer);
	}
	
	public void end()throws Exception
	{
		this.beginning(this._targetFile, this._source);
		this._ap.selectXPath("//temp_temp_temp_dylan");
		int i=0;
		while((i=_ap.evalXPath())!=-1)
		{
			this._modifier.remove();
		}
		this._ap.resetXPath();
		this._vtd.writeToFile(_modifier, _targetFile);
		this.beginning(this._targetFile, this._source);
	}
	
	public void modifySourceFile(HashMap hm)
	{
		try {
			ArrayList<String> op_list=(ArrayList<String>)hm.get("op_list");
			ArrayList<Integer> index_list=(ArrayList<Integer>)hm.get("index_list");
			ArrayList<String> fragment_list=(ArrayList<String>)hm.get("fragment_list");
			ArrayList<String> xpath_list=(ArrayList<String>)hm.get("xpath_list");
			
			for (int i = 0; i < index_list.size(); i++) 
			{
				String op=op_list.get(i);
				String fragment=fragment_list.get(i);
				String xpath=xpath_list.get(i);
				
				xpath=xpath.replaceAll("\\[0\\]", "[1]");
				System.out.println(xpath);
				System.out.println(op_list.size()+" "+fragment_list.size()+" "+xpath_list.size()+" "+index_list.size());
				this._num=Integer.parseInt(xpath.substring(xpath.lastIndexOf("]")-1, xpath.lastIndexOf("]")));
				
				if("insert".equals(op))
				{
					this.insertElement(fragment,xpath);
					this._vtd.writeToFile(this._modifier, this._targetFile);
//					this.beginning(this._targetFile, this._source);
					this.end();
				}
				else if("move".equals(op))
				{
					this.moveElement(fragment,xpath);
					this._vtd.writeToFile(this._modifier, this._targetFile);
//					this.beginning(this._targetFile, this._source);
					this.end();
					int j=i+1;
					String temp=xpath_list.get(j);
					_num2=Integer.parseInt(xpath.substring(xpath.lastIndexOf("]")-1, xpath.lastIndexOf("]")));
					this.deleteElement(temp);
					
					this._vtd.writeToFile(this._modifier, this._targetFile);
//					this.beginning(this._targetFile, this._source);
					this.end();
				}
				else if("delete".equals(op))
				{
					this.deleteElement(xpath);
					this._vtd.writeToFile(this._modifier, this._targetFile);
//					this.beginning(this._targetFile, this._source);
					this.end();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertElement(String fragment,String xpath)throws Exception
	{
		this._ap.selectXPath(xpath);
		int i=0;
		boolean flag=false;
		while((i=_ap.evalXPath())!=-1)
		{
			flag=true;
//System.out.println("fragment:"+fragment);
			this._modifier.insertBeforeElement(fragment);
		}
		this._ap.resetXPath();
		if(false==flag)
		{
			if(_num==1)//fruits/*[1]/*[1]
			{
				xpath=xpath.substring(0, xpath.lastIndexOf("]")-4);
				this._ap.selectXPath(xpath);
				int j=0;
				int offset=0;
				int currentIndex=0;
				//<a id="w"/>  different form  <a id="w"></a>
				//type=0                       type=5
				while((j=_ap.evalXPath())!=-1)
				{
					offset=this._s_pointer.getTokenOffset(j);
					currentIndex=this._s_pointer.getCurrentIndex();
					while(true)
					{
						currentIndex=currentIndex+1;
						if((_s_pointer.getTokenType(currentIndex)!=2)&&(_s_pointer.getTokenType(currentIndex)!=4))
						{
							if(_s_pointer.getTokenType(currentIndex)==0)
							{
								String newtemp=this._s_pointer.toString(j);
								String newfragment=">"+fragment+"</"+newtemp+"><temp_temp_temp_dylan ";
								this._modifier.insertBytesAt(offset, newfragment.getBytes());
							}
							else if(_s_pointer.getTokenType(currentIndex)==5)
							{
								offset=this._s_pointer.getTokenOffset(currentIndex);
								this._modifier.insertBytesAt(offset, fragment.getBytes());
							}
							break;
						}
						offset=this._s_pointer.getTokenOffset(currentIndex);
						offset=offset+this._s_pointer.getTokenLength(currentIndex);
						offset=offset+1;
					}
				}
				this._ap.resetXPath();
			}
			else//fruits/*[1]/*[>1]
			{
				int temp2=this._num-1;
				xpath=xpath.substring(0, xpath.lastIndexOf("]")-4);
				xpath+="/*["+temp2+"]";
				
//System.out.println("fragment:"+fragment);
				
				this._ap.selectXPath(xpath);
				int k=0;
				boolean flag2=false;
				while((k=_ap.evalXPath())!=-1)
				{
					flag2=true;
					this._modifier.insertAfterElement(fragment);
				}
				this._ap.resetXPath();
			}
		}
	}
	
	public void moveElement(String fragment,String xpath)throws Exception
	{
		this.insertElement(fragment, xpath);
	}
	
	public void deleteElement(String xpath)throws Exception
	{
		this._ap.selectXPath(xpath);
		int i=0;
		boolean flag=false;
		while((i=_ap.evalXPath())!=-1)
		{
			flag=true;
			this._modifier.remove();
		}
		this._ap.resetXPath();
		if(false==flag)
		{
			System.out.println("no child3");
		}
	}
}
