package edu.tongji.webgis.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.tongji.webgis.model.MapCategory;

@Controller
@RequestMapping("/api")
public class BeanController {
//	@RequestMapping("getmaps")
//	public void test(HttpServletRequest request , HttpServletResponse response){
//		MapService service = new MapService(request);
//		service.init();
//		List<Map> maps = service.getAllMaps();
//		/*List<Map> maps = new LinkedList<Map>();
//		Map map1 = new Map(3, "北京地图", 1);
//		Date date = new Date(2015,5,1);
//		TimeStamp times = new TimeStamp(date,01,01);
//		map1.addTimeStamp(times);
//		Map map2 = new Map(4, "上海地铁图", 1);
//		maps.add(map1);
//		*/
//
//		//maps.add(map2);
//		//JSONObject map = new JSONObject();
//		//String jsonStr=JSONObject.fromObject(map1).toString();
//		ObjectMapper mapper = new ObjectMapper();
//		String str;
//		PrintWriter out = null;
//		try {
//			str = mapper.writeValueAsString(maps);
//           // String arrayStr = JSONArray.fromObject(date.toString();
//		   //System.out.println(jsonStr);
//           //System.out.println(jsonStr.get("name"));
//			response.setContentType("text/html; charset=utf-8");
//            out = response.getWriter();
//            out.append(str);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//	}
	
	@RequestMapping("/getlayers")
	public void getLayers(HttpServletRequest request, HttpServletResponse response){
		/*String result = "[{\"id\":\"su\",\"name\":\"超市\",\"typeId\":0,"+
						"\"tslist\":[{\"date\":\"2015-04-05\",\"version\":1,\"mapId\":1},"+
									"{\"date\":\"2015-05-13\",\"version\":2,\"mapId\":1}]},"+
	                      "{\"id\":\"hos\",\"name\":\"医院\",\"typeId\":0,"+
						"\"tslist\":[{\"date\":\"2015-04-05\",\"version\":1,\"mapId\":1}]},"+
	                      "{\"id\":\"rest\",\"name\":\"饭馆\",\"typeId\":0,"+
						"\"tslist\":[{\"date\":\"2020-1-1\",\"version\":1,\"mapId\":1},"+
	                      "{\"date\":\"2012-1-1\",\"version\":1,\"mapId\":2}]}"
						+"]";*/
		String result = MapCategory.getInstance().getAllMaps();
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = null;
        try {
			out = response.getWriter();
			response.setContentType("text/html; charset=utf-8");
			out.append(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally{
			 if (out != null) {  
	                out.close();  
	            }  
		}
        
	}
	
}
