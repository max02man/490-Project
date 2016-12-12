package edu.louisville.cischef.showrecipe;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;
import edu.louisville.cischef.favorite.FavoriteFragment;
import edu.louisville.cischef.recipeList.RecipeListFragment;

/**
 * Created by Max02man on 12/2/2016.
 */

public class showrecipe extends Fragment {
    private long recipeId;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeReference =mRootRef.child("recipe");

    private long recipeid2Delete;

    public long getRecipeid2Delete() {
        return recipeid2Delete;
    }

    public void setRecipeid2Delete(long recipeid2Delete) {
        this.recipeid2Delete = recipeid2Delete;
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
                Recipe recipe = null;
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

                Query q = mRecipeReference.orderByChild("id").equalTo(recipeid2Delete);
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Log.d(Constants.TAG, "Deleting:" + childSnapshot.getKey());
                            childSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(Constants.TAG, "Error occured:" + databaseError);
                    }

               });
                TextView textView = (TextView)view.findViewById(R.id.txtViewDelMessage);
                textView.setText("Removed: " + recipeid2Delete);
           }
        });

        Button butFav =(Button) view.findViewById(R.id.butfav);
        butFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Recipe recipe = new Recipe();

                    Map<String, Object> updates = new HashMap<String, Object>();
                    // updates.put(key, recipe);

                    Log.d(Constants.TAG, "recipe:" + recipe.toString());
                    mRecipeReference.updateChildren(updates);
                    getActivity().getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContent, new FavoriteFragment())
                            .commit();
                }

            }
        );


        return view;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }
}