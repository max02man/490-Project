package edu.louisville.cischef.showrecipe;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import edu.louisville.cischef.Constants;
import edu.louisville.cischef.Delete.DeleteFragment;
import edu.louisville.cischef.FavRecipe;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;
import edu.louisville.cischef.favorite.FavoriteFragment;
import edu.louisville.cischef.signIn.CreateAccountFragment;


/**
 * Created by Max02man on 12/2/2016.
 */

public class showrecipe extends Fragment {
    private long recipeId;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeReference =mRootRef.child("recipe");
    DatabaseReference mRecipeReference1 =mRootRef.child("favorite");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Random r = new Random();
    long rondomNumber = r.nextInt();
    String title, pic, mesg, author;


    public showrecipe(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_recipe, container, false);
        final Query query = mRecipeReference.orderByChild("id").equalTo(recipeId);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Recipe recipe = new Recipe();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                    recipe = childSnapshot.getValue(Recipe.class);
                }
                title = recipe.getTitle();
                pic =recipe.getPicture();
                mesg=recipe.getMessage();
                author=recipe.getAuthor();

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

        if(user !=null) {

        Button butDelete =(Button) view.findViewById(R.id.butdelete);
            //Hide the button when the user unathenticated
         butDelete.setVisibility(view.VISIBLE);

        butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    DeleteFragment fragment = new DeleteFragment();
                    fragment.setRecipeId(recipeId);
                    getActivity().getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContent, fragment)
                            .commit();

            }
        });
        }
        if(user !=null) {
            Button butfav =(Button) view.findViewById(R.id.butfav);
            //Hide the button when the user unathenticated
            butfav.setVisibility(view.VISIBLE);
            butfav.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {


                FavoriteFragment fragment = new FavoriteFragment();
                Recipe recipe = new Recipe();
                FavRecipe favRecipe =new FavRecipe(
                        user.getUid(),
                        rondomNumber,
                        title,
                        pic,
                        mesg,
                        author);

                String key = mRecipeReference1.push().getKey();
                Map<String, Object> updates = new HashMap<String, Object>();
                updates.put(key, favRecipe);

                Log.d(Constants.TAG, "favorite:" + favRecipe.toString());
                mRecipeReference1.updateChildren(updates);


                    getActivity().getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContent, fragment)
                            .commit();

                }
            });
        }



        return view;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }
}