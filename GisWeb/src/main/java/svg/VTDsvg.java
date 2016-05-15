package svg;
import com.ximpleware.*;

import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class VTDsvg {
	
	public VTDsvg(String filein,String mode_op){
		filePath = filein;
		//modPar= new ModeParser(mode_op);
		modes=mode_op;
	//	modesArray=new ArrayList<String>();
		attrsArray=new ArrayList<String>();
		//����ͬmodeId ��Ԫ�ط���Array��ͬһ��Ԫ����
	//	modesIntoArray();
		if(modes!=null)
		{
			getAllattrs();
		}
	}
	
	
	//the measure for normal query,the input 'in' contains String sequence,sparated by " "
	//��һ������������Ĳ�ѯ�ʣ��ڶ��������е�ͼ���ļ������ڲ�ѯԪ�أ������������ɵ����ļ���·������
	public String NormalQuery(String in,ArrayList<String> layerFileLocation,ArrayList<String> newFilePaths) throws ModifyException, 
								XPathParseException, XPathEvalException, NavException, TranscodeException, FileNotFoundException, IOException{
		String[] InToArr=in.split(" ");
		String out=new String();
		int outFileCount=0;
		for(int j=0;j<InToArr.length;j++){
			boolean queryFlag=false;//�жϵ�ǰ����Ƿ�ƥ��
			for(String layerfile:layerFileLocation){
				boolean fileFlag=false;//�жϵ�ǰͼ���ļ��Ƿ��������ѯԪ��
				VTDGen vg = new VTDGen();
				XMLModifier xm = new XMLModifier();
				if (vg.parseFile(layerfile, false)){
					vn = vg.getNav();
					ap = new AutoPilot(vn);
					xm.bind(vn);
					BookMark bm = new BookMark(vn);
					BookMark bm2 = new BookMark(vn);
					String path = "//g[@class='entity']";
					ap.resetXPath();
					ap.selectXPath(path);
					//int ll=0;
					int i = -1;
					while ((i = ap.evalXPath()) != -1){
						//System.out.println(++ll);
						
						bm.recordCursorPosition();
						String Elename = vn.toString(i);
						
						//���ҵ�text��ǩ
						if(vn.toElement(VTDNav.FC,"text")){
							int text=vn.getText();
							String content=vn.toString(text);
							
							if(content.contains(InToArr[j])){
								fileFlag=true;
								queryFlag=true;
							}
							else{
								bm.setCursorPosition();
								
								
								//�ҵ�info
								if(vn.toElement(VTDNav.FC,"info")){
									bm2.recordCursorPosition();
									//�ҵ�address
									if(vn.toElement(VTDNav.FC,"address")){
										int text3=vn.getText();
										String content3=vn.toString(text3);
										
										if(content3.contains(InToArr[j])){
											fileFlag=true;
											queryFlag=true;
										}
										else{
											//������һ�� �����ҵ�phone
											bm2.setCursorPosition();
											if(vn.toElement(VTDNav.FC,"phone")){
												int text2=vn.getText();
												String content2=vn.toString(text2);
												
												if(content2.contains(InToArr[j])){
													fileFlag=true;
													queryFlag=true;
												}
												else{
													bm.setCursorPosition();
													//���ﶼû��ƥ�䵽ɾ��
													xm.remove();
													//System.out.println("remove    "+ll);
												}
											}
											
											else{
												bm.setCursorPosition();
												//���ﶼû��ƥ�䵽ɾ��
												xm.remove();
												//System.out.println("remove    "+ll);
											}
										}
									}
									
									else{
										bm2.setCursorPosition();
										if(vn.toElement(VTDNav.FC,"phone")){
											int text2=vn.getText();
											String content2=vn.toString(text2);
											
											if(content2.contains(InToArr[j])){
												fileFlag=true;
												queryFlag=true;
											}
											else{
												bm.setCursorPosition();
												//���ﶼû��ƥ�䵽ɾ��
												xm.remove();
												//System.out.println("remove    "+ll);
											}
										}
										
										else{
											bm.setCursorPosition();
											//���ﶼû��ƥ�䵽ɾ��
											xm.remove();
											//System.out.println("remove    "+ll);
										}
									}
									

								}
								
								
								
								
							}
						}
						bm.setCursorPosition();
					}
				}
				if(fileFlag){
					
					//String filepath[]=layerfile.split("\\\\");
					String filepath[] = layerfile.split(File.separator + File.separator);
					String fileout =  filePath;
					fileout = fileout + "new" + File.separator;
					fileout = fileout+filepath[filepath.length-1]+"_"+filepath[filepath.length-1]+"_"+InToArr[j]+"_nw"+outFileCount+".svg";
				xm.output(new FileOutputStream(fileout));
				newFilePaths.add(fileout);
				outFileCount++;
			}
		}
		if(!queryFlag){
			out+=" "+InToArr[j];
			}
		}
		return out;
	}

	
	//handle one request
	public void handleOne(String mode) throws ModifyException, XPathParseException,
			XPathEvalException, NavException, TranscodeException,
			FileNotFoundException, IOException {
		VTDGen vg = new VTDGen();
		XMLModifier xm = new XMLModifier();
		if (vg.parseFile(filePath, false)) {
			vn = vg.getNav();
			ap = new AutoPilot(vn);
			xm.bind(vn);
			BookMark bm = new BookMark(vn);
			//

			modPar = new ModeParser(mode);
			String path = "//g[@id='" + modPar.getElement() + "']/g";
			ap.resetXPath();
			ap.selectXPath(path);

			int i = -1;
			while ((i = ap.evalXPath()) != -1) {
				bm.recordCursorPosition();
				String Elename = vn.toString(i);
				//
				int valLoc = vn.getAttrVal(modPar.getAttr());
				String val = vn.toString(valLoc);
				if (modPar.operate(Integer.parseInt(val))) {

					// path????
					// vn.toElement(VTDNav.FC,"path");

					// ֻ�޸�һ��Ԫ�ص���ɫ
					vn.toElement(VTDNav.FIRST_CHILD);
					int colorLoc = vn.getAttrVal("stroke");
					String color = vn.toString(colorLoc);
					if (colorLoc != -1) {
						xm.updateToken(colorLoc, "red");
					}

				}

				// int j=vn.getAttrVal("")
				bm.setCursorPosition();
			}

		}
		
/*		String path = "//g[@id='" + modPar.getElement() + "']";
		ap.resetXPath();
		ap.selectXPath(path);
		ap.evalXPath();
		String xml=getCurrentXML();*/
		
		// ���������ļ���path �������ļ���path
		String[] infileArr = filePath.split("/");
		String fileout = "";

		for (int i = 0; i < infileArr.length - 1; i++) {
			fileout += infileArr[i];
			fileout += "/";
		}
		fileout += "new/";
		fileout = fileout+ mode+"_";
		fileout += infileArr[infileArr.length - 1];
		
		
/*		FileWriter fw=new FileWriter(fileout);
		fw.write(xml.toCharArray());
		fw.flush();
		fw.close();
		*/
		//xm.bind(vn);
		xm.output(new FileOutputStream(fileout));

	}
	
	//handle one request
	public void handleOneWithLayer(String mode,String layerfile) throws ModifyException, XPathParseException,
				XPathEvalException, NavException, TranscodeException,
				FileNotFoundException, IOException {
			VTDGen vg = new VTDGen();
			XMLModifier xm = new XMLModifier();
			if (vg.parseFile(layerfile, false)) {
				vn = vg.getNav();
				ap = new AutoPilot(vn);
				xm.bind(vn);
				BookMark bm = new BookMark(vn);
				//

				modPar = new ModeParser(mode);
				String path = "//g[@class='entity']";
				ap.resetXPath();
				ap.selectXPath(path);

				int i = -1;
				while ((i = ap.evalXPath()) != -1) {
					bm.recordCursorPosition();
					String Elename = vn.toString(i);
					//
					if(vn.toElement(VTDNav.FC,"info"))
					{
						if(vn.toElement(VTDNav.FC,modPar.getAttr()))
						{
							int text=vn.getText();
							String content=vn.toString(text);
							if (modPar.operate(Integer.parseInt(content)))
							{}
							else
							{
								bm.setCursorPosition();
								xm.remove();
							}
						}
						else
						{
							bm.setCursorPosition();
							xm.remove();
						}
					}
/*					int valLoc = vn.getAttrVal(modPar.getAttr());
					String val = vn.toString(valLoc);
					if (modPar.operate(Integer.parseInt(val))) {

						// path????
						// vn.toElement(VTDNav.FC,"path");

						// ֻ�޸�һ��Ԫ�ص���ɫ
						vn.toElement(VTDNav.FIRST_CHILD);
						int colorLoc = vn.getAttrVal("stroke");
						String color = vn.toString(colorLoc);
						if (colorLoc != -1) {
							xm.updateToken(colorLoc, "red");
						}

					}
*/
					// int j=vn.getAttrVal("")
					bm.setCursorPosition();
				}

			}
			
	/*		String path = "//g[@id='" + modPar.getElement() + "']";
			ap.resetXPath();
			ap.selectXPath(path);
			ap.evalXPath();
			String xml=getCurrentXML();*/
			
			String fileout =  filePath;
/*			// ���������ļ���path �������ļ���path
			String[] infileArr = filePath.split("/");
			

			for (int i = 0; i < infileArr.length - 1; i++) {
				fileout += infileArr[i];
				fileout += "/";
			}*/
			
			fileout +="/new/";
			fileout = fileout+mode+"_nw.svg";
			//fileout += infileArr[infileArr.length - 1];
			
			
	/*		FileWriter fw=new FileWriter(fileout);
			fw.write(xml.toCharArray());
			fw.flush();
			fw.close();
			*/
			//xm.bind(vn);
			xm.output(new FileOutputStream(fileout));

		}
	
	//handle one request  store filepath
	public void handleOnce(String mode,String layerfile,ArrayList<String> returnPaths) throws ModifyException, XPathParseException,
						XPathEvalException, NavException, TranscodeException,
						FileNotFoundException, IOException {
					VTDGen vg = new VTDGen();
					XMLModifier xm = new XMLModifier();
					if (vg.parseFile(layerfile, false)) {
						vn = vg.getNav();
						ap = new AutoPilot(vn);
						xm.bind(vn);
						BookMark bm = new BookMark(vn);
						//

						modPar = new ModeParser(mode);
						String path = "//g[@class='entity']";
						ap.resetXPath();
						ap.selectXPath(path);

						int i = -1;
						while ((i = ap.evalXPath()) != -1) {
							bm.recordCursorPosition();
							String Elename = vn.toString(i);
							//
							if(vn.toElement(VTDNav.FC,"info"))
							{
								if(vn.toElement(VTDNav.FC,modPar.getAttr()))
								{
									int text=vn.getText();
									String content=vn.toString(text);
									if (modPar.operate(Integer.parseInt(content)))
									{}
									else
									{
										bm.setCursorPosition();
										xm.remove();
									}
								}
								else
								{
									bm.setCursorPosition();
									xm.remove();
								}
							}

							bm.setCursorPosition();
						}

					}
	
					String fileout =  filePath;

					fileout +="/new/";
					fileout = fileout+mode+"_nw.svg";
					returnPaths.add(fileout);
					xm.output(new FileOutputStream(fileout));

				}
				
	//ouput into several files depends on the requests count
	public void handleSeparately()
	{
		String modeArr[]=modes.split("\\+");
		for(String mode :modeArr)
		{
			try {
				long startMili=System.currentTimeMillis();
				handleOne(mode);
				
				long endMili=System.currentTimeMillis();
				System.out.println("�ܺ�ʱΪ��"+(endMili-startMili)+"����  in handler");
				
			} catch (ModifyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathEvalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NavException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TranscodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//ouput into several files depends on the requests count with different layers
	public void handleSeparatelyWithLayer(ArrayList<String> layerFileLocation)
		{
			String modeArr[]=modes.split("\\+");
			for(int i=0;i<modeArr.length;i++)
			{
				try {
					long startMili=System.currentTimeMillis();
				
					handleOneWithLayer(modeArr[i],layerFileLocation.get(i));
					
					long endMili=System.currentTimeMillis();
					System.out.println("�ܺ�ʱΪ��"+(endMili-startMili)+"����  in handler");
					
				} catch (ModifyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XPathParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XPathEvalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NavException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TranscodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	//ouput into several files depends on the requests count with different layers  return new files paths
	public ArrayList<String> handleSeparatelyRetrunPaths(ArrayList<String> layerFileLocation)
		{
			ArrayList<String> filePaths=new ArrayList<String>();
			String modeArr[]=modes.split("\\+");
			for(int i=0;i<modeArr.length;i++)
			{
				try {
					long startMili=System.currentTimeMillis();
				
					handleOnce(modeArr[i],layerFileLocation.get(i),filePaths);
					
					long endMili=System.currentTimeMillis();
					System.out.println("�ܺ�ʱΪ��"+(endMili-startMili)+"����  in handler");
					
				} catch (ModifyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XPathParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XPathEvalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NavException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TranscodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return filePaths;
		}
	
	//output all into one file
	public void handleTogether() throws ModifyException, XPathParseException,
			XPathEvalException, NavException, TranscodeException,
			FileNotFoundException, IOException {
		VTDGen vg = new VTDGen();
		XMLModifier xm = new XMLModifier();
		if (vg.parseFile(filePath, false)) {
			vn = vg.getNav();
			AutoPilot ap = new AutoPilot(vn);
			xm.bind(vn);
			BookMark bm = new BookMark(vn);
			//

			String cmd[] = modes.split("\\+");
			for (int j = 0; j < cmd.length; j++) {
				modPar = new ModeParser(cmd[j]);
				String path = "//g[@id='" + modPar.getElement() + "']/g";
				ap.resetXPath();
				ap.selectXPath(path);

				int i = -1;
				while ((i = ap.evalXPath()) != -1) {
					bm.recordCursorPosition();
					String Elename = vn.toString(i);

					//
					int valLoc = vn.getAttrVal(modPar.getAttr());
					String val = vn.toString(valLoc);
					if (modPar.operate(Integer.parseInt(val))) {

						// path????
						// vn.toElement(VTDNav.FC,"path");

						// ֻ�޸�һ��Ԫ�ص���ɫ
						vn.toElement(VTDNav.FIRST_CHILD);
						int colorLoc = vn.getAttrVal("stroke");
						String color = vn.toString(colorLoc);
						if (colorLoc != -1) {
							try{
							xm.updateToken(colorLoc, "red");
							}
							catch(Exception e)
							{
								int ii=0;
								//������ѯ�н��� ���׳��쳣
							}
						}

					}

					// int j=vn.getAttrVal("")
					bm.setCursorPosition();
				}

			}
		}

		// ���������ļ���path �������ļ���path
		String[] infileArr = filePath.split("/");
		String fileout = "";

		for (int i = 0; i < infileArr.length - 1; i++) {
			fileout += infileArr[i];
			fileout += "/";
		}
		fileout += "new/";
		
		File file =new File(fileout);    
		//����ļ��в������򴴽�    
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    System.out.println("//������");  
		    file .mkdir();    
		} else   
		{  
		    System.out.println("//Ŀ¼����");  
		}  
		
		fileout += infileArr[infileArr.length - 1];

		xm.output(new FileOutputStream(fileout));
	}
	

	private void getAllattrs()
	{
		String modeArr[]=modes.split("\\+");
		for(String mode :modeArr)
		{
			modPar = new ModeParser(mode);
			attrsArray.add(modPar.getElement());
		}
		
	}
	/*private void modesIntoArray()
	{
		String cmd[] = modes.split("\\+");
		Arrays.sort(cmd);
		for(int i=0;i<cmd.length;i++)
		{
			if(i==0)
			{
				modesArray.add(cmd[i]);
			}
			else
			{
				if(modesArray.get(modesArray.size()-1).charAt(0)==cmd[i].charAt(0))
				{
					modesArray.set(modesArray.size()-1, modesArray.get(modesArray.size()-1)+"+"+cmd[i]);
				}
				else
				{
					modesArray.add(cmd[i]);
				}
			}
		}
	}
	*/
	
	private String getCurrentXML()
	{
		
		try {
			//long l=vn.getContentFragment();
			long l=vn.getElementFragment();
			return vn.toString((int)l,(int)(l>>32));
		} catch (NavException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;
		}
	}
	
/*	public void setLayerFiles(ArrayList<String> in)
	{
		LayerFiles=in;
	}*/
	
	
	//��ʹ��layerLocationʱ��filepath��Ϊ����ļ���ʹ��
	private String filePath;
	private ModeParser modPar;
	private String modes;
	//private ArrayList<String> LayerFiles;
	private VTDNav vn;
	private AutoPilot ap;
/*	public ArrayList<String> modesArray; */
	
	//����mode��Ӧ��layer����
	public ArrayList<String> attrsArray;
}
