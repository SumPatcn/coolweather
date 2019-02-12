package cc.sumpat.coolweather.gson;

import java.util.List;

public class Weather {
    public String status;

    public Basic basic;

    public Update update;

    public Now now;

    public List<LifeStyle> lifeStyleList;

    public List<DailyForecast> dailyForecastList;
}
