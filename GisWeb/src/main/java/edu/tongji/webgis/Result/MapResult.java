package edu.tongji.webgis.Result;

import edu.tongji.webgis.model.TimeStamp;

import java.util.List;

/**
 * Created by panyan on 16/5/16.
 */
public class MapResult {
    //[{"id":"su","name":"超市","typeid":1,"tslist":[{"date":"2015-04-05","version":1,"mapId":1},{"date":"2015-05-13","version":2,"mapId":1}]},{"id":"hos","name":"医院","typeid":1,"tslist":[{"date":"2015-04-05","version":1,"mapId":1}]},{"id":"rest","name":"饭馆","typeid":1,"tslist":[{"date":"2020-01-01","version":1,"mapId":1}]}]
    private String id;

    private String name;

    private int typeid;

    private List<TimeStamp> tslist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public List<TimeStamp> getTslist() {
        return tslist;
    }

    public void setTslist(List<TimeStamp> tslist) {
        this.tslist = tslist;
    }
}
