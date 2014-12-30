package gjacobs.com.websockettest.model.data;

/**
 * Created by brettjacobs on 12/25/14.
 */
public class Geo {
    String lat;
    String lng;

    public String getLa() {
        return lat;
    }

    public void setLa(String la) {
        this.lat = la;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Geo(Geo geo) {
        setLa(geo.getLa());
        setLng(geo.getLng());
    }
}
