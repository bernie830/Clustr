package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nothing.hunnaz.clustr.UserDB.User;


/**
 *
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText usernameTextEntry;
    private EditText emailTextEntry;
    private EditText passwordTextEntry;
    private EditText passwordConfirmTextEntry;
    private EditText dobTextEntry;

    private static final String TAG = "RegisterFragment";

    private User newUser;

    private FirebaseAuth mAuth;
    private FirebaseUser currentFirebaseUser;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize auth and database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Buttons
        v.findViewById(R.id.cancelButtonRegister).setOnClickListener(this);
        v.findViewById(R.id.doneRegisterButton).setOnClickListener(this);

        // Text Views
        usernameTextEntry = (EditText) v.findViewById(R.id.userNameRegister);
        emailTextEntry = (EditText) v.findViewById(R.id.emailRegister);
        passwordTextEntry = (EditText) v.findViewById(R.id.passwordRegister);
        passwordConfirmTextEntry = (EditText) v.findViewById(R.id.passwordRegisterConfirm);
        dobTextEntry =  (EditText) v.findViewById(R.id.dateOfBirthRegister);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) before use
        currentFirebaseUser = mAuth.getCurrentUser();
    }

    /* Returns true if username already exists.
     * Queries the database for the first user with username equal to @param username
     */
    private  boolean confirmUsername(String username){
        final boolean[] result = {false};
        mDatabase.child("users").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    // username already exists
                    result[0] = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        return result[0];
    }

    private static boolean confirmEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private static boolean confirmPasswordEquality(String password, String confirmationPassword){
        return password.equals(confirmationPassword);
    }

    private static String confirmPasswordValidity(String password){
        // ADD Password validation such as numbers, letters, etc.
        String returnVal = "";
        int passLength = 6; // required length by Firebase

        if(password.length() < passLength) {
            returnVal = "ERROR: The password entered is not long enough. Must be at least " + passLength + " characters.";
        }
        return returnVal;
    }

    private String registerUser() {
        String username = usernameTextEntry.getText().toString();
        String email = emailTextEntry.getText().toString();
        String password = passwordTextEntry.getText().toString();
        String passwordConfirm = passwordConfirmTextEntry.getText().toString();
        String date = dobTextEntry.getText().toString();
        Date dob = new Date(date);
        String currentError = "";

       String passwordError = confirmPasswordValidity(password);

        if (username.equals("") || email.equals("") || password.equals("") || passwordConfirm.equals("") || date.equals("")) {
            currentError = "ERROR: All fields are required to be filled in";
        } else if (!confirmUsername(username)) {
            currentError = "ERROR: The username entered already exists.";
        } else if (!confirmEmail(email)) {
            currentError = "ERROR: The email entered is not a valid email address.";
        } else if (passwordError.length() > 0) {
            currentError = passwordError;
        }else if(!confirmPasswordEquality(password, passwordConfirm)) {
            currentError = "ERROR: The entered password does not match the confirmation password.";
        } else if (!dob.confirmDate()) {
            currentError = "ERROR: The date of birth entered is not valid.";
        } else {
            //NO errors occurred

            // Register the user with Firebase and add the account to the db
            newUser = new User(dob.toString(), email, username, "");
            createAccount(email, password);
            if(currentFirebaseUser != null) {
                // Sign in new user after creating account
                signIn(email, password);
                if(currentFirebaseUser != null) {
                    newUser.setAccountID(currentFirebaseUser.getUid());
                    // Add user to Firebase database
                    addUserToDatabase(newUser);
                } else {
                    currentError = "ERROR: Could not sign in user.";
                }
            } else {
                currentError = "ERROR: Could not create user.";
            }

            switchIntent(HomeActivity.class);
        }
        return currentError;
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "create account: " + email);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail: success");
                    currentFirebaseUser = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail: failure", task.getException());
                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    // TODO: Clear text views if auth fails?
                }
            }
        });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "sign in: " + email);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    currentFirebaseUser = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addUserToDatabase(User user) {
        Log.d(TAG, "Signed in: " + user.getEmail());
        mDatabase.child("users").child(user.getAccountID()).setValue(user);
    }

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent().getParent();
        TextView errorMessageText = (TextView) v.findViewById(R.id.errorMessage);

        //Because from here we only ever want to return to the Home screen
        //TODO Change to go to the home screen

        switch (view.getId()) {
            case R.id.cancelButtonRegister:
                switchIntent(LoginActivity.class);
                break;
            case R.id.doneRegisterButton:
                Log.d(TAG, "Register Button pressed");
                String error = registerUser();
                Log.d(TAG, error);
                if(error.length() > 0){
                    errorMessageText.setText(error);
                    errorMessageText.setTextColor(Color.RED);
                } else {
                    // Logs the user in automatically if no errors
                    switchIntent(HomeActivity.class);
                }
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
