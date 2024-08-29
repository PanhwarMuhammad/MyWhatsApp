package com.muhammad.mywhatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.muhammad.mywhatsapp.databinding.ActivitySignInBinding;

public class SignIn extends AppCompatActivity {
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    ActivitySignInBinding binding;
//    GoogleSignInClient googleSignInClient;
//    BeginSignInRequest signInRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());

        { // Google signIn code that has been deprecated
//            signInRequest = BeginSignInRequest.builder()
//                    .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                            .setSupported(true)
//                            // Your server's client ID, not your Android client ID.
//                            .setServerClientId(getString(R.string.default_web_client_id))
//                            // Only show accounts previously used to sign in.
//                            .setFilterByAuthorizedAccounts(true)
//                            .build()).build();
//
//            googleSignInClient = GoogleSignIn.getClient(SignIn.this, gso);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkgreen)));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.lightgreen));
        }

//        progressDialog = new ProgressDialog(SignIn.this);
//        progressDialog.setTitle("Creating Account");
//        progressDialog.setMessage("Account is being created...");
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // progressDialog.show();

                auth.signInWithEmailAndPassword(binding.email.getText().toString(),
                                binding.password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        binding.navSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });


//       if(auth.getCurrentUser() != null) {
//            Intent intent = new Intent(SignIn.this, MainActivity.class);
//            startActivity(intent);
//        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        invalidateOptionsMenu();

        { // Deprecated code of Google authentication
//    public class YourActivity extends AppCompatActivity {
//
//        // ...
//        private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
//        private boolean showOneTapUI = true;
//        // ...
//
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            switch (requestCode) {
//                case REQ_ONE_TAP:
//                    try {
//                        SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
//                        String idToken = credential.getGoogleIdToken();
//                        if (idToken !=  null) {
//                            // Got an ID token from Google. Use it to authenticate
//                            // with Firebase.
//                            Log.d("TAG", "Got ID token.");
//                        }
//                    } catch (ApiException e) {
//                        // ...
//                    }
//                    break;
//            }
//        }
//    }
        }


    }
}