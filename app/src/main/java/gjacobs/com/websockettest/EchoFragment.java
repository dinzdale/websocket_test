package gjacobs.com.websockettest;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


/**
 * A simple {@link Fragment} subclass.
 */
public class EchoFragment extends Fragment {
    TextView messageBox;
    TextView sendMessageTextView;
    Button sendMessageButton;
    //final WebSocketConnection webSocketConnection = new WebSocketConnection();
    OkHttpClient okHttpClient;
    WebSocket webSocket;
    private Activity myActivity;
    final String wsuri = "ws://echo.websocket.org";

    public EchoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentContainer = inflater.inflate(R.layout.fragment_echo, container, false);
        return fragmentContainer;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageBox = (TextView) view.findViewById(R.id.message_box);
        sendMessageTextView = (TextView) view.findViewById(R.id.next_message);
        sendMessageButton = (Button) view.findViewById(R.id.send_message_button);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = sendMessageTextView.getText().toString();
                webSocket.send(message);
            }
        });

        messageBox.append("Welcome!!!\n");
    }

    @Override
    public void onResume() {
        super.onResume();

        //
        webSocket = new OkHttpClient().newWebSocket(new Request.Builder().url(wsuri).build(), new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                updateMessageBox("WebSocket opened");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                updateMessageBox(text);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                updateMessageBox("Closing Websocket: " + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                updateMessageBox("WebSocket closed: code: " + code + " reason: " + reason + "\n");

            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                updateMessageBox(t.getMessage());
            }

        });

    }

    @Override
    public void onPause() {
        super.onPause();
        webSocket.close(100,"onPause called");
    }

    private void updateMessageBox(final String message) {
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageBox.append(message + "\n");
            }
        });

    }
}