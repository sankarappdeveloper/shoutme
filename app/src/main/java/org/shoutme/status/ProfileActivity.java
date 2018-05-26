package org.shoutme.status;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    FirebaseDatabase database;
    DatabaseReference mref,mref1;
    EditText name,mobile,code;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=(EditText)findViewById(R.id.name);
        mobile=(EditText)findViewById(R.id.mobileno);
        code=(EditText)findViewById(R.id.code);

        submit=(Button)findViewById(R.id.submit);
        Intent in=getIntent();
        String mob=in.getStringExtra("mobile");
        database = FirebaseDatabase.getInstance();
        mref = database.getReference("User").child(mob);
        mref1 = database.getReference("User");
        mobile.setText(mob);


submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mref.child("name").setValue(name.getText().toString());
        mref.child("mobile").setValue(mobile.getText().toString());
        mref.child("code").setValue(code.getText().toString());

        mref.child("earnings").child("money").setValue("5");
        mref.child("impressions").child("date").setValue(10);
        SharedPreferences mref = getApplicationContext().getSharedPreferences("Login", 0);
        SharedPreferences.Editor editor = mref.edit();
        editor.putString("refercode",code.getText().toString());
        editor.commit();
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
    }
});

    }
}
