package edu.louisville.cischef.favorite;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.FavRecipe;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;
import edu.louisville.cischef.recipeList.RecipeListFragment;
import edu.louisville.cischef.showrecipe.showrecipe;
import edu.louisville.cischef.signIn.CreateAccountFragment;

/**
 * Created by Max02man on 12/6/2016.
 */

public class FavoriteFragment extends Fragment {
    public FavoriteFragment(){}

        @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_favorite, container, false);

        return view;
    }
}