package edu.tongji.webgis.service.impl;

import edu.tongji.webgis.Result.MapResult;
import edu.tongji.webgis.dao.mapper.MapMapper;
import edu.tongji.webgis.model.SMap;
import edu.tongji.webgis.model.TimeStamp;
import edu.tongji.webgis.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by panyan on 16/5/16.
 */
@Service("MapService")
public class MapServiceImpl implements MapService{

    @Autowired
    MapMapper mapMapper;

    @Override
    public List<MapResult> getAllMaps() {
        List<SMap> maps = mapMapper.selectMaps();
        List<MapResult> mapResults = new ArrayList<>();
        List<String> tempTags = new ArrayList<>();
        for (SMap map : maps){
            String tag = map.getTag();
            if (!tempTags.contains(tag)){
                tempTags.add(tag);
                MapResult result = new MapResult();
                result.setId(tag);
                result.setName(map.getName());
                result.setTypeid(map.getType());
                TimeStamp ts = new TimeStamp( DateFormat.getDateInstance().format(map.getCreatetime()), map.getVersion(), map.getId());
                List<TimeStamp> tslist = new ArrayList<>();
                tslist.add(ts);
                result.setTslist(tslist);
                mapResults.add(result);
            }else {
                for (MapResult mapResult : mapResults){
                    if (mapResult.getId().equals(tag)){
                        TimeStamp ts = new TimeStamp( DateFormat.getDateInstance().format(map.getCreatetime()), map.getVersion(), map.getId());
                        mapResult.getTslist().add(ts);
                    }
                }
            }
        }
        return mapResults;
    }

    @Override
    public int getRecentMapVersion(String layer) {
        List<SMap> maps = mapMapper.selectMapsByTag(layer);
        int version = 1;
        for (SMap map : maps){
            if (map.getVersion() > version){
                version = map.getVersion();
            }
        }
        return version;
    }

    @Override
    public void addNewMap(String tag, String name, Date ts, int version, int type) throws DataAccessException {
        SMap map = new SMap();
        map.setTag(tag);
        map.setName(name);
        map.setCreatetime(ts);
        map.setVersion(version);
        map.setType(type);
        try {
            mapMapper.insertMap(map);
        }catch (DataAccessException e){
            throw e;
        }
    }

}
