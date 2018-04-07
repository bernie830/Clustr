package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener {
    private EditText emailTextEntry;
    private EditText passwordTextEntry;
    private EditText newPassTextEntry;
    private EditText confirmPassTextEntry;
    private TextView loginInfoMessage;
    private String TAG = "Change password";

    // TODO - Does nothing right now
    private void saveNewPassword(String email, final String oldPass, final String newPass){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, oldPass);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Password updated");
                                    } else {
                                        Log.d(TAG, "Error password not updated");
                                        loginInfoMessage.setText("Attempted change failed. Please check your username and old password");
                                    }
                                    switchIntent(AccountActivity.class);
                                }
                            });
                        } else {
                            Log.d(TAG, "Error auth failed");
                            loginInfoMessage.setText("Attempted change failed. Please check your username and old password");
                        }
                    }
                });
    }

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doneButton:
                String email = emailTextEntry.getText().toString();
                String oldPass = passwordTextEntry.getText().toString();
                String newPass = newPassTextEntry.getText().toString();
                String confirmPass = confirmPassTextEntry.getText().toString();
                String error = "";
                if(!isNetworkAvailable()){
                    error = "No network connection found. Internet connection is needed to change passwords";
                }
                if(error.length() == 0 && !newPass.equals(confirmPass)){
                    error = "The entered passwords do not match";
                } else if (error.length() == 0 && newPass.length() < 8) {
                    error = "The new passwords must be at least 8 characters in length";
                }

                if(error.length() != 0){
                    loginInfoMessage.setText(error);
                    loginInfoMessage.setTextColor(Color.RED);
                } else {
                    saveNewPassword(email, oldPass, newPass);
                }
                break;
            case R.id.backButton:
                switchIntent(AccountActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

//        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
//            switchIntent(ChangePasswordActivity.class); // Change this to the landscape version
//        }

        loginInfoMessage = (TextView) v.findViewById(R.id.loginInfo);
        emailTextEntry = (EditText) v.findViewById(R.id.email);
        passwordTextEntry = (EditText) v.findViewById(R.id.password);
        newPassTextEntry = (EditText) v.findViewById(R.id.newPassword);
        confirmPassTextEntry = (EditText) v.findViewById(R.id.newPasswordConfirm);

        Button btnAdd = (Button) v.findViewById(R.id.backButton);
        btnAdd.setOnClickListener(this);

        btnAdd = (Button) v.findViewById(R.id.doneButton);
        btnAdd.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
