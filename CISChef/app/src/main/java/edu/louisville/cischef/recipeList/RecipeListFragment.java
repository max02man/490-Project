package edu.louisville.cischef.recipeList;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.MainActivity;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;
import edu.louisville.cischef.showrecipe.showrecipe;
import edu.louisville.cischef.topmenu.TopMenuFragment;

/**
 * Created by Max02man on 11/24/2016.
 */

public class RecipeListFragment extends Fragment {
    DatabaseReference mRootRef =FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeReference =mRootRef.child("recipe");

    List<Long> listofIds =new ArrayList<Long>();
    ListView recipelistView;
    ListAdapter recipelistAdapter;
    public RecipeListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_recipe_list, container, false);
        recipelistView =(ListView) view.findViewById(R.id.list);
        recipelistAdapter= new FirebaseListAdapter<Recipe>(getActivity(),Recipe.class,R.layout.recipe_layout,mRecipeReference) {
            @Override
            protected void populateView(View v, Recipe model, int position) {
                Log.d(Constants.TAG,model.toString());
                TextView recpieName = (TextView)v.findViewById(R.id.recpieName);
                recpieName.setText(model.getTitle());
                TextView authorName = (TextView)v.findViewById(R.id.authorName);
                authorName.setText(model.getAuthor());


                listofIds.add(position,model.getId());
            }

        };
        recipelistView.setAdapter(recipelistAdapter);

        recipelistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                loadFragment(new showrecipe());
            }
        });

        return view;
    }
    private void loadFragment(Fragment fragment2load) {

        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContent, fragment2load)

                .commit();
    }

}
