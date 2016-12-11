package edu.louisville.cischef;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.louisville.cischef.recipeList.RecipeListFragment;
import edu.louisville.cischef.signIn.signInFragment;
import edu.louisville.cischef.topmenu.TopMenuFragment;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadFragment(new TopMenuFragment(), new RecipeListFragment());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void loadFragment(Fragment fragment2load, Fragment loadFragment2) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContent,  loadFragment2)
                .replace(R.id.fragmentMain, fragment2load)
                .commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        MenuItem signin = menu.findItem(R.id.action_sign_in);
        MenuItem signout = menu.findItem(R.id.action_sign_out);
        if (user != null) {
            signin.setVisible(false);
            signout.setVisible(true);
        } else {
            signin.setVisible(true);
            signout.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            loadFragment(new TopMenuFragment(), new RecipeListFragment());
        }
        else if (id == R.id.action_sign_in) {
            loadFragment(new TopMenuFragment(), new signInFragment());
        }
        else if (id == R.id.action_sign_out) {
            // signs current user out and returns them to the home screen
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "User Signed Out: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                Log.d(Constants.TAG, "signOut:" + user.getUid());
                loadFragment(new TopMenuFragment(), new RecipeListFragment());
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
