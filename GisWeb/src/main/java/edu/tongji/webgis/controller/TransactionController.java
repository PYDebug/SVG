package edu.tongji.webgis.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import edu.tongji.webgis.form.UserForm;
import edu.tongji.webgis.model.*;
import edu.tongji.webgis.service.UserService;
import edu.tongji.webgis.utils.DataWrapper;
import edu.tongji.webgis.utils.ErrorCode;
import edu.tongji.webgis.utils.RequiredRole;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import btreeSearch.BPlusTreeIndex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ximpleware.ModifyException;
import com.ximpleware.NavException;
import com.ximpleware.TranscodeException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

import edu.tongji.webgis.service.MatchingService;
import edu.tongji.webgis.svg.layer.model.LayerShow;
import edu.tongji.webgis.svg.matching.DiffSVG;
import edu.tongji.webgis.utils.LogRecorder;
import svg.Lexicon;
import svg.VTDsvg;
import edu.tongji.webgis.model.User.Role;

@Controller
public class TransactionController {

	@Autowired
	UserService us;

	// @Autowired
	private HttpServletRequest request;

	@RequestMapping("/api/home")
	public String loadHomePage(Model m) {
		// m.addAttribute("name", "CodeTutr");
		return "index";
	}

	@RequestMapping("/api/uploadSuccess")
	public String loadUploadSuccess(Model m) {
		// m.addAttribute("name", "CodeTutr");
		return "uploadSuccess";
	}

	@RequestMapping("/api/uploadFailed")
	public String loadUploadFailed(Model m) {
		// m.addAttribute("name", "CodeTutr");
		return "uploadFailed";
	}


	public String getImagePixel(String image) throws Exception {
		int[] rgb = new int[3];
		File file = new File(image);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		int pixel = bi.getRGB(width/2, height/2); //下面三行代码将一个数字转换为RGB数字
		rgb[0] = (pixel & 0xff0000) >> 16;
		rgb[1] = (pixel & 0xff00) >> 8;
		rgb[2] = (pixel & 0xff);
		return ("{\"R\":"+rgb[0]+",\"G\":"+rgb[1]+",\"B\":"+rgb[2]+"}");
	}

	@RequestMapping(value = "/api/getColor",method = RequestMethod.POST)
	public void getColor(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String s;
		float lng = Float.parseFloat(request.getParameter("lng"));
		float lat = Float.parseFloat(request.getParameter("lat"));
		String phanthomPath = request.getSession().getServletContext()
				.getRealPath("/resources/phantomjs.exe");
		String rasterizePath = request.getSession().getServletContext()
				.getRealPath("/resources/rasterize.js");
		String phantomPicPath = request.getSession().getServletContext()
				.getRealPath("/resources/phantomPic.png");
		//执行phanthom.js 生成图片
		Process process = Runtime.getRuntime().exec(phanthomPath + " " + rasterizePath +" \"http://localhost:8080/resources/getColor.html?lng="+lng+"&lat="+lat+"\" " + phantomPicPath + " 101px*101px");
		process.waitFor();
		//运行readColor java程序去读取图片中心点像素的颜色
//		String readColorPath = request.getSession().getServletContext()
//				.getRealPath("/resources/readColor.jar");
		s = getImagePixel(phantomPicPath);
//		process = Runtime.getRuntime().exec("java -jar " + readColorPath + " " + phantomPicPath);
//		//将java程序的输出结果读出来
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//		s=bufferedReader.readLine();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(s);
		System.out.println(s);
//		s=bufferedReader.readLine();
//		System.out.println(s);
		process.waitFor();

	}





	@RequestMapping(value = "/api/upload", method = RequestMethod.GET)
	public String showUploadPage() {

		return "upload";
	}

	@RequestMapping(value = "/api/register", method = RequestMethod.POST)
	@ResponseBody
	public DataWrapper register(@RequestBody UserForm user){
		DataWrapper dataWrapper = new DataWrapper();
		try {
			User u = us.addUser(user.getUsername(), user.getPassword(), user.getRole());
			dataWrapper.setData(u);
		}catch (DataAccessException e){
			final Throwable cause = e.getCause();
			if( cause instanceof MySQLIntegrityConstraintViolationException ) {
				dataWrapper.setErrorCode(ErrorCode.DUPLICATION);
				dataWrapper.setData("已经存在该用户");
			}
		}
		return dataWrapper;
	}

//	@RequestMapping(value = "uploadTS/{mapId}/{timeStamp}", method = RequestMethod.POST)
//	public String handleFormUploadTS(@PathVariable int mapId,
//			@PathVariable String timeStamp,
//			@RequestParam("inputUploadFile") MultipartFile file,
//			HttpServletRequest request) throws IOException {
//
//		System.out.print("uploading TimeStamp for map:" + mapId + " at Time:"
//				+ timeStamp);
//		if (!file.isEmpty()) {
//			System.out.println(mapId);
//
//			System.out.println("filesize: " + file.getSize());
//			System.out.println("filecontent: " + file.getContentType());
//			System.out.println("filename: " + file.getName());
//			System.out.println("originalfilename: "
//					+ file.getOriginalFilename());
//			System.out.println("========================================");
//
//			String realPath = request.getSession().getServletContext()
//					.getRealPath("/WEB-INF/resource/svg");
//			LogRecorder.info(realPath);
//
//			TimeStampService tsService = new TimeStampService(request);
//			tsService.init();
//
//			int mapVersion = tsService.getMapMaxVersion(mapId) + 1;
//			System.out.println(" for version:" + mapVersion);
//			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(
//					realPath, mapId + "_" + mapVersion + ".svg"));
//
//			Date date = Date.valueOf(timeStamp);
//			TimeStamp ts = new TimeStamp(date, mapVersion, mapId);
//
//			tsService.add(ts);
//
//			MatchingService matchService = new MatchingService(realPath);
//			matchService.MatchSVG(mapId + "", (mapVersion - 1) + "", mapVersion
//					+ "", new DiffSVG());
//			// matchService.MatchingSVG("1", "01", "02", new DiffSVG());
//			// newMap.setTypeId(mapType);
//
//			// ;
//			/*
//			 * try { byte[] bytes = file.getBytes(); File dirFile=new
//			 * File("nima111.svg"); if(!dirFile.exists()) { dirFile.mkdir(); }
//			 * FileOutputStream fos = new FileOutputStream("nima111.svg");
//			 *
//			 * fos.write(bytes); fos.close(); } catch (IOException e) { // TODO
//			 * Auto-generated catch block e.printStackTrace(); }
//			 */
//
//			return "uploadSuccess";
//		} else {
//			return "uploadFailed";
//		}
//	}

	@RequestMapping(value = "/api/fuzzyQuery", method = RequestMethod.POST)
	@RequiredRole({Role.ADMIN,Role.NORMAL_USER,Role.SPECIAL_USER})
	public void fuzzyQuery(HttpServletRequest request/*
													 * ,@RequestParam("msg")
													 * String msg
													 */,
			HttpServletResponse response) throws IOException, ModifyException,
			XPathParseException, XPathEvalException, NavException,
			TranscodeException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		System.out.println("data = "+ data);
		/*String lexiconPath = request.getSession().getServletContext()
				.getRealPath(File.separator + "resources" + File.separator + "lexicon");*/
		String lexiconPath = request.getSession().getServletContext()
				.getRealPath("/");
		lexiconPath = lexiconPath + "resources" + File.separator + "lexicon"+File.separator;
		/*String lexiconPath = request.getSession().getServletContext()
				.getRealPath("/resources/lexicon");*/
		System.out.println("lexiconpath="+lexiconPath);
		Lexicon lex = new Lexicon(lexiconPath, true);

		/*String resultsPath = request.getSession().getServletContext()
				.getRealPath(File.separator +"WEB-INF" + File.separator + "resource" + File.separator + "svg" + File.separator + "blocks");*/
		String resultsPath = request.getSession().getServletContext()
				.getRealPath("/");
		resultsPath = resultsPath + "WEB-INF" + File.separator + "resource" + File.separator + "svg" + File.separator + "blocks"+ File.separator;
		String hospital = resultsPath +"hos" + File.separator + "1_1.svg";
		String hotel = resultsPath +"hotel" + File.separator + "1_1.svg";
		String rest = resultsPath + "rest" + File.separator + "1_1.svg";
		String sch = resultsPath + "sch" + File.separator + "1_1.svg";
		String su = resultsPath + "su" + File.separator + "1_1.svg";
		String cine = resultsPath + "cine" + File.separator + "1_1.svg";
		String river = resultsPath + "river" + File.separator + "1_1.svg";
//		String hotel = resultsPath + "/hotel/1_1.svg";
//		String rest = resultsPath + "/rest/1_1.svg";
//		String sch = resultsPath + "/sch/1_1.svg";
//		String su = resultsPath + "/su/1_1.svg";
//		String cine = resultsPath + "/cine/1_1.svg";
//		String river = resultsPath + "/river/1_1.svg";
		
		LinkedList<String> finalResult = new LinkedList<String>();
		ArrayList<String> normalQueryResult = new ArrayList<String>();

		ArrayList<String> Alllayers = new ArrayList<String>();
		Alllayers.add(hospital);
		Alllayers.add(hotel);
		Alllayers.add(rest);
		Alllayers.add(sch);
		Alllayers.add(su);
		Alllayers.add(cine);
		Alllayers.add(river);
		VTDsvg normalHandler = new VTDsvg(resultsPath, null);
		data = normalHandler.NormalQuery(data, Alllayers, normalQueryResult);
		for (String result : normalQueryResult) {
			System.out.println("result:normal " + result);
		}
		finalResult.addAll(normalQueryResult);
		String mode = lex.getMode(new StringBuffer(data));
		System.out.println(mode);

		/*
		 * String mapPath =
		 * request.getSession().getServletContext().getRealPath(
		 * "/WEB-INF/resource/svg"); VTDsvg handler3 = new
		 * VTDsvg(mapPath+"/1_1.svg", mode); handler3.handleTogether();
		 */

		System.out.println(resultsPath + "/new");
		System.out.println(hospital);
		File file = new File(resultsPath + "/new");
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("in mkdir");
			file.mkdir();
		} else {
			String[] childFilePaths = file.list();
			for (String childFilePath : childFilePaths) {
				File childFile = new File(file.getAbsolutePath() + "/"
						+ childFilePath);
				childFile.delete();
			}

		}
		
		List<QueryResult> responseList = new LinkedList<QueryResult>();
		
		if (mode != "") {
			ArrayList<String> layers = new ArrayList<String>();
			String modeArr[] = mode.split("/+");
			for (int i = 0; i < modeArr.length; i++) {
				System.out.println(modeArr[i]);
				svg.ModeParser parser = new svg.ModeParser(modeArr[i]);
				String element = parser.getElement();
				System.out.println(element);
				if (parser.getElement() == "hospital") {
					layers.add(hospital);
				} else if (parser.getElement() == "hotels") {
					layers.add(hotel);
				} else if (parser.getElement() == "restaurant") {
					layers.add(rest);
				} else if (parser.getElement() == "school") {
					layers.add(sch);
				} else if (parser.getElement() == "supermarket") {
					layers.add(su);
				} else if (parser.getElement() == "rivers") {
					layers.add(river);
				}
			}

			VTDsvg handler4 = new VTDsvg(resultsPath, mode);

			// layers.add(testStr);
			// handler4.handleSeparatelyWithLayer(layers);
			
			List<String> resultPaths = handler4
					.handleSeparatelyRetrunPaths(layers);
			finalResult.addAll(resultPaths);

			
		}
		else{
			String errorPath = request.getSession().getServletContext()
					.getRealPath("/resources/lexicon");
			List<String> errorStrings = lex.queryFailedInfo(errorPath+"/");
			for(String errorString : errorStrings){
				System.out.println("maybe:"+errorString);
				responseList.add(new QueryResult("", errorString,0));
			}
		}
		int searchResultValue = 0;
		ObjectMapper mapper = new ObjectMapper();
		String str;
		PrintWriter out = null;

		for (String path : finalResult) {
			System.out.println("result:" + path);
			File tempF = new File(path);
			FileInputStream fis = new FileInputStream(tempF);
			FileUtils.copyInputStreamToFile(fis, new File(resultsPath
					+ "/result/1_" + searchResultValue + ".svg"));
			responseList.add(new QueryResult("" + searchResultValue, ""
					+ searchResultValue,1));
			searchResultValue++;
		}

		str = mapper.writeValueAsString(responseList);
		response.setContentType("application/json; charset=utf-8");
		out = response.getWriter();
		out.append(str);
		
		// return "uploadSuccess";
	}
	
	@RequestMapping(value = "/api/layerQuery/{layer}", method = RequestMethod.POST)
	public void layerQuery(@PathVariable String layer,HttpServletRequest request/*
													 * ,@RequestParam("msg")
													 * String msg
													 */,
			HttpServletResponse response) throws IOException, ModifyException,
			XPathParseException, XPathEvalException, NavException,
			TranscodeException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		System.out.println(data);
		String lexiconPath = request.getSession().getServletContext()
				.getRealPath("/resources/lexicon")+"/";
		System.out.println(lexiconPath);
		Lexicon lex = new Lexicon(lexiconPath, true);

		String resultsPath = request.getSession().getServletContext()
				.getRealPath("/WEB-INF/resource/svg/blocks");
		String layerPath = resultsPath + "/"+layer+"/1_1.svg";

		LinkedList<String> finalResult = new LinkedList<String>();
		ArrayList<String> normalQueryResult = new ArrayList<String>();

		ArrayList<String> Alllayers = new ArrayList<String>();
		Alllayers.add(layerPath);

		VTDsvg normalHandler = new VTDsvg(resultsPath, null);
		data = normalHandler.NormalQuery(data, Alllayers, normalQueryResult);
		for (String result : normalQueryResult) {
			System.out.println("result:normal " + result);
		}
		finalResult.addAll(normalQueryResult);
		String mode = lex.getMode(new StringBuffer(data));
		System.out.println(mode);

		/*
		 * String mapPath =
		 * request.getSession().getServletContext().getRealPath(
		 * "/WEB-INF/resource/svg"); VTDsvg handler3 = new
		 * VTDsvg(mapPath+"/1_1.svg", mode); handler3.handleTogether();
		 */

		System.out.println(resultsPath + "/new");
		File file = new File(resultsPath + "/new");
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("in mkdir");
			file.mkdir();
		} else {
			String[] childFilePaths = file.list();
			for (String childFilePath : childFilePaths) {
				File childFile = new File(file.getAbsolutePath() + "/"
						+ childFilePath);
				childFile.delete();
			}

		}
		
		if(finalResult.size()>0){
			String resultPath = finalResult.get(0);
			InputStreamReader isr = new InputStreamReader(new FileInputStream(resultPath), "UTF-8");
			BufferedReader bf = new BufferedReader(isr);
			PrintWriter out = response.getWriter();
			response.setContentType("application/xml; charset=utf-8");
			
			String valueString = null;
			while ((valueString=bf.readLine())!=null){
			    if(!valueString.equals("  ")){
			    	out.println(valueString);
			    	System.out.println(valueString);
			    }
			}
			
			out.close();
			bf.close();
			isr.close();
		}
		else{
			
		}
		

		
		// return "uploadSuccess";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	// save interface ()url
	public String saveMap(HttpServletRequest request) {

		/*ax
		 */

		try {
			System.out.println("saving changes");
			String realPath = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/resource/svg");
			System.out.println(realPath);
			File file = new File(realPath + "/1_1.svg");
			FileOutputStream fos = new FileOutputStream(file);

			InputStream is = request.getInputStream();
			byte temp[] = new byte[1024];
			int i;
			int status;
			while ((status = is.read(temp)) != -1) {
				fos.write(temp, 0, status);
			}
			is.close();
			int count= 0;
			List<String> layers = LayerShow.showLayer(realPath+ "/1_1.svg", realPath+"/temp/");
			for(String layer: layers){
				FileUtils.forceMkdir(new File(realPath+"/temp/"+layer+"/"));
				
				FileInputStream fis = new FileInputStream(realPath+"/temp/"+layer+".svg");
				FileUtils.copyInputStreamToFile(fis, new File(realPath+"/temp/"+layer+"/1_1.svg"));
				
				BPlusTreeIndex.addLayer("0000_"+Integer.toBinaryString(count), realPath+"/temp/"+layer+"/");
				String location = BPlusTreeIndex.getLayer("0000_"+Integer.toBinaryString(count));
				System.out.println(location);
				count++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "uploadSuccess";

	}
	
	@RequestMapping(value = "/save/{mapid}/{timestamp}", method = RequestMethod.POST)
	// save interface ()url
	public void saveMap(HttpServletRequest request,HttpServletResponse response
			,@PathVariable("mapid") String mapId
			,@PathVariable("timestamp") String timeStamp) {

		/*ax
		 */

		try {
			System.out.println("saving changes");
			String realPath = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/resource/svg/files/"+mapId+"/1_1");
			System.out.println(realPath);
			File file = new File(realPath + "/"+timeStamp+".svg");
			FileOutputStream fos = new FileOutputStream(file);

			InputStream is = request.getInputStream();
			byte temp[] = new byte[1024];
			int i;
			int status;
			while ((status = is.read(temp)) != -1) {
				fos.write(temp, 0, status);
			}
			is.close();
			/*int count= 0;
			List<String> layers = LayerShow.showLayer(realPath+ "/1_1.svg", realPath+"/temp/");
			for(String layer: layers){
				FileUtils.forceMkdir(new File(realPath+"/temp/"+layer+"/"));
				
				FileInputStream fis = new FileInputStream(realPath+"/temp/"+layer+".svg");
				FileUtils.copyInputStreamToFile(fis, new File(realPath+"/temp/"+layer+"/1_1.svg"));
				
				BPlusTreeIndex.addLayer("0000_"+Integer.toBinaryString(count), realPath+"/temp/"+layer+"/");
				String location = BPlusTreeIndex.getLayer("0000_"+Integer.toBinaryString(count));
				System.out.println(location);
				count++;
			}*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		String result = "{\"result\":\"success\"}";
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
	
	@RequestMapping(value = "/savenew/{mapname}/{timestamp}", method = RequestMethod.POST)
	// save interface ()url
	public void saveNewMap(HttpServletRequest request,HttpServletResponse response
			,@PathVariable("mapname") String mapId
			,@PathVariable("timestamp") String timeStamp) {

		/*ax
		 */
		String newMapId = null;
		try {
			int newId = MapCategory.getInstance().getMaps().size()+1;
			
			System.out.println("saving changes");
			String realPath = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/resource/svg/files/");
			String layerPath = realPath+"/"+newId+"/1_1";
			FileUtils.forceMkdir(new File(layerPath));
			System.out.println(layerPath);
			File file = new File(layerPath + "/"+1+".svg");
			FileOutputStream fos = new FileOutputStream(file);
			
			InputStream is = request.getInputStream();
			byte temp[] = new byte[1024];
			int status;
			while ((status = is.read(temp)) != -1) {
				fos.write(temp, 0, status);
			}
			is.close();
			newMapId = MapCategory.getInstance().addMap(mapId, timeStamp);
			/*int count= 0;
			List<String> layers = LayerShow.showLayer(realPath+ "/1_1.svg", realPath+"/temp/");
			for(String layer: layers){
				FileUtils.forceMkdir(new File(realPath+"/temp/"+layer+"/"));
				
				FileInputStream fis = new FileInputStream(realPath+"/temp/"+layer+".svg");
				FileUtils.copyInputStreamToFile(fis, new File(realPath+"/temp/"+layer+"/1_1.svg"));
				
				BPlusTreeIndex.addLayer("0000_"+Integer.toBinaryString(count), realPath+"/temp/"+layer+"/");
				String location = BPlusTreeIndex.getLayer("0000_"+Integer.toBinaryString(count));
				System.out.println(location);
				count++;
			}*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String result;
		
		if(newMapId!=null){
			result = "{\"result\":\"success\",\"mapid\":\""+newMapId+"\"}";
		}else{
			result = "{\"result\":\"failed\"}";
		}
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
	
	@RequestMapping(value = "/savets/{mapid}/{timestamp}", method = RequestMethod.POST)
	// save interface ()url
	public void saveNewTS(HttpServletRequest request,HttpServletResponse response
			,@PathVariable("mapid") String mapId
			,@PathVariable("timestamp") String timeStamp) {

		/*ax
		 */
		int newVersion = -1;
		try {
			int newTs = MapCategory.getInstance().getMap(mapId).getTSList().size()+1;
			
			System.out.println("saving changes");
			String realPath = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/resource/svg/files/"+mapId+"/1_1");
			System.out.println(realPath);
			File file = new File(realPath + "/"+newTs+".svg");
			FileOutputStream fos = new FileOutputStream(file);

			InputStream is = request.getInputStream();
			byte temp[] = new byte[1024];
			int i;
			int status;
			while ((status = is.read(temp)) != -1) {
				fos.write(temp, 0, status);
			}
			is.close();
			newVersion = MapCategory.getInstance().addTimeStamp(mapId, timeStamp);
			/*int count= 0;
			List<String> layers = LayerShow.showLayer(realPath+ "/1_1.svg", realPath+"/temp/");
			for(String layer: layers){
				FileUtils.forceMkdir(new File(realPath+"/temp/"+layer+"/"));
				
				FileInputStream fis = new FileInputStream(realPath+"/temp/"+layer+".svg");
				FileUtils.copyInputStreamToFile(fis, new File(realPath+"/temp/"+layer+"/1_1.svg"));
				
				BPlusTreeIndex.addLayer("0000_"+Integer.toBinaryString(count), realPath+"/temp/"+layer+"/");
				String location = BPlusTreeIndex.getLayer("0000_"+Integer.toBinaryString(count));
				System.out.println(location);
				count++;
			}*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String result;
		
		if(newVersion!=-1){
			result = "{\"result\":\"success\",\"version\":\""+newVersion+"\"}";
		}else{
			result = "{\"result\":\"failed\"}";
		}
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

//	@RequestMapping(value = "upload/{mapName}/{mapType}/{timeStamp}", method = RequestMethod.POST)
	/*
	 * public void handleFormUpload(@RequestParam MultipartFile[] myfiles,
	 * HttpServletRequest request) { System.out.println("success!!!!!");
	 * System.out.println(request.toString());
	 * 
	 * for(MultipartFile myfile : myfiles){ if(myfile.isEmpty()){
	 * System.out.println("�ļ�δ�ϴ�"); }else{ System.out.println("�ļ�����: " +
	 * myfile.getSize()); System.out.println("�ļ�����: " +
	 * myfile.getContentType()); System.out.println("�ļ����: " +
	 * myfile.getName()); System.out.println("�ļ�ԭ��: " +
	 * myfile.getOriginalFilename());
	 * System.out.println("========================================");
	 * //����õ���
	 * Tomcat�����������ļ����ϴ���/%TOMCAT_HOME%/webapps/YourWebProject
	 * /WEB-INF/upload/�ļ����� //String realPath =
	 * request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
	 * /
	 * /���ﲻ�ش���IO���رյ����⣬��ΪFileUtils.copyInputStreamToFile()�����ڲ����Զ����
	 * õ���IO���ص������ǿ����Դ���֪���� //
	 * FileUtils.copyInputStreamToFile(myfile.getInputStream(), new
	 * File(realPath, myfile.getOriginalFilename())); } }
	 * 
	 * }
	 */
//	public String handleFormUpload(@PathVariable String mapName,
//			@PathVariable int mapType, @PathVariable String timeStamp,
//			@RequestParam("inputUploadFile") MultipartFile file,
//			HttpServletRequest request) throws IOException {
//
//		System.out.println("success!!!!!");
//		if (!file.isEmpty()) {
//			System.out.println(mapName);
//			System.out.println(mapType);
//			System.out.println("filesize: " + file.getSize());
//			System.out.println("content type: " + file.getContentType());
//			System.out.println("filename: " + file.getName());
//			System.out.println("original file name: "
//					+ file.getOriginalFilename());
//			System.out.println("========================================");
//
//			String realPath = request.getSession().getServletContext()
//					.getRealPath("/WEB-INF/resource/svg");
//			LogRecorder.info(realPath);
//
//			Map newMap = new Map(mapName, mapType);
//
//			MapService mapService = new MapService(request);
//			mapService.init();
//			mapService.add(newMap);
//
//			System.out.println("map " + mapName + " added");
//			int newMapId = mapService.getMap(mapName).getId();
//
//			Date date = Date.valueOf(timeStamp);
//			TimeStamp ts = new TimeStamp(date, 1, newMapId);
//
//			TimeStampService tsService = new TimeStampService(request);
//			tsService.init();
//			tsService.add(ts);
//
//			System.out.println("Add new map:" + mapName + " with id:"
//					+ newMapId);
//			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(
//					realPath, newMapId + "_1.svg"));
//			// TimeStamp ts = new TimeStamp();
//			// ;
//			/*
//			 * try { byte[] bytes = file.getBytes(); File dirFile=new
//			 * File("nima111.svg"); if(!dirFile.exists()) { dirFile.mkdir(); }
//			 * FileOutputStream fos = new FileOutputStream("nima111.svg");
//			 *
//			 * fos.write(bytes); fos.close(); } catch (IOException e) { // TODO
//			 * Auto-generated catch block e.printStackTrace(); }
//			 */
//
//			return "uploadSuccess";
//		} else {
//			return "uploadFailed";
//		}
//	}

	/**
	 * Delete the specified map
	 * 
	 * @param mapId
	 *            the map id
	 * @param request
	 * @param response
	 */
//	@RequestMapping(value = "delete/{mapId}")
//	public void deleteMap(@PathVariable int mapId, HttpServletRequest request,
//			HttpServletResponse response) {
//		System.out.println("deleting map:" + mapId);
//		String str;
//		str = "删除成功";
//		PrintWriter out = null;
//		try {
//			// String arrayStr = JSONArray.fromObject(date.toString();
//			// System.out.println(jsonStr);
//			// System.out.println(jsonStr.get("name"));
//			response.setContentType("text/html; charset=utf-8");
//
//			MapService mapService = new MapService(request);
//			mapService.init();
//			mapService.delete(mapId);
//
//			out = response.getWriter();
//			out.append(str);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (out != null) {
//				out.close();
//			}
//		}
//	}

	/**
	 * Delete the specified timestamp
	 * 
	 * @param mapId
	 *            the map id
	 * @param version
	 *            the version to be deleted in the map
	 * @param request
	 * @param response
	 */
//	@RequestMapping(value = "delete/{mapId}/{version}")
//	public void deleteTimeStamp(@PathVariable int mapId,
//			@PathVariable int version, HttpServletRequest request,
//			HttpServletResponse response) {
//		System.out.println("deleting timestamp:" + mapId + "_" + version);
//		String str;
//		str = "删除成功";
//		PrintWriter out = null;
//		try {
//			// String arrayStr = JSONArray.fromObject(date.toString();
//			// System.out.println(jsonStr);
//			// System.out.println(jsonStr.get("name"));
//			response.setContentType("text/html; charset=utf-8");
//
//			TimeStampService tsService = new TimeStampService(request);
//			tsService.init();
//			tsService.delete(mapId, version);
//
//			out = response.getWriter();
//			out.append(str);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (out != null) {
//				out.close();
//			}
//		}
//
//	}

}