package erjon.lamy.channelmessaging.ChannelActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import erjon.lamy.channelmessaging.Adapters.ChannelAdapter;
import erjon.lamy.channelmessaging.Gson.ChannelList.ChannelContener;
import erjon.lamy.channelmessaging.Gson.ChannelList.ChannelGson;
import erjon.lamy.channelmessaging.OnWSEventListener;
import erjon.lamy.channelmessaging.R;
import erjon.lamy.channelmessaging.WSRequest;

public class ChannelListActivity extends AppCompatActivity implements OnWSEventListener, AdapterView.OnItemClickListener {
    private ListView myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myList = (ListView)findViewById(R.id.listView);
        myList.setOnItemClickListener(this);
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        String accessToken = settings.getString("accessToken", "");

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("accesstoken", accessToken));
        WSRequest connectionRequest = new WSRequest(0, "http://www.raphaelbischof.fr/messaging/?function=getchannels", nameValuePairs);
        connectionRequest.setOnWSEventListener(this);
        connectionRequest.execute();
    }

    @Override
    public void OnSuccess(int requestCode, String result) {
        Gson gson = new Gson();
        ChannelContener channels = gson.fromJson(result, ChannelContener.class);

        ChannelGson[] mesChannels = channels.getChannels();

        myList.setAdapter(new ChannelAdapter(mesChannels, this));

    }

    @Override
    public void OnError(int requestCode) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentChannel = new Intent(getApplicationContext(),ChannelActivity.class);
        intentChannel.putExtra("id", ""+id);
        startActivity(intentChannel);
    }
}
