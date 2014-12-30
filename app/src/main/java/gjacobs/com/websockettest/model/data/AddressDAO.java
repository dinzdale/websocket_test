package gjacobs.com.websockettest.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by brettjacobs on 12/26/14.
 */
@Table(name = "address")
public class AddressDAO extends Model implements CRUD {
    @Column(name = "street")
    String street;
    @Column(name = "suite")
    String suite;
    @Column(name = "city")
    String city;
    @Column(name = "zipcode")
    String zipcode;
    @Column(name = "geo")
    GeoDAO geo;

    public AddressDAO(Address address) {
        setStreet(address.getStreet());
        setSuite(address.getSuite());
        setCity(address.getCity());
        setZipcode(address.getZipcode());
        setGeo(address.getGeo());
    }

    public AddressDAO() {
    }

    @Override
    public void doSave() {
        getGeo().save();
        save();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public GeoDAO getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = new GeoDAO(geo);
    }
}
