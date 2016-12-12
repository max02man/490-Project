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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.FavRecipe;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;
import edu.louisville.cischef.recipeList.RecipeListFragment;
import edu.louisville.cischef.showrecipe.ShowRecipeFav;
import edu.louisville.cischef.showrecipe.showrecipe;
import edu.louisville.cischef.signIn.CreateAccountFragment;

/**
 * Created by Max02man on 12/6/2016.
 */

public class FavoriteFragment extends Fragment {
    public FavoriteFragment(){}
    DatabaseReference mRootRef =FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeReference =mRootRef.child("favorite");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    List<Long> listofIds =new ArrayList<Long>();
    ListView recipelistView;
    ListAdapter recipelistAdapter;
    HashMap<Integer,Long> reciprMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_favorite, container, false);
        reciprMap = new HashMap<>();
        recipelistView =(ListView) view.findViewById(R.id.list2);
        recipelistAdapter= new FirebaseListAdapter<Recipe>(getActivity(),Recipe.class, R.layout.recipe_layout,mRecipeReference) {


                @Override
            protected void populateView(View v, Recipe model, int position) {
                Log.d(Constants.TAG,model.toString());
                TextView recpieName = (TextView)v.findViewById(R.id.recpieName);
                recpieName.setText(model.getTitle());
                TextView authorName = (TextView)v.findViewById(R.id.authorName);
                authorName.setText(model.getAuthor());
                reciprMap.put(position,model.getId());


                listofIds.add(position,model.getId());
            }

        };
        recipelistView.setAdapter(recipelistAdapter);



        recipelistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ShowRecipeFav showrecipe = new ShowRecipeFav();
                showrecipe.setRecipeId(reciprMap.get(position));
                Log.d(Constants.TAG, "recipeid: " + showrecipe.getRecipeId());

                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContent, showrecipe)

                        .commit();
            }
        });
        return view;
    }

}
