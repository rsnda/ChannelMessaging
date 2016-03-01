package erjon.lamy.channelmessaging.Gson.Messaging;

/**
 * Created by Erjon on 01/03/2016.
 */
public class MessageGson {
    private int userID;
    private String message;
    private String date;
    private String imageUrl;
    private String username;
    private String latitude;
    private String longitude;
    private String messageImageUrl;
    private String soundUrl;

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

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getMessageImageUrl() {
        return messageImageUrl;
    }

    public String getSoundUrl() {
        return soundUrl;
    }
}
