package edu.louisville.cischef.add;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.louisville.cischef.Constants;
import edu.louisville.cischef.R;
import edu.louisville.cischef.Recipe;
import edu.louisville.cischef.recipeList.RecipeListFragment;

/**
 * Created by Max02man on 12/6/2016.
 */

public class AddFragment extends Fragment {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeReference =mRootRef.child("recipe");
    Random r = new Random();
    int rondomNumber = r.nextInt();
    boolean threadNotComplete=false;
    public AddFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View)  inflater.inflate(R.layout.fragment_add, container, false);

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
        editPic.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange (View view, boolean b){
                if (editPic.length()<=0)
                    editPic.setError("Name of the picture is Required");
            }
        });

        final EditText editAuthor = (EditText)view.findViewById(R.id.editauthor);
        editPic.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange (View view, boolean b){
                if (editAuthor.length()<=0)
                    editAuthor.setError("Author is Required");
            }
        });


        Button butSubmit =(Button) view.findViewById(R.id.butsubmit);
        butSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (editTitle == null && editMessage == null && editAuthor == null) {
                String title = editTitle.getText().toString();
                String msg = editMessage.getText().toString();
                String author = editAuthor.getText().toString();

                if(!(TextUtils.isEmpty(title) ||
                        TextUtils.isEmpty(msg) ||
                        TextUtils.isEmpty(author))){
                    String key = mRecipeReference.push().getKey();
                    Recipe recipe = new Recipe(
                            rondomNumber,
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
                else
                {
                    Toast.makeText(getActivity(), "Fill everything PLEASE",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
