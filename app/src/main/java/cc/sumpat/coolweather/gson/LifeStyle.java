package cc.sumpat.coolweather.gson;

public class LifeStyle {


    /**
     * brf	生活指数简介
     * txt	生活指数详细描述
     * type	生活指数类型 comf：舒适度指数、cw：洗车指数、drsg：穿衣指数、flu：感冒指数、
     *      sport：运动指数、trav：旅游指数、uv：紫外线指数、air：空气污染扩散条件指数、
     *      ac：空调开启指数、ag：过敏指数、gl：太阳镜指数、mu：化妆指数、airc：晾晒指数、
     *      ptfc：交通指数、fsh：钓鱼指数、spi：防晒指数
     */

    private String type;
    private String brf;
    private String txt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
