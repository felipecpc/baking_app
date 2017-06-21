package android.example.com.bakingapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.example.com.bakingapp.adapters.ItemSelectedInterface;
import android.example.com.bakingapp.adapters.RecipeAdapter;
import android.example.com.bakingapp.connection.ConnectionCallback;
import android.example.com.bakingapp.connection.ConnectionManager;
import android.example.com.bakingapp.database.DatabaseHelper;
import android.example.com.bakingapp.model.RecipeModel;
import android.example.com.bakingapp.model.StepsModel;
import android.example.com.bakingapp.view.RecipeInstructionsActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemSelectedInterface {

    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private static ArrayList<RecipeModel> mRecipeList = null;

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);


        mRecipeRecyclerView = (RecyclerView) findViewById(R.id.rv_recipes);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecipeRecyclerView.setLayoutManager(layoutManager);

        mRecipeRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this);
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);

        final DatabaseHelper mDBHelper = new DatabaseHelper(this);

        ConnectionManager cManager = ConnectionManager.getInstance();
        cManager.request(ConnectionManager.URL, new ConnectionCallback() {
            @Override
            public void connectionResponse(int status, Object data) {
                dialog.dismiss();

                if(status == ConnectionManager.FAIL){
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage((String) data);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }else{
                    ArrayList<RecipeModel> recipes = (ArrayList<RecipeModel>) data;
                    for(RecipeModel recipe: recipes) {
                        mDBHelper.addRecipe(recipe);
                        mRecipeAdapter.setRecipes(mDBHelper.getRecipes());
                    }

                    mRecipeList = recipes;
                }

            }
        });

    }

    @Override
    public void itemSelected(int selected) {
        Log.d(TAG, "Recipe selected " + selected + " " + mRecipeList.get(selected).getId());
        Intent intent = new Intent(this,RecipeInstructionsActivity.class);
        intent.putExtra(StepsModel.ID,mRecipeList.get(selected).getId());
        startActivity(intent);
    }


}
