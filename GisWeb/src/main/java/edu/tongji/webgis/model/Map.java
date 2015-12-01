package edu.tongji.webgis.model;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class Map {

	private int id;
	private String name;
	private String mapId;
	private int typeId;


	List<TimeStamp> TSList = new LinkedList<TimeStamp>();


	public Map(String name, int typeId){
		this.name = name;
		this.typeId = typeId;
	}
	
	public Map(int id, String name, int typeId) {
		this.id = id;
		this.name = name;
		this.typeId = typeId;
		TSList = new LinkedList<TimeStamp>();
	}
	
	public Map(String id,String name,int typeId){
		this.mapId = id;
		this.name = name;
		this.typeId = typeId;
		TSList = new LinkedList<TimeStamp>();
	}

	public void addTimeStamp(TimeStamp ts){
		this.TSList.add(ts);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTypeId() {
		return typeId;
	}
	
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public List<TimeStamp> getTSList(){
		return this.TSList;
	}

	/**
	 * @return the mapId
	 */
	public String getMapId() {
		return mapId;
	}

	/**
	 * @param mapId the mapId to set
	 */
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public void addTimestamp(TimeStamp ts){
		TSList.add(ts);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":\""+mapId+"\"");sb.append(",");
		sb.append("\"name\":\""+name+"\"");sb.append(",");
		sb.append("\"typeid\":"+typeId);sb.append(",");
		sb.append("\"tslist\":[");
		int length = TSList.size();
		for(int i =0; i<length;i++){
			sb.append(TSList.get(i).toString());
			if(i!=length-1) sb.append(",");
		}
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}
}
