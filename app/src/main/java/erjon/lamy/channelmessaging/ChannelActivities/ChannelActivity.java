package erjon.lamy.channelmessaging.ChannelActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import erjon.lamy.channelmessaging.Gson.Messaging.MessageContainer;
import erjon.lamy.channelmessaging.Gson.Messaging.MessageGson;
import erjon.lamy.channelmessaging.OnWSEventListener;
import erjon.lamy.channelmessaging.R;
import erjon.lamy.channelmessaging.WSRequest;

public class ChannelActivity extends AppCompatActivity implements OnWSEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String channelId = intent.getStringExtra("id");

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        String accessToken = settings.getString("accessToken", "");

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("accesstoken", accessToken));
        nameValuePairs.add(new BasicNameValuePair("channelid", channelId));
        WSRequest connectionRequest = new WSRequest("http://www.raphaelbischof.fr/messaging/?function=getmessages", nameValuePairs);
        connectionRequest.setOnWSEventListener(this);
        connectionRequest.execute();
    }

    @Override
    public void OnSuccess(String result) {
        Gson gson = new Gson();
        MessageContainer messages = gson.fromJson(result, MessageContainer.class);

        MessageGson[] myMessages = messages.getMessages();

        if (myMessages == null){
            Toast toast = Toast.makeText(getApplicationContext(), "Pas de message", Toast.LENGTH_LONG);
            toast.show();
        }else{
            for(MessageGson myMess : myMessages)
            {
                Toast toast2 = Toast.makeText(getApplicationContext(),myMess.getMessage(), Toast.LENGTH_LONG);
                toast2.show();
            }
        }

    }

    @Override
    public void OnError() {
        Toast toast = Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG);
        toast.show();
    }
}
