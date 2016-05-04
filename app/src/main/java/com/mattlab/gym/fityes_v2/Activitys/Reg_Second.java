package com.mattlab.gym.fityes_v2.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Reg_Second extends AppCompatActivity {

    TextView TermsLink;

    String name;
    String mail;
    String date;
    String password;
    EditText editPassword;
    EditText editPassSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsecond);
        //Buttons
        Button btn = (Button) findViewById(R.id.btn_regsave);
        //EditText
        editPassword = (EditText) findViewById(R.id.password);
        editPassSecond = (EditText) findViewById(R.id.secondPassword);
        //Bundle
        Bundle extras = getIntent().getExtras();
        //ExtraString
        name = extras.getString("name");
        mail = extras.getString("mail");
        date = extras.getString("date");
        //String
        password = editPassword.getText().toString();
        //Termslink
        TermsLink = (TextView) findViewById(R.id.TermsLink);

        TermsLink.setText(Html.fromHtml("<a href=\"http://www.google.com\">A felhasználói feltételeket megértettem és elfogadom! A szövegre kattintva a feltételek böngészőben jelennek meg!</a>"));
        TermsLink.setAutoLinkMask(Linkify.WEB_URLS);
        TermsLink.setLinksClickable(true);
        TermsLink.setMovementMethod(LinkMovementMethod.getInstance());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("Jelszó", editPassword.getText().toString());
                password = editPassword.getText().toString();
                new PostAsync().execute(name, password);
            }
        });
    }

    class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;


        private static final String LOGIN_URL = "http://www.ext.hu/api/index.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {


            pDialog = new ProgressDialog(Reg_Second.this);
            pDialog.setMessage("Regisztráció...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {


                HashMap<String, String> params = new HashMap<>();
                params.put("action", "reg");
                params.put("name", args[0]);
                params.put("password", args[1]);
                params.put("mail", mail);
                params.put("date", date);

                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject json) {


            int success = 0;
            String message = "";

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
                /*Toast.makeText(Initialize.this, json.toString(),
                        Toast.LENGTH_LONG).show();*/

                try {
                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (success == 1) {
                pDialog = new ProgressDialog(Reg_Second.this);
                pDialog.setMessage("Sikeres regisztráció...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                Log.d("Sikeres!", message);

                Intent myIntent = new Intent(Reg_Second.this, MenuActivity.class);
                myIntent.putExtra("key", "2"); //Optional parameters
                Reg_Second.this.startActivity(myIntent);
                finish();

            } else {

                Toast.makeText(Reg_Second.this, "Hibás e-mail vagy jelszó páros",
                        Toast.LENGTH_LONG).show();
                Log.d("Sikertelen", message);

            }
        }

    }
    public void btn_regsave(View v) {
        Intent myIntent = new Intent(Reg_Second.this, MenuActivity.class);
        Reg_Second.this.startActivity(myIntent);
    }
}
