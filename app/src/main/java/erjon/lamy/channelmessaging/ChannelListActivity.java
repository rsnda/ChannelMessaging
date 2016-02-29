package erjon.lamy.channelmessaging;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ChannelListActivity extends AppCompatActivity implements OnWSEventListener {
    private ListView myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myList = (ListView)findViewById(R.id.listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        String accessToken = settings.getString("accessToken", "");

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("accesstoken", accessToken));
        WSRequest connectionRequest = new WSRequest("http://www.raphaelbischof.fr/messaging/?function=getchannels", nameValuePairs);
        connectionRequest.setOnWSEventListener(this);
        connectionRequest.execute();
    }

    @Override
    public void OnSuccess(String result) {
        Gson gson = new Gson();
        ChannelContener channels = gson.fromJson(result, ChannelContener.class);

        ChannelGson[] mesChannels = channels.getChannels();

        myList.setAdapter(new ChannelAdapter(mesChannels, this));
        for(ChannelGson mych : mesChannels)
        {
            Toast toast = Toast.makeText(getApplicationContext(), mych.getName(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void OnError() {

    }

}
