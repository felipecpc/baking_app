package android.example.com.bakingapp.view;

import android.example.com.bakingapp.R;
import android.example.com.bakingapp.adapters.ItemSelectedInterface;
import android.example.com.bakingapp.database.BakingAppDBHelper;
import android.example.com.bakingapp.database.DatabaseHelper;
import android.example.com.bakingapp.model.RecipeModel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class RecipeInstructionsActivity extends AppCompatActivity implements ItemSelectedInterface {

    private static String TAG = RecipeInstructionsActivity.class.getSimpleName();
    private RecipeModel mRecipeModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra("ID")){
            Log.d(TAG,"This is the ID " + getIntent().getIntExtra("ID",0));
            int id = getIntent().getIntExtra("ID",0);

            DatabaseHelper test = new DatabaseHelper(this);
            mRecipeModel = test.getRecipeDetails(id);

        }

        setContentView(R.layout.activity_recipe_instructions);

    }

    public RecipeModel getRecipeDetails(){
        return mRecipeModel;
    }

    @Override
    public void itemSelected(int selected) {
        Log.d(TAG,"Item clicked position " + selected);
    }
}
