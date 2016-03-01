package erjon.lamy.channelmessaging.ChannelActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import erjon.lamy.channelmessaging.Adapters.MessageAdapter;
import erjon.lamy.channelmessaging.Gson.Messaging.MessageContainer;
import erjon.lamy.channelmessaging.Gson.Messaging.MessageGson;
import erjon.lamy.channelmessaging.Gson.Messaging.MessageSendGson;
import erjon.lamy.channelmessaging.OnWSEventListener;
import erjon.lamy.channelmessaging.R;
import erjon.lamy.channelmessaging.WSRequest;

public class ChannelActivity extends AppCompatActivity implements View.OnClickListener, OnWSEventListener {
    private static final int REQUEST_SEND_MESSAGE = 1;
    private static final int REQUEST_GET_MESSAGES = 2;
    private Button sendBtn;
    private EditText messageToSend;
    private ListView myList;
    private String channelID;
    private String accessToken;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        channelID = intent.getStringExtra("id");

        sendBtn = (Button) findViewById(R.id.sendBtn);
        messageToSend = (EditText) findViewById(R.id.etMessage);

        sendBtn.setOnClickListener(this);
        myList = (ListView)findViewById(R.id.lvMessageList);

        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                accessToken = settings.getString("accessToken", "");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("accesstoken", accessToken));
                nameValuePairs.add(new BasicNameValuePair("channelid", channelID));
                final WSRequest connectionRequest = new WSRequest(REQUEST_GET_MESSAGES, "http://www.raphaelbischof.fr/messaging/?function=getmessages", nameValuePairs);
                connectionRequest.setOnWSEventListener(ChannelActivity.this);
                connectionRequest.execute();
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(r);

    }

    @Override
    public void OnSuccess(int requestCode, String result) {
        if(requestCode == REQUEST_GET_MESSAGES){
            Gson gson = new Gson();
            MessageContainer messages = gson.fromJson(result, MessageContainer.class);

            MessageGson[] myMessages = messages.getMessages();

            if (myMessages == null){
                Toast toast = Toast.makeText(getApplicationContext(), "Pas de message", Toast.LENGTH_LONG);
                toast.show();
            }else{
                myList.setAdapter(new MessageAdapter(myMessages, this));
            }
        }else{
            Gson gson = new Gson();
            MessageSendGson messSend = gson.fromJson(result, MessageSendGson.class);

            Toast toast = Toast.makeText(getApplicationContext(), messSend.getResponse(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void OnError(int requestCode) {
        Toast toast = Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        // NameValuePair nvp = new Name
        String mess = messageToSend.getText().toString();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("accesstoken", accessToken));
        nameValuePairs.add(new BasicNameValuePair("channelid", channelID));
        nameValuePairs.add(new BasicNameValuePair("message", mess));
        WSRequest connectionRequest = new WSRequest(REQUEST_SEND_MESSAGE, "http://www.raphaelbischof.fr/messaging/?function=sendmessage", nameValuePairs);
        connectionRequest.setOnWSEventListener(this);
        connectionRequest.execute();
        messageToSend.setText("");
    }
}
