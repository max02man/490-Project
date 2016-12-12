package edu.louisville.cischef.signIn;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.R;
import edu.louisville.cischef.User;
import edu.louisville.cischef.recipeList.RecipeListFragment;
import edu.louisville.cischef.topmenu.TopMenuFragment;

/**
 * Created by nyelo on 12/7/2016.
 */

public class CreateAccountFragment extends Fragment {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    EditText mTextEmail;
    EditText mTextPassword;
    EditText mTextUsername;
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.account_create_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        createAuthListener();

        mTextUsername = (EditText)view.findViewById(R.id.textUsername);
        mTextEmail = (EditText)view.findViewById(R.id.textEmail);
        mTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mTextEmail.length()<=0)
                    mTextEmail.setError("Email is Required");
                if (!mTextEmail.getText().toString().contains("@"))
                    mTextEmail.setError("Valid Email is Required");
            }
        });

        mTextPassword = (EditText) view.findViewById(R.id.textPassword);
        mTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mTextPassword.length()<=6)
                    mTextPassword.setError("Password must be at least 6 characters");
            }
        });
        pb=(ProgressBar)view.findViewById(R.id.progress_bar);
        Button btnSignIn = (Button)view.findViewById(R.id.btnCreateAccount);
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // logic that stops bad submissions
                Pattern pattern = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}");
                Matcher matcher = pattern.matcher(mTextEmail.getText().toString().toUpperCase());
                if(view.getId() == R.id.btnCreateAccount){
                    if(TextUtils.isEmpty(mTextEmail.getText())) {
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
                    }
                    else if(!matcher.matches()){
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    }
                    else if(mTextPassword.length()<6){
                        Toast.makeText(getActivity().getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        createUser();

                    }

                }

            }
        });

        return view;
    }

    // Method that actually creates account through Firebase
    private void createUser(){
        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mTextEmail.getText().toString(), mTextPassword.getText().toString())
                .addOnCompleteListener((new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(Constants.TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        pb.setVisibility(View.INVISIBLE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity().getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    // listens for a sign in and returns user to home screen when sign in occurs
    private void createAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(Constants.TAG, "onAuthStateChagned:signed_in:" + user.getUid());
                    //update the username
                    if(user!=null) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(mTextUsername.getText().toString()).build();
                        user.updateProfile(profileUpdates);
                    }

                    loadFragment(new TopMenuFragment(), new RecipeListFragment());

                }
            }
        };
    }

    public void loadFragment(Fragment fragment2load, Fragment loadFragment2) {
        getFragmentManager()
                .beginTransaction()

                .replace(R.id.fragmentContent,  loadFragment2)
                .replace(R.id.fragmentMain, fragment2load)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}


