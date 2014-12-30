package gjacobs.com.websockettest;

import com.activeandroid.query.Select;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import gjacobs.com.websockettest.events.AndroidBus;
import gjacobs.com.websockettest.events.UserDBInit;
import gjacobs.com.websockettest.model.data.UsersDAO;

/**
 * Created by brettjacobs on 12/28/14.
 */
public class WebSocketTestApplication extends com.activeandroid.app.Application {
    AndroidBus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new AndroidBus();
        bus.register(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        bus.unregister(this);
    }

    public Bus getBus() {
        return bus;
    }

    @Produce
    public UserDBInit sendInitialUsersCount() {
        return new UserDBInit(new Select().from(UsersDAO.class).execute().size());
    }
}
