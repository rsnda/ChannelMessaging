package erjon.lamy.channelmessaging.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import erjon.lamy.channelmessaging.ChannelActivities.ChannelListActivity;
import erjon.lamy.channelmessaging.Gson.Messaging.MessageContainer;
import erjon.lamy.channelmessaging.Gson.Messaging.MessageGson;
import erjon.lamy.channelmessaging.Gson.Messaging.MessageSendGson;
import erjon.lamy.channelmessaging.OnWSEventListener;
import erjon.lamy.channelmessaging.R;
import erjon.lamy.channelmessaging.WSRequest;

/**
 * Created by Erjon on 07/03/2016.
 */
public class MessageFragment extends Fragment implements View.OnClickListener, OnWSEventListener {
    private static final int REQUEST_SEND_MESSAGE = 1;
    private static final int REQUEST_GET_MESSAGES = 2;
    private Button sendBtn;
    private EditText messageToSend;
    private String channelID;
    private String accessToken;
    private Handler handler;
    private ListView myList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_channel,container);
        myList = (ListView)v.findViewById(R.id.listView);

        Intent intent = getActivity().getIntent();
        channelID = intent.getStringExtra("id");

        sendBtn = (Button) v.findViewById(R.id.sendBtn);
        messageToSend = (EditText) v.findViewById(R.id.etMessage);

        sendBtn.setOnClickListener(this);
        myList = (ListView) v.findViewById(R.id.lvMessageList);

        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                SharedPreferences settings = getActivity().getSharedPreferences("PREFS", 0);
                accessToken = settings.getString("accessToken", "");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("accesstoken", accessToken));
                nameValuePairs.add(new BasicNameValuePair("channelid", channelID));
                final WSRequest connectionRequest = new WSRequest(REQUEST_GET_MESSAGES, "http://www.raphaelbischof.fr/messaging/?function=getmessages", nameValuePairs);
                connectionRequest.setOnWSEventListener(MessageFragment.this);
                connectionRequest.execute();
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(r);

        return v;
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

    @Override
    public void OnSuccess(int requestCode, String result) {
        if(requestCode == REQUEST_GET_MESSAGES){
            Gson gson = new Gson();
            MessageContainer messages = gson.fromJson(result, MessageContainer.class);

            MessageGson[] myMessages = messages.getMessages();

            if (myMessages == null){
                Toast toast = Toast.makeText(getActivity(), "Pas de message", Toast.LENGTH_LONG);
                toast.show();
            }else{
                myList.setAdapter(new MessageAdapter(myMessages, getActivity()));
            }
        }else{
            Gson gson = new Gson();
            MessageSendGson messSend = gson.fromJson(result, MessageSendGson.class);

            Toast toast = Toast.makeText(getActivity(), messSend.getResponse(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void OnError(int requestCode) {
        Toast toast = Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_LONG);
        toast.show();
    }
}
