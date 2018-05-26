package org.shoutme.status;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddpostActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference mref;
    StorageReference ds;
    Uri uri;
    int Pick = 1;
    private ProgressDialog progressDialog;
    TextView tx;
    RecyclerView recyclerview;
    ImageView post_image;
    Button choose;
    String[] catergort_list={"Comedy","Committed","Single","Lovefailure","DP","Lovefeel","Romantic","Funny","Friendship"};
    String category_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);
        final EditText post_text = (EditText)findViewById(R.id.text);
        Button submit = (Button) findViewById(R.id.post);
        choose = (Button) findViewById(R.id.choose);
        post_image=(ImageView)findViewById(R.id.imageView);
        Spinner category=(Spinner)findViewById(R.id.category);

        ArrayAdapter<String>array_category=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,catergort_list);
        category.setAdapter(array_category);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 category_string=parent.getSelectedItem().toString();

                Toast.makeText(getApplicationContext(),category_string,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SharedPreferences mrefs = getApplicationContext().getSharedPreferences("Login", 0);
        SharedPreferences.Editor editor = mrefs.edit();
        final String san= mrefs.getString("name",null);
        editor.commit();
        database = FirebaseDatabase.getInstance();
        mref = database.getReference("Posts").push();
        ds = FirebaseStorage.getInstance().getReference("images");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(AddpostActivity.this);
                progressDialog.setMessage("uploading");
                progressDialog.show();
                progressDialog.setCancelable(false);
                StorageReference filepath = ds.child("imag").child(uri.getLastPathSegment());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadurl = taskSnapshot.getDownloadUrl();
                        mref.child("name").setValue("sankar");
                        mref.child("message").setValue(post_text.getText().toString());
                        mref.child("image").setValue(downloadurl.toString());
                        mref.child("category").setValue(category_string);
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"upload success",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddpostActivity.this,MainActivity.class));
                    }
                });

            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ga = new Intent(Intent.ACTION_GET_CONTENT);
                ga.setType("image/*");
                startActivityForResult(ga, 1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            uri = data.getData();
            choose.setVisibility(View.GONE);
            post_image.setVisibility(View.VISIBLE);
            post_image.setImageURI(uri);
        }
        else
        {
            
        }

    }
}
