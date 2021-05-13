package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoadActivity extends AppCompatActivity {

    private EditText mLoad;
    private EditText mPassword;
    private Button mStudentButton;
    private Button mMangerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoad = (EditText)findViewById(R.id.load_text);
        mPassword = (EditText)findViewById(R.id.password_text);
        mStudentButton = (Button)findViewById(R.id.student_load_button) ;
        mMangerButton = (Button)findViewById(R.id.manager_load_button);


        mMangerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = mPassword.getText().toString();
                final String true_password = "8888"; // 管理员密码
                if (password.equals(true_password))
                {
                    Intent i = new Intent(LoadActivity.this, CourseEntryActivity.class);
                    startActivity(i);
                }
                else { Toast.makeText(LoadActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show(); }
            }
        });

        mStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = mPassword.getText().toString();
                final String true_password = "6666";//学生默认密码
                if(password.equals(true_password)){
                    Intent i = new Intent(LoadActivity.this, AllEventActivity.class);
                    startActivity(i);
                }
                else { Toast.makeText(LoadActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show(); }
            }
        });
    }
}
