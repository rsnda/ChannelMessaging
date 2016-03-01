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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

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
        Toast toast = Toast.makeText(getApplicationContext(),"High Success !", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void OnError() {
        Toast toast = Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG);
        toast.show();
    }
}
