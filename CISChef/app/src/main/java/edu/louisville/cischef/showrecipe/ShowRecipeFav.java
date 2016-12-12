package edu.louisville.cischef.showrecipe;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.FavRecipe;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;

/**
 * Created by MAX MAN on 12/12/2016.
 */

public class ShowRecipeFav extends Fragment{
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeReference =mRootRef.child("favorite");
    public ShowRecipeFav(){}
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private long recipeId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_show_recipe_fav, container, false);
        final Query query = mRecipeReference.orderByChild("id").equalTo(recipeId);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FavRecipe favrecipe = new FavRecipe();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    favrecipe = childSnapshot.getValue(FavRecipe.class);
                }
//                Query q = mRecipeReference.orderByChild("userid").equalTo(user.getUid());

                    TextView titleName = (TextView) view.findViewById(R.id.titlename);
                    titleName.setText(favrecipe.getTitle());

                    TextView textViewWriter = (TextView) view.findViewById(R.id.writer);
                    textViewWriter.setText("Author: " + favrecipe.getAuthor());

                    TextView textViewmessage = (TextView) view.findViewById(R.id.message);
                    textViewmessage.setText(favrecipe.getMessage());

                    Log.d(Constants.TAG, favrecipe.toString());
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return view;
    }

    public long getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(long recipeId) {
            this.recipeId = recipeId;
        }


    }
