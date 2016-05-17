package edu.tongji.webgis.dao.mapper;

import edu.tongji.webgis.model.SMap;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by panyan on 16/5/16.
 */
@Repository(value="MapMapper")
public interface MapMapper {
    public List<SMap> selectMaps();

    public List<SMap> selectMapsByTag(String tag);

    public void insertMap(SMap map) throws DataAccessException;
}
