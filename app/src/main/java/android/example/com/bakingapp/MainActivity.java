package android.example.com.bakingapp;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
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
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ItemSelectedInterface {

    private static final String LIST_STATE = "LIST_STATE";
    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private static ArrayList<RecipeModel> mRecipeList = null;

    private final String TAG = MainActivity.class.getSimpleName();
    final DatabaseHelper mDBHelper = new DatabaseHelper(this);
    private ConnectionManager cManager;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridlayoutManager;
    private Parcelable mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipeRecyclerView = (RecyclerView) findViewById(R.id.rv_recipes);
        mRecipeRecyclerView.setVisibility(View.INVISIBLE);


        cManager = ConnectionManager.getInstance();

        if(findViewById(R.id.framelayout_tablet)==null) {
            layoutManager = new LinearLayoutManager(this);
            mRecipeRecyclerView.setLayoutManager(layoutManager);
        }else{
            gridlayoutManager = new GridLayoutManager(this,2);
            mRecipeRecyclerView.setLayoutManager(gridlayoutManager);
        }

        mRecipeList = mDBHelper.getRecipes();

        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(this);
        mRecipeAdapter.setRecipes(mRecipeList);
        if(savedInstanceState!=null) {
            mBundleRecyclerViewState = savedInstanceState.getParcelable(LIST_STATE);
            // restore RecyclerView state
            if (mBundleRecyclerViewState != null) {
                mRecipeRecyclerView.getLayoutManager().onRestoreInstanceState(mBundleRecyclerViewState);
            }

        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                refreshData();
            }
        }, 200);



        mRecipeRecyclerView.setVisibility(View.VISIBLE);
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);


    }

    public void refreshData(){


        cManager.request(ConnectionManager.URL, new ConnectionCallback() {
            @Override
            public void connectionResponse(int status, Object data) {

                if (status == ConnectionManager.FAIL) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(getResources().getString(R.string.error_title));
                    alertDialog.setMessage((String) data);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    mRecipeList = mDBHelper.getRecipes();

                    if (mRecipeList.isEmpty())
                        alertDialog.show();
                    else
                        mRecipeAdapter.setRecipes(mRecipeList);
                } else {
                    ArrayList<RecipeModel> recipes = (ArrayList<RecipeModel>) data;
                    for (RecipeModel recipe : recipes) {
                        mDBHelper.addRecipe(recipe);
                        mRecipeAdapter.setRecipes(mDBHelper.getRecipes());
                    }

                    mRecipeList = recipes;
                }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();


        //Update widget
        Intent intent = new Intent(this,RecipeWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        intent.putExtra(RecipeInstructionsActivity.RECIPE,0);
        sendBroadcast(intent);
    }

    @Override
    public void itemSelected(int selected) {
        Log.d(TAG, "Recipe selected " + selected + " " + mRecipeList.get(selected).getId());
        Intent intent = new Intent(this,RecipeInstructionsActivity.class);
        intent.putExtra(StepsModel.ID,mRecipeList.get(selected).getId());
        intent.putExtra(StepsModel.RECIPE_NAME,mRecipeList.get(selected).getName());
        startActivity(intent);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save list state
        mBundleRecyclerViewState = mRecipeRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_STATE, mBundleRecyclerViewState);

    }
}
