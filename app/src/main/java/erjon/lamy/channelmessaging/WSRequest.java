package erjon.lamy.channelmessaging;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erjon on 02/02/2016.
 */
public class WSRequest extends AsyncTask<String, Integer, String>{
    private String url;
    private List<NameValuePair> parametres;
    private ArrayList<OnWSEventListener> listeners = new ArrayList<>();
    private Exception myException = null;
    private int requestCode;

    public WSRequest(int requestCode, String url ,List<NameValuePair> parametres) {
        this.requestCode = requestCode;
        this.parametres = parametres;
        this.url = url;
    }

    public void setOnWSEventListener(OnWSEventListener listener){
        listeners.add(listener);
    }

    @Override
    protected String doInBackground(String... params) {
        String content = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        // Add your data
        try {
            httppost.setEntity(new UrlEncodedFormEntity(parametres));
        } catch (UnsupportedEncodingException e) {
            myException = e;
        //TODO Handler
        }
        // Execute HTTP Post Request
        HttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
            content = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            myException = e;
        //TODO Handler
        }

        return content;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);

        for (OnWSEventListener listener : listeners)
        {
            if (myException == null)
            {
                listener.OnSuccess(requestCode, s);
            }
            else
            {
                listener.OnError(requestCode);
            }

        }
    }
}
