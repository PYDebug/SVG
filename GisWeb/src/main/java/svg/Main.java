package svg;

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
		
			String a ="长度大于5000米的河流";
			String b ="长度小于10000米的河流";
			String c ="长度等于8000米的河流";
			String dd ="价格等于150元的酒店";
			String ddd ="星级等于3星的饭店";
			String dddd ="好评率等于80%的超市";
			String ddddd ="人数等于20000人的学校";
			String dddddd ="满意度等于80%的医院";
			String d ="价格高于150元的酒店";
			String e ="价格低于150元的酒店";
			String f ="酒店的价格低于140元酒店的价格低于130元";
			String g ="酒店的价格低于140元酒店的价格低于130元酒店的价格低于120元酒店的价格低于150元酒店的价格高于150元河流的长度大于5000米";
			String l4="长度小于5000米的河流 长度大于8000米的河流长度小于10000米的河流 长度小于15000米的河流";
			String l3="长度大于8000米的河流长度小于10000米的河流 长度小于15000米的河流";
			String l2="长度小于5000米的河流 长度大于8000米的河流";
			String w="长度大于5000米的河流，价格低于150元的酒店";
			String fileRiver="E:/svg_gis/SVGFILE/30_1.svg";
			String fileHotelBigger="E:/svg_gis/SVGFILE/30_1_hotelbigger.svg";
			String fileHotel="E:\\svg_gis\\SVGFILE\\30_1_hotel.svg";
			
			ArrayList<String> layers=new ArrayList<String>();
			layers.add("E:\\svg_gis\\SVGFILE\\svg_map\\TiandiResult_医院_1.svg");
			layers.add("E:\\svg_gis\\SVGFILE\\svg_map\\TiandiResult_学校_1.svg");
			
			ArrayList<String> normalOutFiles=new ArrayList<String>();
			String newText=ddd+dd+" 长海医院 长征医院 同济大学 ";
			String textPhone="021-31166666";
			VTDsvg handlerNormalQuery = new VTDsvg("E:/svg_gis/SVGFILE/svg_map",null);
			String out=handlerNormalQuery.NormalQuery("长海路168号", layers,normalOutFiles);
			
			
			String testStr=fileHotel;
			System.out.println("1 for non-parallel 2 for parallel");
			Scanner scan=new Scanner(System.in);
			int runningMode=scan.nextInt();


			
			Lexicon lex = new Lexicon("E:/svg_gis/lexicon/",true);
			//Lexicon lex = new Lexicon("D:/lexicon/",true);
			String mode="";
			//分词计时开始
			String add10="asdasdasda";
			String add100=add10;
			for(int i=0;i<9;i++)
			{
				add100=add100+add10;
			}
			
			String add1000="";
			for(int i=0;i<5;i++)
			{
				System.out.println("当前数据量为"+(i+1)*100);
				System.out.println("***************************************************************");
				add1000=add1000+add100;
			long timecount=0;
			for(int j=0;j<10;j++)
			{
			long startMiliasd=System.currentTimeMillis();
			mode=lex.getMode(new StringBuffer(add1000+"长度大于5000米的河流"));
			long endMiliasd=System.currentTimeMillis();
			System.out.println("分词+模型匹配总耗时为："+(endMiliasd-startMiliasd)+"毫秒 ");
			timecount+=endMiliasd-startMiliasd;
			}
			
			System.out.println("分词+模型匹配平均耗时为："+timecount/10+"毫秒 ");
			
			}
			//计时结束
			
			
			
			
			
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
				//如果文件夹不存在则创建    
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
			System.out.println("总耗时为："+(endMili-startMili)+"毫秒");
			
	}
	

	
	


}
