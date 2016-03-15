package erjon.lamy.channelmessaging.ChannelActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import erjon.lamy.channelmessaging.R;
import erjon.lamy.channelmessaging.fragments.ChannelListFragment;
import erjon.lamy.channelmessaging.fragments.MessageFragment;

public class ChannelListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MessageFragment messageFrag = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);

        if(messageFrag == null || !messageFrag.isInLayout()){ // The fragment is not visible / on screen
            Intent intentChannel = new Intent(getApplicationContext(),ChannelActivity.class);
            intentChannel.putExtra("id", ""+id);
            startActivity(intentChannel);
            return;
        }

        // From here the layout shows both fragments
        messageFrag.changeChannelId(""+id);
    }
}
