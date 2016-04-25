package com.mattlab.gym.fityes_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Reg_Second extends AppCompatActivity {

    TextView TermsLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsecond);

        TermsLink = (TextView) findViewById(R.id.TermsLink);

        TermsLink.setText(Html.fromHtml("<a href=\"http://www.google.com\">A felhasználói feltételeket megértettem és elfogadom! A szövegre kattintva a feltételek böngészőben jelennek meg!</a>"));
        TermsLink.setAutoLinkMask(Linkify.WEB_URLS);
        TermsLink.setLinksClickable(true);
        TermsLink.setMovementMethod(LinkMovementMethod.getInstance());

        Button btn = (Button) findViewById(R.id.btn_regsave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_regsave(v);
            }
        });
    }

    public void btn_regsave(View v) {
        Intent myIntent = new Intent(Reg_Second.this, MenuActivity.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        Reg_Second.this.startActivity(myIntent);
    }
}
