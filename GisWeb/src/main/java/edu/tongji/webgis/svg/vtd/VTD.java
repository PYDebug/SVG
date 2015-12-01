/**
 * 
 */
package edu.tongji.webgis.svg.vtd;

/**
 * @author Administrator
 *
 */
import com.ximpleware.*;

import java.io.*;

public class VTD {

//	private byte _byte[];
	private String _sourceFile;
//	private String _deltaFile;
//	private String _targetFile;
	private boolean _namespaces;
	private VTDGen _vtdGen;
	private VTDNav _vtdNav;
	
	public VTD() {}
	
	public VTD(String sourceFile,boolean ns)
	{
		this._sourceFile=sourceFile;
		this._namespaces=ns;
		
		try {
			this._vtdGen=this.getVTDGen();
			this._vtdNav=this._vtdGen.getNav();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] readFileToByte(String sourceFile)throws IOException
	{
		File file=new File(sourceFile);
		if(file.exists()){
			FileInputStream fis= new FileInputStream(file);
			byte[] b=new byte[(int)file.length()];
			fis.read(b);//FileReader)
			fis.close();
			return b;
		}else{
			System.out.println("file dosen't exist."+sourceFile);
			System.exit(-1);
			return null;
		}
	}

	public VTDGen getVTDGen()throws Exception
	{
		byte b[]=this.readFileToByte(this._sourceFile);
		if(b==null)
		{
			System.out.println("read file fail and couldn't create the VTDGen");
			return null;
		}
		else
		{
			VTDGen vtdGen=new VTDGen();
			vtdGen.setDoc(b);
			vtdGen.parse(this._namespaces);//The main parsing function, internally generates VTD records, etc
			return vtdGen;
		}
	}
	
	public VTDGen getVTDGen(byte[] b,boolean namespace)throws Exception
	{
		if(b==null)
		{
			System.out.println("read file fail and couldn't create the VTDGen");
			return null;
		}
		else
		{
			VTDGen vtdGen=new VTDGen();
			vtdGen.setDoc(b);
			vtdGen.parse(namespace);//The main parsing function, internally generates VTD records, etc
			return vtdGen;
		}
	}
	
	public VTDNav getVTDNavForSpecificFile(String sourceFile,boolean namespace)throws Exception
	{
		byte b[]=this.readFileToByte(sourceFile);
		if(b==null)
		{
			System.out.println("read file fail and couldn't create the VTDGen");
			return null;
		}
		else
		{
			VTDGen vg=new VTDGen();
			vg.setDoc(b);
			vg.parse(namespace);
			VTDNav vn=vg.getNav();//pointer
			return vn;
		}
	}
	
	public void getAllInfo()throws Exception
	{
		int size=(int)this._vtdNav.getTokenCount();
		for (int i = 0; i < size; i++) {
			System.out.println("index:"+i
					+"   value:"+_vtdNav.toString(i)
					+"   type:"+_vtdNav.getTokenType(i)
					+"   depth:"+_vtdNav.getTokenDepth(i)
					+"        "+_vtdNav.getTokenOffset(i)
					//+":"+_vtdNav.getPrefixString(i)
					+":"+_vtdNav.getTokenLength(i));
		}
	}
	
	public void getAllInfo(VTDNav vn)throws Exception
	{
		int size=(int)vn.getTokenCount();
		for (int i = 0; i < size; i++) {
			System.out.println("index:"+i
					+"   value:"+vn.toString(i)
					+"   type:"+vn.getTokenType(i)
					+"   depth:"+vn.getTokenDepth(i)
					+"        "+vn.getTokenOffset(i)
					//+":"+_vtdNav.getPrefixString(i)
					+":"+vn.getTokenLength(i));
		}
	}
	
	public AutoPilot getAutoPilot(VTDNav vtdNav)throws Exception
	{
		AutoPilot ap= new AutoPilot();
		ap.bind(vtdNav);
		return ap;
	}
	
	public XMLModifier modifyXMLAttr(int index,String name,String value) throws Exception
	{
		VTDNav vtdNav=this._vtdNav;
		XMLModifier modifier=new XMLModifier();
		modifier.bind(vtdNav);
		if(vtdNav.toElement(VTDNav.ROOT))
		{
			if(vtdNav.matchElement(vtdNav.toString(vtdNav.getRootIndex())))
			{
				vtdNav.toElement(index);
				modifier.updateToken(vtdNav.getAttrVal(name), value);
			}
		}
		else
		{
			System.out.println("the pointer doesn't point to Root element.");
		}
		
		return modifier;
	}
	
	public XMLModifier modifyXMLAttr(int index,String name,String value,VTDNav vn) throws Exception
	{
		VTDNav vtdNav=vn;
		XMLModifier modifier=new XMLModifier();
		modifier.bind(vtdNav);
		if(vtdNav.toElement(VTDNav.ROOT))
		{
			if(vtdNav.matchElement(vtdNav.toString(vtdNav.getRootIndex())))
			{
				vtdNav.toElement(index);
				modifier.updateToken(vtdNav.getAttrVal(name), value);
			}
		}
		else
		{
			System.out.println("the pointer doesn't point to Root element.");
		}
		
		return modifier;
	}
	
	public int getElementCount(String elementName)throws Exception
	{
		int count=0;
		boolean flag=false;
		VTDNav vtdNav=this._vtdNav;
		AutoPilot ap=this.getAutoPilot(vtdNav);
		ap.selectXPath("//"+elementName);
		while(ap.evalXPath()!=-1)
		{
			if(vtdNav.matchElement(elementName))
			{
				flag=true;
				count++;
			}
		}
		ap.resetXPath();
		if(false==flag)
			System.out.println("donse't find this element1");
		return count;
	}
	
	public int getElementCount(String elementName,VTDNav vn)throws Exception
	{
		int count=0;
		boolean flag=false;
		VTDNav vtdNav=vn;
		AutoPilot ap=this.getAutoPilot(vtdNav);
		ap.selectXPath("//"+elementName);
		while(ap.evalXPath()!=-1)
		{
			if(vtdNav.matchElement(elementName))
			{
				flag=true;
				count++;
			}
		}
		ap.resetXPath();
		if(false==flag)
			System.out.println("donse't find this element2");
		return count;
	}
	
	public int[] getElementIndex(String elementName)throws Exception
	{
		int index=0;
		int count=0;
		int []num=new int[this.getElementCount(elementName)];
		boolean flag=false;
		VTDNav vtdNav=this._vtdNav;
		AutoPilot ap=this.getAutoPilot(vtdNav);
		ap.selectXPath("//"+elementName);
		while((index=ap.evalXPath())!=-1)
		{
			if(vtdNav.matchElement(elementName))
			{
				num[count++]=index;
				//System.out.println(index+" : "+vtdNav.toString(index));
				flag=true;
			}
		}
		if(false==flag)
			System.out.println("donse't find this element3");
		return num;
	}
	
	public int[] getElementIndex(String elementName,VTDNav vn)throws Exception
	{
		int index=0;
		int count=0;
		int []num=new int[this.getElementCount(elementName,vn)];
		boolean flag=false;
		VTDNav vtdNav=vn;
		AutoPilot ap=this.getAutoPilot(vtdNav);
		ap.selectXPath("//"+elementName);
		while((index=ap.evalXPath())!=-1)
		{
			if(vtdNav.matchElement(elementName))
			{
				num[count++]=index;
				//System.out.println(index+" : "+vtdNav.toString(index));
				flag=true;
			}
		}
		if(false==flag)
			System.out.println("donse't find this element4");
		return num;
	}
	
	public int getAttrCount(String elementName,VTDNav vn)throws Exception
	{
		int count=0;
		boolean flag=false;
		VTDNav vtdNav=vn;
		AutoPilot ap=this.getAutoPilot(vtdNav);
		ap.selectXPath("//@"+elementName);
		int k=0;
		while((k=ap.evalXPath())!=-1)
		{
			if(vn.toString(k).equals(elementName))
			{
				flag=true;
				count++;
			}
		}
		if(false==flag)
			System.out.println("donse't find this element4");
		return count;
	}
	
	public int[] getAttrIndex(String elementName,VTDNav vn)throws Exception
	{
		int index=0;
		int count=0;
		int []num=new int[this.getAttrCount(elementName,vn)];
		boolean flag=false;
		VTDNav vtdNav=vn;
		AutoPilot ap=this.getAutoPilot(vtdNav);
		ap.selectXPath("//@"+elementName);
		int k=0;
		while((k=ap.evalXPath())!=-1)
		{
			if(vn.toString(k).equals(elementName))
			{
				num[count++]=k;
				flag=true;
			}
		}
		if(false==flag)
			System.out.println("donse't find this element4");
		return num;
	}
	
	public void writeToFile(XMLModifier modifier,String targetFile) throws Exception
	{
		File file=new File(targetFile);
		FileOutputStream fos=new FileOutputStream(file);
		modifier.output(fos);
		modifier.reset();
		fos.flush();
		fos.close();
		//System.out.println("modify the File to the new file:"+targetFile+"  successfully.");
	}
	
	public static void main(String[] args) 
	{
		try {
			VTD vtd=new VTD("test.svg",true);
//			VTDNav vn1=vtd.getVTDNavForSpecificFile("xml2.xml",false);
//			VTDNav vn2=vtd.getVTDNavForSpecificFile("xml22.xml", false);
//			vtd.modifyXMLAttr(0,"xml22","xml22");
			vtd.getAllInfo();
//			int num[]=vtd.getElementIndex("fruits");
//			int num[]=vtd.getElementIndex("infor");
//			int num[]=vtd.getElementIndex("name");
//			int num[]=vtd.getElementIndex("color");
//			int num[]=vtd.getElementIndex("price");
//			for (int i = 0; i < num.length; i++) {
//				System.out.println(num[i]);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
