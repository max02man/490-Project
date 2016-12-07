package edu.louisville.cischef.showrecipe;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.DelFragment;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;
import edu.louisville.cischef.recipeList.RecipeListFragment;

/**
 * Created by Max02man on 12/2/2016.
 */

public class showrecipe extends Fragment {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeReference =mRootRef.child("recipe");

    private long recipeId;
    public long getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public showrecipe(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_recipe, container, false);
        Query query = mRecipeReference.orderByChild("id").equalTo(recipeId);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Recipe recipe = new Recipe();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                    recipe = childSnapshot.getValue(Recipe.class);
                }
                TextView titleName = (TextView)view.findViewById(R.id.titlename);
                titleName.setText(recipe.getTitle());

                TextView textViewWriter = (TextView)view.findViewById(R.id.writer);
                textViewWriter.setText("Author: "+ recipe.getAuthor());

                TextView textViewmessage = (TextView)view.findViewById(R.id.message);
                textViewmessage.setText(recipe.getMessage());

                Log.d(Constants.TAG,recipe.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        Button butDelete =(Button) view.findViewById(R.id.butdelete);
        butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DelFragment fragment = new DelFragment();
                fragment.setRecipeId(recipeId);
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContent, fragment)
                        .commit();

            }
        });


        return view;

    }

}