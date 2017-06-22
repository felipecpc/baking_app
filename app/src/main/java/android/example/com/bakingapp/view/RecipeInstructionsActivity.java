package android.example.com.bakingapp.view;

import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.adapters.ItemSelectedInterface;
import android.example.com.bakingapp.database.BakingAppDBHelper;
import android.example.com.bakingapp.database.DatabaseHelper;
import android.example.com.bakingapp.model.RecipeModel;
import android.example.com.bakingapp.model.StepsModel;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeInstructionsActivity extends AppCompatActivity implements ItemSelectedInterface {

    private static String TAG = RecipeInstructionsActivity.class.getSimpleName();
    private RecipeModel mRecipeModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);


        if(getIntent().hasExtra(StepsModel.ID)){
            Log.d(TAG,"This is the ID " + getIntent().getIntExtra(StepsModel.ID,0));
            int id = getIntent().getIntExtra(StepsModel.ID,0);

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
        Intent intent = new Intent(this,StepDetailsActivity.class);
        intent.putExtra(StepsModel.DATA,mRecipeModel.getSteps().get(selected));
        startActivity(intent);
    }
}
