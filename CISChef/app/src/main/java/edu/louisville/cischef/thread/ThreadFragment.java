package edu.louisville.cischef.thread;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;
import edu.louisville.cischef.recipeList.RecipeListFragment;
import edu.louisville.cischef.showrecipe.showrecipe;

/**
 * Created by Max02man on 11/24/2016.
 */

public class ThreadFragment extends Fragment{
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeReference =mRootRef.child("recipe");

    public ThreadFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View)  inflater.inflate(R.layout.fragment_thread, container, false);

        //get fields & validate
        final EditText editTitle = (EditText)view.findViewById(R.id.edittitle);
        editTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (editTitle.length()<=0)
                    editTitle.setError("Title is Required");
            }
        });

        final EditText editMessage = (EditText)view.findViewById(R.id.editmessage);
        editMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (editMessage.length()<=0)
                    editMessage.setError("Recipe is Required");
            }
        });

        final EditText editPic = (EditText)view.findViewById(R.id.editpic);
        final EditText editAuthor = (EditText)view.findViewById(R.id.editauthor);


        Button butSubmit =(Button) view.findViewById(R.id.butsubmit);
        butSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = mRecipeReference.push().getKey();
                Recipe recipe = new Recipe(
                        156,
                        editTitle.getText().toString(),
                        editPic.getText().toString(),
                        editMessage.getText().toString(),
                        editAuthor.getText().toString()
                );
                Map<String, Object> updates = new HashMap<String, Object>();
                updates.put(key, recipe);

                Log.d(Constants.TAG, "recipe:" + recipe.toString());
                mRecipeReference.updateChildren(updates);
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContent, new RecipeListFragment())
                        .commit();
            }
        });
        return view;
    }
}
