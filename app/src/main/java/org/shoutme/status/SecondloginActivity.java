package org.shoutme.status;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;


public class SecondloginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondlogin);

        final EditText username=(EditText)findViewById(R.id.username);
        final EditText password=(EditText)findViewById(R.id.password);
        Button submit=(Button)findViewById(R.id.login);
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (Objects.equals(username.getText().toString(), "admin") && Objects.equals(password.getText().toString(), "admin123")) {
                   startActivity(new Intent(SecondloginActivity.this, AddpostActivity.class));
               } else if (Objects.equals(username.getText().toString(), "sankar") && Objects.equals(password.getText().toString(), "sankar123")) {
                   startActivity(new Intent(SecondloginActivity.this, AddpostActivity.class));
               }

           }
       });



    }
}
