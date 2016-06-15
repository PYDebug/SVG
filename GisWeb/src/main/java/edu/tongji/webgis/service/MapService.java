package edu.tongji.webgis.service;

import edu.tongji.webgis.Result.MapResult;
import edu.tongji.webgis.model.SMap;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

/**
 * Created by panyan on 16/5/16.
 */
public interface MapService {

    public List<MapResult> getAllMaps();

    public int getRecentMapVersion(String layer);

    public void addNewMap(String tag, String name, Date ts, int version , int type) throws DataAccessException;

    public String getMapnameByTag(String tag);
}
