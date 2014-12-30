package gjacobs.com.websockettest.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by brettjacobs on 12/26/14.
 */
@Table(name = "users")
public class UsersDAO extends Model implements CRUD {
    @Column(name = "name")
    String name;
    @Column(name = "username")
    String username;
    @Column(name = "email")
    String email;
    @Column(name = "address")
    AddressDAO address;
    @Column(name = "phone")
    String phone;
    @Column(name = "website")
    String website;
    @Column(name = "company")
    CompanyDAO company;

    public UsersDAO(Users users) {
        setName(users.getName());
        setUsername(users.getUsername());
        setEmail(users.getEmail());
        setAddress(users.getAddress());
        setPhone(users.getPhone());
        setWebsite(users.getWebsite());
        setAddress(users.getAddress());
        setCompany(users.getCompany());
    }

    public UsersDAO() {

    }

    @Override
    public void doSave() {
        getAddress().doSave();
        getCompany().doSave();
        save();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDAO getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = new AddressDAO(address);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public CompanyDAO getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = new CompanyDAO(company);
    }
}
