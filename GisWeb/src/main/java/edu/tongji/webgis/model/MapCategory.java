/**
 * 
 */
package edu.tongji.webgis.model;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class MapCategory {
	private static MapCategory instance=null;
	private List<Map> maps;
	 
	public static MapCategory getInstance(){
		if(instance == null)
			instance = new MapCategory();
		return instance;
	}
	
	private MapCategory(){
		maps = new LinkedList<Map>();
		Map map = new Map("su","超市",1);
		map.addTimestamp(new TimeStamp(Date.valueOf("2015-04-05"),1,1));
		map.addTimestamp(new TimeStamp(Date.valueOf("2015-05-13"),2,1));
		maps.add(map);
		
		map = new Map("hos","医院",1);
		map.addTimestamp(new TimeStamp(Date.valueOf("2015-04-05"),1,1));
		maps.add(map);
		
		map = new Map("rest","饭馆",1);
		map.addTimestamp(new TimeStamp(Date.valueOf("2020-1-1"),1,1));
		//map.addTimestamp(new TimeStamp(Date.valueOf("2012-1-1"),2,1));
		maps.add(map);
	}
	
	public String getAllMaps(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0;i<maps.size();i++){
			sb.append(maps.get(i).toString());
			if(i!=maps.size()-1) sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public List<Map> getMaps(){
		return maps;
	}
	
	public String addMap(String name, String timestamp){
		String newMapId = ""+(maps.size()+1);
		Map map = new Map(newMapId,name,1);
		map.addTimestamp(new TimeStamp(Date.valueOf(timestamp),1,1));
		maps.add(map);
		return newMapId;
	}
	
	public Map getMap(String mapId){
		Map result = null;
		for(Map map : maps){
			if(map.getMapId().equals(mapId)){
				result = map;
				break;
			}
		}
		return result;
	}
	
	public int addTimeStamp(String id, String timestamp){
		Map map = getMap(id);
		int newTs = map.getTSList().size()+1;
		map.addTimestamp(new TimeStamp(Date.valueOf(timestamp),newTs,1));
		return newTs;
	}
}
