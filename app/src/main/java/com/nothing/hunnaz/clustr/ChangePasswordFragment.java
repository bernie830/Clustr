package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nothing.hunnaz.clustr.UserDB.UserSingleton;

/**
 *
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener {
    private EditText usernameTextEntry;
    private EditText passwordTextEntry;
    private EditText newPassTextEntry;
    private EditText confirmPassTextEntry;
    private TextView loginInfoMessage;

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    private boolean confirmOldInformation(String username, String pass){
        UserSingleton singleton = UserSingleton.get(this.getContext());
        boolean validLogin = singleton.isValidLogin(username, pass);
        return validLogin;
    }

    // TODO - Does nothing right now
    private void saveNewPassword(String username, String newPass){
        UserSingleton singleton = UserSingleton.get(this.getContext());
        singleton.changePassword(username, newPass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doneButton:
                String username = usernameTextEntry.getText().toString();
                String oldPass = passwordTextEntry.getText().toString();
                String newPass = newPassTextEntry.getText().toString();
                String confirmPass = confirmPassTextEntry.getText().toString();
                String error = "";
                boolean validLogin = confirmOldInformation(username, oldPass);
                if(validLogin && !newPass.equals(confirmPass)){
                    error = "The entered passwords do not match";
                } else if (validLogin && newPass.length() < 8) {
                    error = "The new passwords must be at least 8 characters in length";
                }
                if(!validLogin && error.length() == 0){
                    error = "The old login information was incorrect or could not be found";
                }

                if(error.length() != 0){
                    loginInfoMessage.setText(error);
                } else {
                    saveNewPassword(username, newPass);
                    switchIntent(AccountActivity.class);
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

        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            switchIntent(ChangePasswordActivity.class); // Change this to the landscape version
        }

        loginInfoMessage = (TextView) v.findViewById(R.id.loginInfo);
        usernameTextEntry = (EditText) v.findViewById(R.id.userName);
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
