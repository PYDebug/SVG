/**
 * 
 */
package edu.tongji.webgis.model;

/**
 * @author Administrator
 *
 */
public class QueryResult {
	private String layer;
	private int type;
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	private String url;
	/**
	 * @return the layer
	 */
	public String getLayer() {
		return layer;
	}
	/**
	 * @param layer the layer to set
	 */
	public void setLayer(String layer) {
		this.layer = layer;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @param layer
	 * @param url
	 */
	public QueryResult(String layer, String url,int type) {
		super();
		this.layer = layer;
		this.url = url;
		this.type = type;
	}
	
}
