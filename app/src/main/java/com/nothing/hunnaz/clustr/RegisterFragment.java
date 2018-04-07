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
import com.google.firebase.database.ValueEventListener;


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
        emailTextEntry = (EditText) v.findViewById(R.id.inEmailRegister);
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
        final boolean[] result = {true};
        mDatabase.child("users").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    // username already exists
                    result[0] = false;
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
        } else if (!dob.confirmDate(false)) {
            currentError = "ERROR: The date of birth entered is not valid.";
        } else {
            //NO errors occurred

            // Register the user with Firebase and add the account to the db
            String dateOfBirth = dob.toString();
            newUser = new User(dateOfBirth, email, username, "");
            createAccount(email, password);

        }
        return currentError;
    }

    private void createAccount(final String email, final String password) {
        Log.d(TAG, "create account: " + email);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createAccount: success");
                    currentFirebaseUser = mAuth.getCurrentUser();
                    signIn(email, password);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "createAccount: failure", task.getException());
                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                    newUser.setAccountID(currentFirebaseUser.getUid());
                    addUserToDatabase();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addUserToDatabase() {
        Log.d(TAG, "Add to databaase: " + newUser.getEmail());
        mDatabase.child("users").child(newUser.getAccountID()).setValue(newUser);
        // If the process reaches this point, account creation has been a success.
        // Go to home activity.
        switchIntent(HomeActivity.class);
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
                String error = "Internet connection is required to register a user.";
                if(isNetworkAvailable()){
                    error = registerUser();
                }
                Log.d(TAG, error);
                if(error.length() > 0){
                    errorMessageText.setText(error);
                    errorMessageText.setTextColor(Color.RED);
                } else {
                    // Logs the user in automatically if no errors
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
