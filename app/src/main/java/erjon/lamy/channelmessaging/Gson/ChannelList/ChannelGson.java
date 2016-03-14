package erjon.lamy.channelmessaging.Gson.ChannelList;

/**
 * Created by Erjon on 08/02/2016.
 */
public class ChannelGson {
    private int channelID;
    private String name;
    private int connectedusers;

    ChannelGson(){}

    public int getConnectedUsers() {
        return connectedusers;
    }

    public int getChannelId() {
        return channelID;
    }

    public String getName() {
        return name;
    }
}
