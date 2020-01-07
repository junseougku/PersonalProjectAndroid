package com.example.teachermanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button attendCheckButton = (Button)findViewById(R.id.AttendCheckButton);
        attendCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AttendCheckActivity.class);
                startActivity(intent);
            }
        });

        Button resisterStudentButton = (Button)findViewById(R.id.ResisterStudentButton);
        resisterStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ResisterStudentActivity.class);
                startActivity(intent);

            }
        });
    }


}
