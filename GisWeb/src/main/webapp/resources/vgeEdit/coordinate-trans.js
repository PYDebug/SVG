var baseMapLevel = 15;

/**
 * Created by SHIKUN on 2015/10/10.
 */
//利用经纬度计算天地图行列号
function transLonLatToCoordinate(lon,lat) {
    var topTileFromX = -180;
    var topTileFromY = 90;

    var tdtScale = {
        18: 5.36441802978515E-06,
        17: 1.07288360595703E-05,
        16: 2.1457672119140625E-05,
        15: 4.29153442382814E-05,
        14: 8.58306884765629E-05,
        13: 0.000171661376953125,
        12: 0.00034332275390625,
        11: 0.0006866455078125,
        10: 0.001373291015625,
        9: 0.00274658203125,
        8: 0.0054931640625,
        7: 0.010986328125,
        6: 0.02197265625,
        5: 0.0439453125,
        4: 0.087890625,
        3: 0.17578125,
        2: 0.3515625,
        1: 0.703125
    };
    var coef = tdtScale[tmap.getZoom()] * 256;

    var x_num = (lon - topTileFromX) / coef;
    var y_num = (topTileFromY - lat) / coef;

    x_num = (x_num-x_base_zero)*50;
    y_num = (y_num-y_base_zero)*50;
    return {x:x_num,y:y_num};
}

/**根据行列号返回经纬度坐标
 * Created by SHIKUN on 2015/10/11.
 */
function transCoordinateToLonLat(x_num,y_num) {
    var topTileFromX = -180;
    var topTileFromY = 90;
    var tdtScale = {
        18: 5.36441802978515E-06,
        17: 1.07288360595703E-05,
        16: 2.1457672119140625E-05,
        15: 4.29153442382814E-05,
        14: 8.58306884765629E-05,
        13: 0.000171661376953125,
        12: 0.00034332275390625,
        11: 0.0006866455078125,
        10: 0.001373291015625,
        9: 0.00274658203125,
        8: 0.0054931640625,
        7: 0.010986328125,
        6: 0.02197265625,
        5: 0.0439453125,
        4: 0.087890625,
        3: 0.17578125,
        2: 0.3515625,
        1: 0.703125
    };

    x_num = x_num/50 + x_base_zero;
    y_num = y_num/50 + y_base_zero;

    var coef = tdtScale[baseMapLevel] * 256;
    var lon=x_num*coef+topTileFromX;
    var lat=topTileFromY-y_num*coef;

    return{lon:lon,lat:lat};
}
