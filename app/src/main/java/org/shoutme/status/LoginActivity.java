package org.shoutme.status;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="PhoneLogin";
    private boolean mVerificationInProgress=false;
    private String mVerificationId;
    TextView donorid_text;
    EditText phone;
    Button login_btn,fb,google;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mref;

    String phone1;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance();
        mref = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();
        login_btn = (Button) findViewById(R.id.BTN_login);
        phone = (EditText) findViewById(R.id.phone);
//        SharedPreferences mref=getApplicationContext().getSharedPreferences("Login",0);
//        SharedPreferences.Editor editor=mref.edit();
//        String sa=mref.getString("number", null);
//        if(sa.equals(phone1))
//        {
//            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
//        }
//        else {
        login_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phone.getText().toString(), 60, TimeUnit.SECONDS, LoginActivity.this, mCallbacks);

            }
        });
//        }
        mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onVerificationCompleted (PhoneAuthCredential credential){
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                mVerificationInProgress = true;
                String status="true";
                SharedPreferences mref = getApplicationContext().getSharedPreferences("Login", 0);
                SharedPreferences.Editor editor = mref.edit();
                editor.putString("number",status);
                editor.commit();
                Log.d(TAG, "onVerificationCompleted:" + credential);
                Toast.makeText(getApplicationContext(), "Verify success", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(credential);
            }
            @Override
            public void onVerificationFailed (FirebaseException e){
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(getApplicationContext(), "Verify failed", Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
                // Show a message and update the UI
                // ...
            }
            @Override
            public void onCodeSent (String verificationId,
                                    PhoneAuthProvider.ForceResendingToken token){
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(getApplicationContext(), "code sended", Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
//                mResendToken = token;

                // ...
            }
        };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success"+task.getException());

                            String mobile = phone.getText().toString();
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.putExtra("mobile", mobile);
                            startActivity(intent);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });

    }
}
