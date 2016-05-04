package edu.tongji.webgis.svg.query;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.ximpleware.AutoPilot;
import com.ximpleware.BookMark;
import com.ximpleware.ModifyException;
import com.ximpleware.NavException;
import com.ximpleware.TranscodeException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XMLModifier;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

public class VtdSvgSyn extends Thread{
	public VtdSvgSyn(String filein){
		filePath = filein;
		//modPar= new ModeParser(mode_op);
		//modes=mode_op;
		//threadNum=modes.split(regex);
		startedThread++;
		threadId=startedThread;
	}
	
	public static void setMode(String modesIn)
	{
		modes=modesIn;
		modeArr=modes.split("\\+");
		threadNum=modeArr.length;
	}
	
	public void run()
	{
		
		try {
			long startMili=System.currentTimeMillis();
			handleOne(modeArr[threadId-1]);
			long endMili=System.currentTimeMillis();
			System.out.println("�ܺ�ʱΪ��"+(endMili-startMili)+"���� from thread:"+threadId);
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
		
		
		stoppedThread++;

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
	//ouput into several files depends on the requests count
	public void handleSeparately()
	{
		String modeArr[]=modes.split("\\+");
		for(String mode :modeArr)
		{
			try {
				handleOne(mode);
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
	private String filePath;
	private ModeParser modPar;
	private static String modes;
	private VTDNav vn;
	private AutoPilot ap;
	public static int threadNum;
	public static int startedThread=0;
	private int threadId;
	public static int stoppedThread=0;
	public static String modeArr[];
}
