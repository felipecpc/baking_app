package android.example.com.bakingapp.view;

import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.adapters.ItemSelectedInterface;
import android.example.com.bakingapp.database.BakingAppDBHelper;
import android.example.com.bakingapp.database.DatabaseHelper;
import android.example.com.bakingapp.model.RecipeModel;
import android.example.com.bakingapp.model.StepsModel;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

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
    private int mId;

    StepDetailsFragment stepsFragment;
    StepsBrowserFragment stepsBrowserFragment;
    ExoPlayerFragment playerFragment;
    FragmentManager fragmentManager;
    FrameLayout fLayout;
    int mStepId;
    int mStepsTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra(StepsModel.ID)){

            mId = getIntent().getIntExtra(StepsModel.ID,0);
            Log.d(TAG,"This is the ID " + mId);
            DatabaseHelper test = new DatabaseHelper(this);
            mRecipeModel = test.getRecipeDetails(mId);
            mStepId = 0;
            mStepsTotal = mRecipeModel.getSteps().size();
        }

        setContentView(R.layout.activity_recipe_instructions);

        // Determine if you're creating a two-pane or single-pane display
        if(findViewById(R.id.linearLayout_recipe_instructions) != null) {
            fLayout = (FrameLayout) findViewById(R.id.framelayout_mediaplayer);
            // Only create new fragments when there is no previously saved state
            if (savedInstanceState == null) {

                fragmentManager = getSupportFragmentManager();

                stepsFragment = new StepDetailsFragment();

                StepsModel steps = null;

                    DatabaseHelper test = new DatabaseHelper(this);
                    steps = test.getRecipeStep(mId, mStepId);

                stepsFragment.setData(steps);


                fragmentManager.beginTransaction()
                        .add(R.id.framelayout_steps_details, stepsFragment)
                        .commit();


                playerFragment = new ExoPlayerFragment();

                if (steps.getVideoURL() != null && !steps.getVideoURL().isEmpty())
                    playerFragment.setUrl(steps.getVideoURL());
                else
                    fLayout.setVisibility(View.GONE);
                fragmentManager.beginTransaction()
                        .add(R.id.framelayout_mediaplayer, playerFragment)
                        .commit();


                stepsBrowserFragment = new StepsBrowserFragment();

                fragmentManager.beginTransaction()
                        .add(R.id.framelayout_steps_browser, stepsBrowserFragment)
                        .commit();

                stepsBrowserFragment.setDualPane(true);
                stepsBrowserFragment.setData(mId, mStepId, mStepsTotal);


            }
        }
    }

    public void refreshFragments(int step){
        mStepId = step;

        stepsFragment = new StepDetailsFragment();

        StepsModel steps = null;

        DatabaseHelper test = new DatabaseHelper(this);
        steps = test.getRecipeStep(mId, mStepId);

        stepsFragment.setData(steps);


        fragmentManager.beginTransaction()
                .replace(R.id.framelayout_steps_details, stepsFragment)
                .commit();


        playerFragment = new ExoPlayerFragment();

        if (steps.getVideoURL() != null && !steps.getVideoURL().isEmpty()) {
            playerFragment.setUrl(steps.getVideoURL());
            fLayout.setVisibility(View.VISIBLE);
        }
        else {
            fLayout.setVisibility(View.GONE);
        }
        fragmentManager.beginTransaction()
                .replace(R.id.framelayout_mediaplayer, playerFragment)
                .commit();


        stepsBrowserFragment = new StepsBrowserFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.framelayout_steps_browser, stepsBrowserFragment)
                .commit();

        stepsBrowserFragment.setDualPane(true);
        stepsBrowserFragment.setData(mId, mStepId, mStepsTotal);



    }

    public RecipeModel getRecipeDetails(){
        return mRecipeModel;
    }

    @Override
    public void itemSelected(int selected) {
        if(findViewById(R.id.linearLayout_recipe_instructions) != null) {
            refreshFragments(mRecipeModel.getSteps().get(selected - 1).getId());
        }else {
            Log.d(TAG, "Item clicked position " + selected + " mID= " + mId);
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(StepsModel.ID, mId);
            intent.putExtra(StepsModel.STEP_ID, mRecipeModel.getSteps().get(selected - 1).getId());
            intent.putExtra(StepsModel.TOTAL_STEPS, mStepsTotal);
            startActivity(intent);
        }
    }
}
