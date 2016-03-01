package erjon.lamy.channelmessaging.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import erjon.lamy.channelmessaging.Gson.ChannelList.ChannelGson;
import erjon.lamy.channelmessaging.R;

/**
 * Created by Erjon on 08/02/2016.
 */
public class ChannelAdapter extends BaseAdapter {

    private ChannelGson[] channels;
    private Context context;

    public ChannelAdapter(ChannelGson[] myChannels, Context myContext)
    {
        channels = myChannels;
        context = myContext;
    }

    @Override
    public int getCount()
    {
        return channels.length;
    }

    @Override
    public ChannelGson getItem(int position)
    {
        return channels[position];
    }

    @Override
    public long getItemId(int position)
    {
        return getItem(position).getChannelId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView tvChannelName = (TextView) rowView.findViewById(R.id.tvChannelName);
        TextView tvConnectedUsers = (TextView) rowView.findViewById(R.id.tvConnectedUsers);

        ChannelGson myCh = getItem(position);
        tvChannelName.setText(myCh.getName());
        tvConnectedUsers.setText("Nombre d'utilisateurs connectés : "+myCh.getConnectedUsers());

        return rowView;
    }
}
