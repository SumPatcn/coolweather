package cc.sumpat.coolweather.gson;

import java.util.List;

public class Aqi {

    /**
     * basic : {"cid":"CN101260101","location":"贵阳","parent_city":"贵阳","admin_area":"贵州","cnty":"中国","lat":"26.57834244","lon":"106.71347809","tz":"+8.00"}
     * update : {"loc":"2019-02-15 10:56","utc":"2019-02-15 02:56"}
     * status : ok
     * air_now_city : {"aqi":"23","qlty":"优","main":"-","pm25":"12","pm10":"23","no2":"17","so2":"10","co":"0.8","o3":"16","pub_time":"2019-02-15 10:00"}
     * air_now_station : [{"air_sta":"马鞍山","aqi":"13","asid":"CNA1439","co":"0.4","lat":"26.6029","lon":"106.6856","main":"-","no2":"14","o3":"13","pm10":"13","pm25":"4","pub_time":"2019-02-15 10:00","qlty":"优","so2":"6"},{"air_sta":"市环保站","aqi":"25","asid":"CNA1440","co":"0.9","lat":"26.5689","lon":"106.6971","main":"-","no2":"23","o3":"13","pm10":"25","pm25":"7","pub_time":"2019-02-15 10:00","qlty":"优","so2":"8"},{"air_sta":"鉴湖路","aqi":"58","asid":"CNA1441","co":"0.6","lat":"26.6266","lon":"106.6243","main":"PM2.5","no2":"12","o3":"10","pm10":"55","pm25":"41","pub_time":"2019-02-15 10:00","qlty":"良","so2":"3"},{"air_sta":"燕子冲","aqi":"23","asid":"CNA1442","co":"0.8","lat":"26.6343","lon":"106.7487","main":"-","no2":"11","o3":"21","pm10":"23","pm25":"14","pub_time":"2019-02-15 10:00","qlty":"优","so2":"6"},{"air_sta":"碧云窝","aqi":"19","asid":"CNA1443","co":"0.8","lat":"26.4364","lon":"106.6554","main":"-","no2":"17","o3":"15","pm10":"19","pm25":"3","pub_time":"2019-02-15 10:00","qlty":"优","so2":"8"},{"air_sta":"中院村","aqi":"16","asid":"CNA1444","co":"0.9","lat":"26.5155","lon":"106.6948","main":"-","no2":"18","o3":"18","pm10":"13","pm25":"11","pub_time":"2019-02-15 10:00","qlty":"优","so2":"22"},{"air_sta":"红边门","aqi":"13","asid":"CNA1445","co":"0.9","lat":"26.6009","lon":"106.7105","main":"-","no2":"20","o3":"10","pm10":"13","pm25":"5","pub_time":"2019-02-15 10:00","qlty":"优","so2":"18"},{"air_sta":"新华路","aqi":"16","asid":"CNA1446","co":"0.8","lat":"26.5697","lon":"106.7164","main":"-","no2":"16","o3":"17","pm10":"16","pm25":"6","pub_time":"2019-02-15 10:00","qlty":"优","so2":"4"},{"air_sta":"太慈桥","aqi":"25","asid":"CNA1447","co":"0.9","lat":"26.5495","lon":"106.6867","main":"-","no2":"28","o3":"8","pm10":"25","pm25":"3","pub_time":"2019-02-15 10:00","qlty":"优","so2":"12"},{"air_sta":"桐木岭","aqi":"28","asid":"CNA1552","co":"0.7","lat":"26.3003","lon":"106.805","main":"-","no2":"5","o3":"33","pm10":"0","pm25":"19","pub_time":"2019-02-15 10:00","qlty":"优","so2":"4"}]
     */

    private BasicBean basic;
    private UpdateBean update;
    private String status;
    private AirNowCityBean air_now_city;
    private List<AirNowStationBean> air_now_station;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AirNowCityBean getAir_now_city() {
        return air_now_city;
    }

    public void setAir_now_city(AirNowCityBean air_now_city) {
        this.air_now_city = air_now_city;
    }

    public List<AirNowStationBean> getAir_now_station() {
        return air_now_station;
    }

    public void setAir_now_station(List<AirNowStationBean> air_now_station) {
        this.air_now_station = air_now_station;
    }

    public static class BasicBean {
        /**
         * cid : CN101260101
         * location : 贵阳
         * parent_city : 贵阳
         * admin_area : 贵州
         * cnty : 中国
         * lat : 26.57834244
         * lon : 106.71347809
         * tz : +8.00
         */

        private String cid;
        private String location;
        private String parent_city;
        private String admin_area;
        private String cnty;
        private String lat;
        private String lon;
        private String tz;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getParent_city() {
            return parent_city;
        }

        public void setParent_city(String parent_city) {
            this.parent_city = parent_city;
        }

        public String getAdmin_area() {
            return admin_area;
        }

        public void setAdmin_area(String admin_area) {
            this.admin_area = admin_area;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }
    }

    public static class UpdateBean {
        /**
         * loc : 2019-02-15 10:56
         * utc : 2019-02-15 02:56
         */

        private String loc;
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }

    public static class AirNowCityBean {
        /**
         * aqi : 23
         * qlty : 优
         * main : -
         * pm25 : 12
         * pm10 : 23
         * no2 : 17
         * so2 : 10
         * co : 0.8
         * o3 : 16
         * pub_time : 2019-02-15 10:00
         */

        private String aqi;
        private String qlty;
        private String main;
        private String pm25;
        private String pm10;
        private String no2;
        private String so2;
        private String co;
        private String o3;
        private String pub_time;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getPub_time() {
            return pub_time;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }
    }

    public static class AirNowStationBean {
        /**
         * air_sta : 马鞍山
         * aqi : 13
         * asid : CNA1439
         * co : 0.4
         * lat : 26.6029
         * lon : 106.6856
         * main : -
         * no2 : 14
         * o3 : 13
         * pm10 : 13
         * pm25 : 4
         * pub_time : 2019-02-15 10:00
         * qlty : 优
         * so2 : 6
         */

        private String air_sta;
        private String aqi;
        private String asid;
        private String co;
        private String lat;
        private String lon;
        private String main;
        private String no2;
        private String o3;
        private String pm10;
        private String pm25;
        private String pub_time;
        private String qlty;
        private String so2;

        public String getAir_sta() {
            return air_sta;
        }

        public void setAir_sta(String air_sta) {
            this.air_sta = air_sta;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getAsid() {
            return asid;
        }

        public void setAsid(String asid) {
            this.asid = asid;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getPub_time() {
            return pub_time;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }
    }
}
