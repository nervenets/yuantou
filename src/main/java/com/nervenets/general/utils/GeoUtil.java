package com.nervenets.general.utils;

import java.math.BigDecimal;

public final class GeoUtil {

    static GeoUtil _THIS = new GeoUtil();

    public static Coordinate getCoordinate(double longitude, double latitude) {
        return _THIS.new Coordinate(longitude, latitude);
    }


    /**
     * 坐标
     *
     * @author Administrator
     */
    public class Coordinate {
        // 经度
        private double longitude;
        // 纬度
        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public Coordinate(double longitude, double latitude) {
            super();
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        @Override
        public String toString() {
            return longitude + "," + latitude;
        }
    }

    public class Bound {
        private Coordinate southWest;
        private Coordinate northEast;

        public Coordinate getSouthWest() {
            return southWest;
        }

        public void setSouthWest(Coordinate southWest) {
            this.southWest = southWest;
        }

        public Coordinate getNorthEast() {
            return northEast;
        }

        public void setNorthEast(Coordinate northEast) {
            this.northEast = northEast;
        }
    }

    private static final double EARTH_RADIUS = 6378137;// 赤道半径(单位m)

    /**
     * 转化为弧度(rad)
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 基于余弦定理求两经纬度距离
     *
     * @param lon1 第一点的精度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的精度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位m
     */
    public static double latitudeLongitudeDist(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double radLon1 = rad(lon1);
        double radLon2 = rad(lon2);

        if (radLat1 < 0) radLat1 = Math.PI / 2 + Math.abs(radLat1);// south
        if (radLat1 > 0) radLat1 = Math.PI / 2 - Math.abs(radLat1);// north
        if (radLon1 < 0) radLon1 = Math.PI * 2 - Math.abs(radLon1);// west
        if (radLat2 < 0) radLat2 = Math.PI / 2 + Math.abs(radLat2);// south
        if (radLat2 > 0) radLat2 = Math.PI / 2 - Math.abs(radLat2);// north
        if (radLon2 < 0) radLon2 = Math.PI * 2 - Math.abs(radLon2);// west
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
        double z1 = EARTH_RADIUS * Math.cos(radLat1);

        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
        double z2 = EARTH_RADIUS * Math.cos(radLat2);

        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
        // 余弦定理求夹角
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
        return BigDecimal.valueOf(theta * EARTH_RADIUS).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // 9.5441448924019
//    public static void main(String[] args) {
//        Coordinate randomCoordinate = randomCoordinate(30.649067, 104.08314, 200);
//        System.out.println(randomCoordinate);
//        System.out.println(latitudeLongitudeDist(30.649067, 104.08314, randomCoordinate.getLongitude(), randomCoordinate.getLatitude()));
        /*Bound bound = getAroundByDistance(getCoordinate(102.03100000, 30), 9540);
        System.err.println((bound.getNorthEast().getLongitude() + bound
                .getSouthWest().getLongitude()) / (double) 2);
        //101.93202000868101,29.91428081305138
        System.err.println(latitudeLongitudeDist(102.031, 30, 101.93202000868101, 29.91428081305138));

        System.err.println(9.5441448924019 * 9.5441448924019 + 9.5441448924019 * 9.5441448924019);
        System.err.println(13.497642648179545 * 13.497642648179545);*/
//        System.out.println(isValidCoordinate(122.471927,37.234543));
//    }

    /**
     * 通过经纬度算凸包正方形
     *
     * @param coordinate 经纬度
     * @param raidus     半径(m)
     * @return 土包正方形
     */
    public static Bound getAroundByDistance(Coordinate coordinate, int raidus) {
        Double latitude = coordinate.getLatitude();
        Double longitude = coordinate.getLongitude();
        Double degree = (24901 * 1609) / 360.0;
        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidus;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;
        Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidus;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        Bound rtnValue = new GeoUtil().new Bound();
        rtnValue.setNorthEast(new GeoUtil().new Coordinate(maxLng, maxLat));
        rtnValue.setSouthWest(new GeoUtil().new Coordinate(minLng, minLat));
        return rtnValue;
    }

    public static Coordinate randomCoordinate(double longitude, double latitude, int raidus) {
        Bound bound = getAroundByDistance(new GeoUtil().new Coordinate(longitude, latitude), raidus);
        BigDecimal db = new BigDecimal(Math.random() * (bound.northEast.longitude - bound.southWest.longitude) + bound.southWest.longitude);
        BigDecimal lon = db.setScale(6, BigDecimal.ROUND_HALF_UP);// 小数后6位
        db = new BigDecimal(Math.random() * (bound.northEast.latitude - bound.southWest.latitude) + bound.southWest.latitude);
        BigDecimal lat = db.setScale(6, BigDecimal.ROUND_HALF_UP);
        return new GeoUtil().new Coordinate(lon.doubleValue(), lat.doubleValue());
    }

    public static boolean isValidCoordinate(double longitude, double latitude) {
        return !(0.0 > longitude) && !(180.0 < longitude) && !(0.0 > latitude) && !(90.0 < latitude);
    }

    public static boolean isValidChinaCoordinate(double longitude, double latitude) {
        return !(73.0 > longitude) && !(135.0 < longitude) && !(17.0 > latitude) && !(53.0 < latitude);
    }
}
