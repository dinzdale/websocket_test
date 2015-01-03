package gjacobs.com.websockettest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.squareup.otto.Subscribe;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import gjacobs.com.websockettest.events.UserDBInit;
import gjacobs.com.websockettest.model.data.UsersDAO;

/**
 * Created by brettjacobs on 12/28/14.
 */
public class UserFragment extends Fragment {

    Button prevUserButton;
    Button nextUserButton;
    TextView nameTextView, userNameTextView, emailTextView, addressTextView, phoneTextView, websiteTextView, noUsersTextView;

    // Address
    TextView streetTextView, suiteTextView, cityTextView, zipCodeTextView;

    ProgressBar progressOverlay;
    int userCount;
    int currentUser;
    List<Long> userIDs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup fragmentContainer = (ViewGroup) inflater.inflate(R.layout.user_layout, container, false);
        progressOverlay = (ProgressBar) fragmentContainer.findViewById(R.id.progressbar_overlay);
        prevUserButton = (Button) fragmentContainer.findViewById(R.id.prev_user_button);
        nextUserButton = (Button) fragmentContainer.findViewById(R.id.next_user_button);
        nameTextView = (TextView) fragmentContainer.findViewById(R.id.name_edittext);
        nameTextView.setOnFocusChangeListener(focusChangeListener);
        userNameTextView = (TextView) fragmentContainer.findViewById(R.id.username_edittext);
        userNameTextView.setOnFocusChangeListener(focusChangeListener);
        emailTextView = (TextView) fragmentContainer.findViewById(R.id.email_edittext);
        addressTextView = (TextView) fragmentContainer.findViewById(R.id.address_edittext);
        phoneTextView = (TextView) fragmentContainer.findViewById(R.id.phone_edittext);
        websiteTextView = (TextView) fragmentContainer.findViewById(R.id.website_edittext);
        noUsersTextView = (TextView) fragmentContainer.findViewById(R.id.number_users);
        streetTextView = (TextView) fragmentContainer.findViewById(R.id.street_edittext);
        streetTextView.setOnFocusChangeListener(focusChangeListener);
        suiteTextView = (TextView) fragmentContainer.findViewById(R.id.suite_edittext);
        cityTextView = (TextView) fragmentContainer.findViewById(R.id.city_edittext);
        zipCodeTextView = (TextView) fragmentContainer.findViewById(R.id.zipcode_edittext);
        setUpCallbacks();
        return fragmentContainer;
    }

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (!hasFocus) {
                List<UsersDAO> usersDAOs = new Select().from(UsersDAO.class).where("id=?", userIDs.get(currentUser)).execute();
                UsersDAO usersDAO = usersDAOs.get(0);
                String newValue = ((TextView) v).getText().toString();
                switch (v.getId()) {
                    case R.id.name_edittext:
                        if (newValue.compareTo(usersDAO.getName()) != 0) {
                            usersDAO.setName(newValue);
                            usersDAO.doSave();
                        }
                        break;
                    case R.id.username_edittext:
                        if (newValue.compareTo(usersDAO.getUsername()) != 0) {
                            usersDAO.setUsername(newValue);
                            usersDAO.doSave();
                        }
                        break;
                    case R.id.street_edittext:
                        if (newValue.compareTo(usersDAO.getAddress().getStreet())!=0) {
                            usersDAO.getAddress().setStreet(newValue);
                            usersDAO.doSave();
                        }
                        break;
                }
            }
        }
    };

    private void updateUI() {
        List<UsersDAO> usersDAOs = new Select().from(UsersDAO.class).where("id=?", userIDs.get(currentUser)).execute();
        if (!usersDAOs.isEmpty()) {
            progressOverlay.setVisibility(View.GONE);
            UsersDAO userDAO = usersDAOs.get(0);
            nameTextView.setText(userDAO.getName());
            userNameTextView.setText(userDAO.getUsername());
            emailTextView.setText(userDAO.getEmail());
            addressTextView.setText(userDAO.getAddress().getStreet());
            phoneTextView.setText(userDAO.getPhone());
            websiteTextView.setText(userDAO.getWebsite());
            streetTextView.setText(userDAO.getAddress().getStreet());
            suiteTextView.setText(userDAO.getAddress().getSuite());
            cityTextView.setText(userDAO.getAddress().getCity());
            zipCodeTextView.setText(userDAO.getAddress().getZipcode());

            noUsersTextView.setText(getString(R.string.user_current_number, currentUser, userCount));
        }
    }


    private void setUpCallbacks() {
        prevUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser > 0) {
                    currentUser--;
                    updateUI();
                }
            }
        });
        nextUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser < userCount - 1) {
                    currentUser++;
                    updateUI();
                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        ((WebSocketTestApplication) getActivity().getApplication()).getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((WebSocketTestApplication) getActivity().getApplication()).getBus().unregister(this);
    }

    @Subscribe
    public void onUserDBInit(UserDBInit event) {
        userCount = event.getNoUsers();
        if (userCount > 0) {
            currentUser = 0;
            // set IDs
            List<UsersDAO> usersDAOs = new Select("id").from(UsersDAO.class).execute();
            userIDs = new ArrayList<>(usersDAOs.size());
            for (UsersDAO nxtUserDAO : usersDAOs) {
                userIDs.add(nxtUserDAO.getId());
            }
            updateUI();
        }
    }


}
