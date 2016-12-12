package edu.louisville.cischef.favorite;

import android.app.Fragment;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.List;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;
import edu.louisville.cischef.showrecipe.showrecipe;

/**
 * Created by Max02man on 12/6/2016.
 */

public class FavoriteFragment extends Fragment {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeReference =mRootRef.child("recipe");

    List<Long> listofIds =new ArrayList<Long>();
    ListView favlistView;
    ListAdapter favlistAdapter;
    HashMap<Integer,Long> favMap = new HashMap<>();
    public FavoriteFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favMap = new HashMap<>();
        favlistView =(ListView) view.findViewById(R.id.list);
        favlistAdapter= new FirebaseListAdapter<Recipe>(getActivity(),Recipe.class, R.layout.recipe_layout,mRecipeReference) {
            @Override
            protected void populateView(View v, Recipe model, int position) {
                Log.d(Constants.TAG,model.toString());
                TextView recpieName = (TextView)v.findViewById(R.id.recpieName);
                recpieName.setText(model.getTitle());
                TextView authorName = (TextView)v.findViewById(R.id.authorName);
                authorName.setText(model.getAuthor());
                favMap.put(position,model.getId());


                listofIds.add(position,model.getId());
            }

        };
        favlistView.setAdapter(favlistAdapter);

        favlistView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                showrecipe showrecipe = new showrecipe();
                showrecipe.setRecipeId(favMap.get(position));
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
