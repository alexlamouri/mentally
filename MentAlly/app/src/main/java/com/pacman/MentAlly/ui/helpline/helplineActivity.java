package com.pacman.MentAlly.ui.helpline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.pacman.MentAlly.R;
import com.pacman.MentAlly.ui.home.MainActivity;

import android.os.Bundle;

public class helplineActivity extends MainActivity {

    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.frag_container);
        getLayoutInflater().inflate(R.layout.activity_helpline, contentFrameLayout);

        findViewById(R.id.kidsHelpCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:1-866-925-5454");
            }
        });

        findViewById(R.id.kidsHelpVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("https://good2talk.ca/");
            }
        });



        findViewById(R.id.safeHavenCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:403-223-0483");
            }
        });

        findViewById(R.id.safeHavenVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("https://www.tabersafehaven.ca");
            }
        });



        findViewById(R.id.transLifelineCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:877-330-6366");
            }
        });

        findViewById(R.id.transLifelineVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("https://www.translifeline.org");
            }
        });



        findViewById(R.id.chimoHelpCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:1-800-667-5005");
            }
        });

        findViewById(R.id.chimoHelpVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("http://www.chimohelpline.ca/");
            }
        });



        findViewById(R.id.someOtherCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:780-743-4357");
            }
        });

        findViewById(R.id.someOtherVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("https://someothersolutions.ca/");
            }
        });



        findViewById(R.id.cmhaCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:1-888-811-2222");
            }
        });

        findViewById(R.id.cmhaVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("https://cmhapeeldufferin.ca");
            }
        });



        findViewById(R.id.crisisServicesCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:1-833-456-4566");
            }
        });

        findViewById(R.id.crisisServicesVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("https://www.crisisservicescanada.ca/");
            }
        });



        findViewById(R.id.hopeForWellnessCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:1-855-242-3310");
            }
        });

        findViewById(R.id.hopeForWellnessVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("https://www.hopeforwellness.ca");
            }
        });



        findViewById(R.id.nedicCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:1-866-633-4220");
            }
        });

        findViewById(R.id.nedicVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("https://nedic.ca");
            }
        });



        findViewById(R.id.drugRehabCall).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                call("tel:1-877-746-1963");
            }
        });

        findViewById(R.id.drugRehabVisit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                visit("http://www.canadadrugrehab.ca");
            }
        });
    }





    public void call(String phone) {
        if (ContextCompat.checkSelfPermission(helplineActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(helplineActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }

        else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(phone));
            startActivity(callIntent);
        }
    }

    public void visit(String website) {

        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setData(Uri.parse(website));
        startActivity(viewIntent);
    }
}
