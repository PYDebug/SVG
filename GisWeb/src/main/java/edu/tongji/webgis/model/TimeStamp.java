package edu.tongji.webgis.model;

import java.util.Date;

public class TimeStamp {

	private String date;
	private int version;
	private int mapId;
	
	public TimeStamp(String date, int version, int mapId) {
		this.date = date;
		this.version = version;
		this.mapId = mapId;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String toString(){
		return "{\"date\":\""+date+"\",\"version\":"+version+",\"mapId\":"+mapId+"}";
	}

}
