package erjon.lamy.channelmessaging.Gson.Connect;

/**
 * Created by Erjon on 08/02/2016.
 */
public class ConnectGson {
    private String response;
    private int code;
    private String accesstoken;

    ConnectGson(){

    }

    public String getResponse() {
        return response;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public int getCode() {
        return code;
    }
}
