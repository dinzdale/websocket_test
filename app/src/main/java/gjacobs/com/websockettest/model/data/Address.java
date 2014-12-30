package gjacobs.com.websockettest.model.data;

/**
 * Created by brettjacobs on 12/25/14.
 */
public class Address {

    String street;
    String suite;
    String city;
    String zipcode;
    Geo geo;

    public Address(Address address) {
        setStreet(address.getStreet());
        setSuite(address.getSuite());
        setCity(address.getCity());
        setZipcode(address.getZipcode());
        setGeo(address.getGeo());
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

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = new Geo(geo);
    }
}