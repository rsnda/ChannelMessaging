package erjon.lamy.channelmessaging;

/**
 * Created by Erjon on 08/02/2016.
 */
public class ChannelGson {
    private int channelId;
    private String name;
    private int connectedUsers;

    ChannelGson()
    {

    }

    public int getConnectedUsers() {
        return connectedUsers;
    }

    public int getChannelId() {
        return channelId;
    }

    public String getName() {
        return name;
    }
}
