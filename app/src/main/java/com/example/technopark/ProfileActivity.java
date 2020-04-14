package com.example.technopark;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

//        Copy links
        findViewById(R.id.phone_number).setOnLongClickListener(this);
        findViewById(R.id.email).setOnLongClickListener(this);
        findViewById(R.id.odnoklassniki).setOnLongClickListener(this);
        findViewById(R.id.github).setOnLongClickListener(this);
        findViewById(R.id.vkontakte).setOnLongClickListener(this);

//        Exit
        findViewById(R.id.exitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog exitDialog = new Dialog(ProfileActivity.this, R.style.ExitDialogAnimation);
                exitDialog.getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimation;
                exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(50, 0, 0, 0)));
                exitDialog.setContentView(R.layout.fragment_exit_popup);
                exitDialog.setCancelable(true);
                exitDialog.show();

                exitDialog.findViewById(R.id.exitButtonApprove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                      TODO: make log out
                        Toast.makeText(getApplicationContext(), "log out in progress", Toast.LENGTH_SHORT).show();
                    }
                });
                exitDialog.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
            }
        });

//        Show group members
        findViewById(R.id.group_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                Intent intent = new Intent(getApplicationContext(), GroupListActivity.class);
                intent.putExtra("GroupName", button.getText());
                ProfileActivity.this.startActivity(intent);
            }
        });

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
