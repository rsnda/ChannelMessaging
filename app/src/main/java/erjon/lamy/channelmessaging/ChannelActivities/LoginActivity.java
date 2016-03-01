package erjon.lamy.channelmessaging.ChannelActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import erjon.lamy.channelmessaging.Gson.ConnectGson;
import erjon.lamy.channelmessaging.OnWSEventListener;
import erjon.lamy.channelmessaging.R;
import erjon.lamy.channelmessaging.WSRequest;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnWSEventListener {
    private Button btnValider;
    private EditText txtIdentifiant;
    private EditText txtMotDePasse;
    private List<NameValuePair> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnValider = (Button) findViewById(R.id.btnValider);
        txtIdentifiant = (EditText) findViewById(R.id.editIdentifiant);
        txtMotDePasse = (EditText) findViewById(R.id.editMotDePasse);

        btnValider.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // NameValuePair nvp = new Name
        String identifiant = txtIdentifiant.getText().toString();
        String motDePasse = txtMotDePasse.getText().toString();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("username", identifiant));
        nameValuePairs.add(new BasicNameValuePair("password", motDePasse));
        WSRequest connectionRequest = new WSRequest("http://www.raphaelbischof.fr/messaging/?function=connect", nameValuePairs);
        connectionRequest.setOnWSEventListener(this);
        connectionRequest.execute();
    }

    @Override
    public void OnSuccess(String result) {
        Gson gson = new Gson();
        ConnectGson connection = gson.fromJson(result, ConnectGson.class);

        if(connection.getCode() == 200)
        {
            SharedPreferences settings = getSharedPreferences("PREFS", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("accessToken", connection.getAccesstoken());
            editor.commit();

            Intent intentChannelList = new Intent(getApplicationContext(),ChannelListActivity.class);
            startActivity(intentChannelList);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), connection.getResponse(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void OnError() {

    }
}
