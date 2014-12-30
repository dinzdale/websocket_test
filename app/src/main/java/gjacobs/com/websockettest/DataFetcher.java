package gjacobs.com.websockettest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Looper;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.TableInfo;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.logging.Handler;

import gjacobs.com.websockettest.events.UserDBInit;
import gjacobs.com.websockettest.model.data.AddressDAO;
import gjacobs.com.websockettest.model.data.CompanyDAO;
import gjacobs.com.websockettest.model.data.Photos;
import gjacobs.com.websockettest.model.data.PhotosDAO;
import gjacobs.com.websockettest.model.data.Post;
import gjacobs.com.websockettest.model.data.PostDAO;
import gjacobs.com.websockettest.model.data.TestDataService;
import gjacobs.com.websockettest.model.data.Users;
import gjacobs.com.websockettest.model.data.UsersDAO;
import retrofit.RestAdapter;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DataFetcher extends IntentService {
    Logger LOG = LoggerFactory.getLogger(DataFetcher.class);

    // URLS
    String TESTDATA_URL = "http://jsonplaceholder.typicode.com";

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FETCH_DATA = "FETCHDATA";

    // TODO: Rename parameters
    // private static final String EXTRA_PARAM1 = "gjacobs.com.websockettest.extra.PARAM1";
    //private static final String EXTRA_PARAM2 = "gjacobs.com.websockettest.extra.PARAM2";

    RestAdapter restAdapter;
    TestDataService testDataService;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFetchData(Context context) {
        Intent intent = new Intent(context, DataFetcher.class);
        intent.setAction(ACTION_FETCH_DATA);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, DataFetcher.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }
    public DataFetcher() {
        super("DataFetcher");
        restAdapter = new RestAdapter.Builder().setEndpoint(TESTDATA_URL).build();
        testDataService = restAdapter.create(TestDataService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_DATA.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFetchData();
            }
        }
    }


    private void handleActionFetchData() {

        List<Post> postList = testDataService.listPosts();
        new Delete().from(PostDAO.class);
        for (Post nxtPost : postList) {
            PostDAO postDAO = new PostDAO().build(nxtPost);
            postDAO.save();
        }

        List<Users> usersList = testDataService.listUsers();


        new Delete().from(UsersDAO.class).execute();
        for (Users nxtUser : usersList) {
            UsersDAO usersDAO = new UsersDAO(nxtUser);
            usersDAO.doSave();
        }
        // send number of users saved to database
        int noUsers = new Select().from(UsersDAO.class).execute().size();
        ((WebSocketTestApplication) getApplication()).getBus().post(new UserDBInit(noUsers));

        new Delete().from(PhotosDAO.class).execute();
        List<Photos> photosList = testDataService.listPhotos();
        for (Photos nxtPhotos : photosList) {
            PhotosDAO postDAO = new PhotosDAO(nxtPhotos);
            postDAO.save();
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */

    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ((WebSocketTestApplication) getApplication()).getBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((WebSocketTestApplication) getApplication()).getBus().unregister(this);
    }
}
