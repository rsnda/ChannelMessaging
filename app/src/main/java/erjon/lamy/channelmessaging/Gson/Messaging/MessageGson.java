package erjon.lamy.channelmessaging.Gson.Messaging;

/**
 * Created by Erjon on 01/03/2016.
 */
public class MessageGson {
    private int userID;
    private String message;
    private String date;
    private String imageUrl;

    MessageGson() {}

    public int getUserId() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
