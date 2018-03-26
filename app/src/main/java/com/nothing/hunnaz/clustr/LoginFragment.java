package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 *
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    private EditText usernameTextEntry;
    private EditText passwordTextEntry;
    private TextView informationText;

    private static String newInfo = "";

    private static final String TAG = "LoginFragment";

    private DatabaseReference users;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

//        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
//            switchIntent(LoginActivity.class); // Change this to the landscape version
//        }

        // Firebase
        users = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        // Buttons
        v.findViewById(R.id.backButton).setOnClickListener(this);
        v.findViewById(R.id.doneButton).setOnClickListener(this);
        v.findViewById(R.id.registerButton).setOnClickListener(this);

        return v;
    }

    // Return an error message if there is one
    private void attemptLogin(String username, String password){
        getUserEmailFromUsername(username, password);
    }

    private void getUserEmailFromUsername(final String username, final String password) {
        users.orderByChild("username").equalTo(username).limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Found username
                        User user = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                        logIn(user.getEmail(), password);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Could not find "username"
                        newInfo = "Could not find user: " + username;
                    }
                });
    }

    private void logIn(String email, String password) {
        Log.d(TAG, "log in: " + email);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail: success");
                    switchIntent(HomeActivity.class);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "signInWithEmail: failure", task.getException());
                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    newInfo = "Login failed.";
                }
            }
        });
    }

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    @Override
    public void onPause() {
        Log.d(getClass().getSimpleName(), "Entering onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(getClass().getSimpleName(), "Entering onResume");
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        usernameTextEntry = (EditText) v.findViewById(R.id.userName);
        passwordTextEntry = (EditText) v.findViewById(R.id.password);
        informationText = (TextView) v.findViewById(R.id.loginInfo);

        switch (view.getId()) {
            case R.id.doneButton:
                attemptLogin(usernameTextEntry.getText().toString(), passwordTextEntry.getText().toString());
                if(newInfo.length() != 0) {
                    informationText.setText(newInfo);
                }
                break;
            case R.id.registerButton:
                switchIntent(RegisterActivity.class);
                break;
            case R.id.backButton:
                switchIntent(WelcomeActivity.class); //TODO: Change back button to be in the App Bar
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
