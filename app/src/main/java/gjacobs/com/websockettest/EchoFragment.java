package gjacobs.com.websockettest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class EchoFragment extends Fragment {
    TextView messageBox;
    TextView sendMessageTextView;
    Button sendMessageButton;
    final WebSocketConnection webSocketConnection = new WebSocketConnection();

    public EchoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentContainer = inflater.inflate(R.layout.fragment_echo, container, false);
        messageBox = (TextView) fragmentContainer.findViewById(R.id.message_box);
        sendMessageTextView = (TextView) fragmentContainer.findViewById(R.id.next_message);
        sendMessageButton = (Button) fragmentContainer.findViewById(R.id.send_message_button);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = sendMessageTextView.getText().toString();
                webSocketConnection.sendTextMessage(message);
                sendMessageTextView.setText("");
            }
        });
        messageBox.append("Welcome!!!\n");

        return fragmentContainer;
    }

    @Override
    public void onResume() {
        super.onResume();


        final String wsuri = "ws://echo.websocket.org";
        try {
            webSocketConnection.connect(wsuri, new WebSocketHandler() {
                @Override
                public void onOpen() {
                    super.onOpen();
                    messageBox.append("WebSocket opened\n");
                }

                @Override
                public void onClose(int code, String reason) {
                    super.onClose(code, reason);
                    messageBox.setText("WebSocket closed: code: " + code + " reason: " + reason + "\n");
                }

                @Override
                public void onTextMessage(String payload) {
                    super.onTextMessage(payload);
                    messageBox.setText(payload);
                }
            });
        } catch (WebSocketException wse) {
            messageBox.setText(wse.getMessage() + "\n");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        webSocketConnection.disconnect();
    }
}
