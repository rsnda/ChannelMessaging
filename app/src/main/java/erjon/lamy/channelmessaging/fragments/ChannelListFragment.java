package erjon.lamy.channelmessaging.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import erjon.lamy.channelmessaging.Adapters.ChannelAdapter;
import erjon.lamy.channelmessaging.ChannelActivities.ChannelListActivity;
import erjon.lamy.channelmessaging.Gson.ChannelList.ChannelContener;
import erjon.lamy.channelmessaging.Gson.ChannelList.ChannelGson;
import erjon.lamy.channelmessaging.OnWSEventListener;
import erjon.lamy.channelmessaging.R;
import erjon.lamy.channelmessaging.WSRequest;

/**
 * Created by Erjon on 07/03/2016.
 */
public class ChannelListFragment extends Fragment implements OnWSEventListener {
    private ListView myList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_channel_list,container);
        myList = (ListView)v.findViewById(R.id.listView);

        myList.setOnItemClickListener((ChannelListActivity)getActivity());
        SharedPreferences settings = getActivity().getSharedPreferences("PREFS", 0);
        String accessToken = settings.getString("accessToken", "");

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("accesstoken", accessToken));
        WSRequest connectionRequest = new WSRequest(0, "http://www.raphaelbischof.fr/messaging/?function=getchannels", nameValuePairs);
        connectionRequest.setOnWSEventListener(this);
        connectionRequest.execute();
        return v;
    }

    @Override
    public void OnSuccess(int requestCode, String result) {
        Gson gson = new Gson();
        ChannelContener channels = gson.fromJson(result, ChannelContener.class);

        ChannelGson[] mesChannels = channels.getChannels();

        myList.setAdapter(new ChannelAdapter(mesChannels, getActivity()));

    }

    @Override
    public void OnError(int requestCode) {

    }
}
