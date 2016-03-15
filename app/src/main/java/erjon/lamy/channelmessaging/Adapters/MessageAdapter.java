package erjon.lamy.channelmessaging.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import erjon.lamy.channelmessaging.CircleTransform;
import erjon.lamy.channelmessaging.Gson.Messaging.MessageGson;
import erjon.lamy.channelmessaging.R;

/**
 * Created by Erjon on 01/03/2016.
 */
public class MessageAdapter extends BaseAdapter {
    MessageGson[] messages;
    Context context;

    public MessageAdapter(MessageGson[] mess, Context con)
    {
        messages = mess;
        context = con;
    }

    @Override
    public int getCount() {
        return messages.length;
    }

    @Override
    public MessageGson getItem(int position) {
        return messages[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.message_layout, parent, false);

        TextView tvUserMessage = (TextView) rowView.findViewById(R.id.tvUserMessage);
        TextView tvDate = (TextView) rowView.findViewById(R.id.tvDate);
        ImageView ivProfilePic = (ImageView) rowView.findViewById(R.id.ivProfilePic);

        MessageGson myMess = getItem(position);
        tvUserMessage.setText(myMess.getMessage());
        tvDate.setText(myMess.getUsername());

        Picasso.with(context).load(myMess.getImageUrl()).transform(new CircleTransform()).into(ivProfilePic);

        return rowView;
    }
}
