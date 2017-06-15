package android.example.com.bakingapp.view;

import android.example.com.bakingapp.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeInstructionsActivity extends AppCompatActivity implements RecipeMasterListFragment.OnRecipeItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_instructions);
    }

    @Override
    public void onRecipeItemClickListener(int position) {

    }
}
