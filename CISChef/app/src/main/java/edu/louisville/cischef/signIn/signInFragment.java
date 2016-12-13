package edu.louisville.cischef.signIn;

import android.app.Fragment;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.MainActivity;
import edu.louisville.cischef.R;
import edu.louisville.cischef.recipeList.RecipeListFragment;
import edu.louisville.cischef.topmenu.TopMenuFragment;

/**
 * Created by nyelo on 12/7/2016.
 */

public class signInFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    EditText mTextEmail;
    EditText mTextPassword;
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_sign_in, container, false);

        mAuth = FirebaseAuth.getInstance();

        createAuthListener();

        TextView txtAccountCreate = (TextView) view.findViewById(R.id.txtCreateAccount);
                txtAccountCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadFragment(new TopMenuFragment(), new CreateAccountFragment());
                    }
                });

        // sets up validation hints
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

        // sets up validation hints
        mTextPassword = (EditText) view.findViewById(R.id.textPassword);
        mTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mTextPassword.length()<=6)
                    mTextPassword.setError("Password must be at least 6 characters");
            }
        });

        Button btnSignIn = (Button)view.findViewById(R.id.sign_in_button);
        btnSignIn.setOnClickListener(this);

        pb = (ProgressBar)view.findViewById(R.id.progress_bar);

        return view;

    }

    // listens for login/logout events
    private void createAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(Constants.TAG, "onAuthStateChagned:signed_in:" + user.getUid());
                    loadFragment(new TopMenuFragment(), new RecipeListFragment());
                    Toast.makeText(getActivity().getApplicationContext(), "User Signed In: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    // method that actually signs in through Firebase
    private void SignInUidPass(){

        pb.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(mTextEmail.getText().toString(), mTextPassword.getText().toString())
        .addOnCompleteListener((new OnCompleteListener<AuthResult>() {
            // listener for sign in completing and successfully signing in
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(Constants.TAG,"signInWithEmail:onComplete:" + task.isSuccessful());

                if(!task.isSuccessful()){
                    pb.setVisibility(View.INVISIBLE);
                    Log.d(Constants.TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(getActivity().getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
                }

            }
        }));





    }




    @Override
    public void onClick(View view) {
        // logic that stops bad submissions
        Pattern pattern = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}");
        Matcher matcher = pattern.matcher(mTextEmail.getText().toString().toUpperCase());
        if(view.getId() == R.id.sign_in_button){
            if(TextUtils.isEmpty(mTextEmail.getText())) {
                Toast.makeText(getActivity().getApplicationContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
                }
            else if(!matcher.matches()){
                Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(mTextPassword.getText())){
                Toast.makeText(getActivity().getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
            }
            else
                SignInUidPass();
            }
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

    public void loadFragment(Fragment fragment2load, Fragment loadFragment2) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContent,  loadFragment2)
                .replace(R.id.fragmentMain, fragment2load)
                .commit();
    }
}
