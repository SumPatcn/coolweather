package cc.sumpat.coolweather.gson;

public class Aqi {

    /**
     * pub_time	数据发布时间,格式yyyy-MM-dd HH:mm
     * aqi	空气质量指数，AQI和PM25的关系
     * main	主要污染物
     * qlty	空气质量，取值范围:优，良，轻度污染，中度污染，重度污染，严重污染，查看计算方式
     * pm10	pm10
     * pm25	pm25
     * no2	二氧化氮
     * so2	二氧化硫
     * co	一氧化碳
     * o3	臭氧
     */

    private AirNowCityBean air_now_city;

    public AirNowCityBean getAir_now_city() {
        return air_now_city;
    }

    public void setAir_now_city(AirNowCityBean air_now_city) {
        this.air_now_city = air_now_city;
    }

    public static class AirNowCityBean {
        /**
         * aqi : 24
         * qlty : 优
         * main : -
         * pm25 : 14
         * pm10 : 24
         * no2 : 14
         * so2 : 16
         * co : 0.8
         * o3 : 33
         * pub_time : 2019-02-11 14:00
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
}
