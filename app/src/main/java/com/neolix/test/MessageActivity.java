package com.neolix.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MessageActivity extends Activity {

    private Button msgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.message_view);

        msgBack = (Button) findViewById(R.id.msg_back);

        msgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
