package gjacobs.com.websockettest.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.orm.SugarRecord;

/**
 * Created by brettjacobs on 12/26/14.
 */
@Table(name = "company")
public class CompanyDAO extends Model implements CRUD {
    @Column(name = "name")
    String name;
    @Column(name = "catchphrase")
    String catchPhrase;
    @Column(name = "bs")
    String bs;

    public CompanyDAO(Company company) {
        setName(company.getName());
        setCatchPhrase(company.getCatchPhrase());
        setBs(company.getBs());
    }

    public CompanyDAO() {

    }

    @Override
    public void doSave() {
        save();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }
}
