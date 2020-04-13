package com.example.technopark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements View.OnLongClickListener {

    private ClipboardManager myClipboard;
    private ClipData myClip;
    private TextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        findViewById(R.id.phone_number).setOnLongClickListener(this);
        findViewById(R.id.email).setOnLongClickListener(this);
        findViewById(R.id.odnoklassniki).setOnLongClickListener(this);
        findViewById(R.id.github).setOnLongClickListener(this);
        findViewById(R.id.vkontakte).setOnLongClickListener(this);
    }


    @Override
    public boolean onLongClick(View v) {
        TextView textView = (TextView) v;
        String text = textView.getText().toString();

        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);

        Toast.makeText(getApplicationContext(), R.string.copied, Toast.LENGTH_SHORT).show();
        return true;
    }
}
