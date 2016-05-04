package edu.tongji.webgis.svg.query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.codehaus.jackson.JsonParseException;







import com.ximpleware.ModifyException;
import com.ximpleware.NavException;
import com.ximpleware.TranscodeException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

public class Main {

	public static void main(String[] args) throws JsonParseException, IOException, ModifyException, XPathParseException, XPathEvalException, NavException, TranscodeException {
		// TODO Auto-generated method stub
		
			String a ="���ȴ���5000�׵ĺ���";
			String b ="����С��10000�׵ĺ���";
			String c ="���ȵ���8000�׵ĺ���";
			String dd ="�۸����150Ԫ�ľƵ�";
			String ddd ="�Ǽ�����3�ǵķ���";
			String dddd ="�����ʵ���80%�ĳ���";
			String ddddd ="��������20000�˵�ѧУ";
			String dddddd ="����ȵ���80%��ҽԺ";
			String d ="�۸����150Ԫ�ľƵ�";
			String e ="�۸����150Ԫ�ľƵ�";
			String f ="�Ƶ�ļ۸����140Ԫ�Ƶ�ļ۸����130Ԫ";
			String g ="�Ƶ�ļ۸����140Ԫ�Ƶ�ļ۸����130Ԫ�Ƶ�ļ۸����120Ԫ�Ƶ�ļ۸����150Ԫ�Ƶ�ļ۸����150Ԫ�����ĳ��ȴ���5000��";
			String l4="����С��5000�׵ĺ��� ���ȴ���8000�׵ĺ�������С��10000�׵ĺ��� ����С��15000�׵ĺ���";
			String l3="���ȴ���8000�׵ĺ�������С��10000�׵ĺ��� ����С��15000�׵ĺ���";
			String l2="����С��5000�׵ĺ��� ���ȴ���8000�׵ĺ���";
			String w="���ȴ���5000�׵ĺ������۸����150Ԫ�ľƵ�";
			String fileRiver="E:/svg_gis/SVGFILE/30_1.svg";
			String fileHotelBigger="E:/svg_gis/SVGFILE/30_1_hotelbigger.svg";
			String fileHotel="E:\\svg_gis\\SVGFILE\\30_1_hotel.svg";
			
			ArrayList<String> layers=new ArrayList<String>();
			layers.add("E:\\svg_gis\\SVGFILE\\svg_map\\TiandiResult_ҽԺ_1.svg");
			layers.add("E:\\svg_gis\\SVGFILE\\svg_map\\TiandiResult_ѧУ_1.svg");
			
			ArrayList<String> normalOutFiles=new ArrayList<String>();
			String newText=ddd+dd+" ����ҽԺ ����ҽԺ ͬ�ô�ѧ ";
			String textPhone="021-31166666";
			VTDsvg handlerNormalQuery = new VTDsvg("E:/svg_gis/SVGFILE/svg_map",null);
			String out=handlerNormalQuery.NormalQuery("����·168��", layers,normalOutFiles);
			
			
			String testStr=fileHotel;
			System.out.println("1 for non-parallel 2 for parallel");
			Scanner scan=new Scanner(System.in);
			int runningMode=scan.nextInt();


			
			Lexicon lex = new Lexicon("E:/svg_gis/lexicon/",true);
			//Lexicon lex = new Lexicon("D:/lexicon/",true);
			String mode="";
			//�ִʼ�ʱ��ʼ
			String add10="asdasdasda";
			String add100=add10;
			for(int i=0;i<9;i++)
			{
				add100=add100+add10;
			}
			
			String add1000="";
			for(int i=0;i<5;i++)
			{
				System.out.println("��ǰ������Ϊ"+(i+1)*100);
				System.out.println("***************************************************************");
				add1000=add1000+add100;
			long timecount=0;
			for(int j=0;j<10;j++)
			{
			long startMiliasd=System.currentTimeMillis();
			mode=lex.getMode(new StringBuffer(add1000+"���ȴ���5000�׵ĺ���"));
			long endMiliasd=System.currentTimeMillis();
			System.out.println("�ִ�+ģ��ƥ���ܺ�ʱΪ��"+(endMiliasd-startMiliasd)+"���� ");
			timecount+=endMiliasd-startMiliasd;
			}
			
			System.out.println("�ִ�+ģ��ƥ��ƽ����ʱΪ��"+timecount/10+"���� ");
			
			}
			//��ʱ����
			
			
			
			
			
			if(mode=="")
			{
				lex.queryFailedInfo("E:/svg_gis/lexicon/");
				for(String s:lex.queryFailed)
				{
					System.out.println("*********************_____"+s);
				}
			}
			System.out.println("there is the mode:~~~~~~~"+mode);
			
			long startMili=System.currentTimeMillis();
			switch(runningMode)
			{
			case 1:
				VTDsvg handler = new VTDsvg(testStr, mode);
				handler.handleSeparately();
				break;
			case 2:
				VtdSvgSyn.setMode(mode);
				ArrayList<VtdSvgSyn> handlers = new ArrayList<VtdSvgSyn>();
				for (int i = 0; i < VtdSvgSyn.threadNum; i++) {
					VtdSvgSyn handler2 = new VtdSvgSyn(testStr);
					handlers.add(handler2);
					handler2.start();
				}
				
				
				//Thread.sleep(VtdSvgSyn.threadNum != VtdSvgSyn.stoppedThread);
				try{
				for(VtdSvgSyn handler2:handlers)
				{
					handler2.join();
				}
				}
				catch(InterruptedException e1)
				{
					e1.printStackTrace();
				}
				break;
			case 3:
				
				VTDsvg handler3 = new VTDsvg("E:/svg_gis/SVGFILE", mode);
				handler3.handleTogether();
				
				break;
			case 4:
				 
				File file =new File("E:/svg_gis/SVGFILE/svg_map/new");
				//����ļ��в������򴴽�    
				if  (!file .exists()  && !file .isDirectory())      
				{       
				    
				    file.mkdir();    
				} else   
				{  
		            String[] childFilePaths = file.list();  
		            for(String childFilePath : childFilePaths){  
		                File childFile=new File(file.getAbsolutePath()+"\\"+childFilePath);  
		                childFile.delete();
		            }  
		            
				}  
				
				
				if(mode!=""){
				VTDsvg handler4 = new VTDsvg("E:/svg_gis/SVGFILE/svg_map",mode);
				
				//layers.add(testStr);
				
				
				
				ArrayList<String> myfilePaths=handler4.handleSeparatelyRetrunPaths(layers);
				//handler4.handleSeparatelyWithLayer(layers);
				for(String hah:myfilePaths)
				{
					System.out.println(hah);
				}
				String aasdasd="";
				}
				default:
					break;	
			}

			long endMili=System.currentTimeMillis();
			System.out.println("�ܺ�ʱΪ��"+(endMili-startMili)+"����");
			
	}
	

	
	


}
