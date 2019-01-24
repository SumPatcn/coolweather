package cc.sumpat.coolweather.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "province")
public class Province {
    @Id(autoincrement = true)
    private long id;

    @Property(nameInDb = "provinceName")
    private String provinceName;

    @Property(nameInDb = "provinceCode")
    private int provinceCode;
}
