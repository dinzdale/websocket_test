package gjacobs.com.websockettest.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by brettjacobs on 12/26/14.
 */
@Table(name = "geo")
public class GeoDAO extends Model {
    @Column(name = "lat")
    String lat;
    @Column(name = "lng")
    String lng;

    public GeoDAO(Geo geo) {
        setLat(geo.getLa());
        setLng(geo.getLng());
    }

    public GeoDAO() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
