/**
 * 
 */
package edu.tongji.webgis.controller;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.tongji.webgis.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.tongji.webgis.model.Map;
import edu.tongji.webgis.model.MapCategory;
import edu.tongji.webgis.service.LayerMatchingService;
import edu.tongji.webgis.service.MatchingService;
import edu.tongji.webgis.svg.compress.CompressAlgorithm;
import edu.tongji.webgis.svg.matching.DiffSVG;



/**
 * @author Yin Kanglin This class is user for services accessing to map
 *         resources
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {


	@Autowired
	MapService mapService;

	/**
	 * Get the SVG map of the specified time
	 * 
	 * @param id
	 *            The map ID
	 * @param time
	 *            The timestamp of the map
	 * @return The file location
	 */
	
	@RequestMapping("/svg/new/{id}/{time}")
	public String getNewMap(@PathVariable("id") String id,
			@PathVariable("time") int time ,HttpServletRequest request) {
		System.out.println("getting map:"+id+"_"+time);
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/resource/svg/new"); 
		
		//getMap(realPath,id,time, true);
		
		//System.out.println(id);
		//System.out.println(time);
		System.out.println("asdasdasdasdasd  newenwenwen");
		return "new/"+id + '_' + time+ ".svg";
	}
	
	
	@RequestMapping("/svg/{id}/{time}")
	public String getMap(@PathVariable("id") String id,
			@PathVariable("time") int time ,HttpServletRequest request) {
		System.out.println("getting map:"+id+"_"+time);
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/resource/svg"); 
		
		getMap(realPath,id,time, true);
		
		//System.out.println(id);
		//System.out.println(time);
		
		return id + '_' + time+ ".svg";
	}
	
	public boolean getMap(String location, String id, int time, boolean deepSearch){
		if(time <= 0) return false;
		String fileLocation = id + "_" + time + ".svg";
		File file = new File(location,fileLocation);
		if(file.exists()){
			System.out.println("file " + fileLocation +" exists");
			return true;
		}else if(deepSearch){
			System.out.println("file " + fileLocation + " not exist");
			if( getMap(location,id,time-1,true) && getDiff(location,id,time,time-1,false) ){
				MatchingService service = new MatchingService(location);
				service.MergeSVG(id, time-1+"", time+"");
				return true;
			}
			else{
				System.out.println("could not generate map:"+ fileLocation);
				return false;
			}
		}else{
			System.out.println("file " + fileLocation + " not exist");
			return false;
		}
	}
	
	public boolean getLayer(String location, String layer, int time, boolean deepSearch){
		if(time <= 0) return false;
		String fileLocation = "/" + layer + "/1_1/" + time + ".svg";
		File file = new File(location,fileLocation);
		if(file.exists()){
			System.out.println("file " + fileLocation +" exists");
			return true;
		}else if(deepSearch){
			System.out.println("file " + fileLocation + " not exist");
			if( getMap(location,layer,time-1,true) && getDiff(location,layer,time,time-1,false) ){
				LayerMatchingService service = new LayerMatchingService(location);
				service.MergeSVG(layer, time-1+"", time+"");
				return true;
			}
			else{
				System.out.println("could not generate map:"+ fileLocation);
				return false;
			}
		}else{
			System.out.println("file " + fileLocation + " not exist");
			return false;
		}
	}
	
	public boolean getDiff(String location,String id, int to, int from, boolean deepSearch){
		if(to-from<=0) return false; 
		String fileLocation = id + "_" + to + "_" + from + ".xml";
		File file = new File(location,fileLocation);
//		if(file.exists()){
//			System.out.println("file " + fileLocation +" exists");
//			return true;
//		}else if(deepSearch){
//			if( getMap(location,id,to,false) && getMap(location,id,from,false)){
//				System.out.println(fileLocation + " has two maps");
//				MatchingService service = new MatchingService(location);
//				service.MatchSVG(id, from+"", to+"", new DiffSVG());
//			}
//			System.out.println("file " + fileLocation +" not exist");
//			return false;
//		}else{
//			System.out.println("file " + fileLocation +" not exist");
//			return false;
//		}
		if( getMap(location,id,to,false) && getMap(location,id,from,false)){
			System.out.println(fileLocation + " has two maps");
			MatchingService service = new MatchingService(location);
			service.MatchSVG(id, from+"", to+"", new DiffSVG());
		}
		System.out.println("file " + fileLocation +" not exist");
		return false;
	}
	
	public boolean getLayerDiff(String location,String layer, int to, int from, boolean deepSearch){
		if(to-from<=0) return false; 
		String fileLocation = "/"+layer + "/1_1/" + to + "_" + from + ".xml";
		File file = new File(location,fileLocation);
		if(file.exists()){
			System.out.println("file " + fileLocation +" exists");
			return true;
		}else if(deepSearch){
			if( getLayer(location,layer,to,false) && getLayer(location,layer,from,false)){
				System.out.println(fileLocation + " has two maps");
				LayerMatchingService service = new LayerMatchingService(location);
				service.MatchSVG(layer, from+"", to+"", new DiffSVG());
			}
			System.out.println("file " + fileLocation +" not exist");
			return false;
		}else{
			System.out.println("file " + fileLocation +" not exist");
			return false;
		}
	}
	
	@RequestMapping("/svg/compress/{id}/{time}")
	public String getCompressedMap(@PathVariable("id") String id,
			@PathVariable("time") String time, HttpServletRequest request , HttpServletResponse response){
//		System.out.println("success2!!!!!");
//		System.out.println(id);
//		System.out.println(time);
//		
  
   	
   	String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/resource/svg");  
	String inputPath = realPath + "/" + id + "_" + time + ".svg";
	String outputPath = realPath + "/" + id + "_" + time + ".svgz";
	System.out.println(outputPath);
	CompressAlgorithm.Gzip(inputPath, outputPath);
	response.setHeader("Content-Encoding" , "gzip");
	System.out.println("**********************************************************************");
	System.out.println(outputPath);
	System.out.println("**********************************************************************");
 	return id + "_"  + time + ".svgz";
	}
	
	@RequestMapping("/svg/matching/{id}/{newTS}/{oldTS}")
	public String getMap(@PathVariable("id") String id,
			@PathVariable("newTS") int newTS, @PathVariable("oldTS") int oldTS, HttpServletRequest request) {
		System.out.println(id);
		System.out.println("getting diff:"+id+"_"+newTS+"_"+oldTS);
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/resource/svg"); 
		
		getDiff(realPath,id,newTS,oldTS, true);
		return id + '_' + newTS + "_" + oldTS + ".xml";
	}
	
	@RequestMapping("/svg/block/{layer}/{x}/{y}")
	public String getMapBlock(@PathVariable("layer") String layer, @PathVariable("x") int x, @PathVariable("y") int y){
		//return "blocks/"+layer+"/"+ x + "_" + y +".svg";
		if(layer.equals("base")||layer.equals("result"))
			return "blocks/"+layer+"/"+ x + "_" + y +".svg";
		else{
			//Map map = MapCategory.getInstance().getMap(layer);
			//System.out.println(layer+" versions:"+map.getTSList().size());
			//return "files/"+layer+"/"+x+"_"+y+"/"+map.getTSList().size()+".svg";
			return "files/"+layer+"/"+x+"_"+y+"/"+mapService.getRecentMapVersion(layer)+".svg";
		}
	}
	
	@RequestMapping("/svg/block/{layer}/{x}/{y}/{version}")
	public String getMapBlockVersion(@PathVariable("layer") String layer, @PathVariable("x") int x, @PathVariable("y") int y, @PathVariable("version") int version){
		return "files/"+layer+"/"+x+"_"+y+"/"+version+".svg";
	}
	
	@RequestMapping("/svg/block/matching/{layer}/{newTS}/{oldTS}")
	
	public String getLayerMatching(@PathVariable("layer") String layer,
			@PathVariable("newTS") int newTS, @PathVariable("oldTS") int oldTS, HttpServletRequest request) {
		System.out.println("getting diff:"+layer+"_"+newTS+"_"+oldTS);
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/resource/svg/files"); 
		
		getLayerDiff(realPath,layer,newTS,oldTS, true);
		return "files/"+layer+"/1_1/"+newTS + "_" + oldTS + ".xml";
	}
	
	@RequestMapping("/icon/{id}")
	public String getIcon(@PathVariable("id") String id){
		return "icon/"+id+".jpg";
	}
	
	@RequestMapping("/png/block/{type}/{x}/{y}")
	public String getPngMapBlock(@PathVariable("type") String type, @PathVariable("x") int x, @PathVariable("y") int y){
		return "../png/"+type+"/"+ x + "_" + y +".png";
	}
}
