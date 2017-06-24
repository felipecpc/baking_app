package android.example.com.bakingapp.view;


import android.content.Intent;
import android.example.com.bakingapp.database.DatabaseHelper;
import android.example.com.bakingapp.model.StepsModel;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.bakingapp.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsActivity extends AppCompatActivity {


    int mStepId;
    int mRecipeId;
    int mStepsTotal;
    StepsModel steps=null;

    StepDetailsFragment stepsFragment;
    StepsBrowserFragment stepsBrowserFragment;
    ExoPlayerFragment playerFragment;
    FragmentManager fragmentManager;

    private String STEP = "STEP";
    private String RECIPE = "RECIPE";
    private String TOTAL = "TOTAL";
    private String STEP_MODEL = "STEP_MODEL";

    public StepDetailsActivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_details_activity);
        FrameLayout fLayout = (FrameLayout) findViewById(R.id.framelayout_mediaplayer);


        // Only create new fragments when there is no previously saved state


        if(savedInstanceState!=null){
            mStepId=savedInstanceState.getInt(STEP);
            mRecipeId=savedInstanceState.getInt(RECIPE);
            mStepsTotal=savedInstanceState.getInt(TOTAL);
            steps = savedInstanceState.getParcelable(STEP_MODEL);

        }


        if(savedInstanceState == null) {

           fragmentManager = getSupportFragmentManager();

            stepsFragment = new StepDetailsFragment();



            if(getIntent().hasExtra(StepsModel.ID)){
                mRecipeId = getIntent().getIntExtra(StepsModel.ID,0);
                mStepId = getIntent().getIntExtra(StepsModel.STEP_ID,0);
                mStepsTotal = getIntent().getIntExtra(StepsModel.TOTAL_STEPS,0);
                DatabaseHelper test = new DatabaseHelper(this);
                steps = test.getRecipeStep(mRecipeId, mStepId);

            }

            stepsFragment.setData(steps);


            fragmentManager.beginTransaction()
                    .add(R.id.framelayout_steps_details, stepsFragment)
                    .commit();


            playerFragment = new ExoPlayerFragment();

            if(steps!=null && steps.getVideoURL()!=null && !steps.getVideoURL().isEmpty())
                playerFragment.setUrl(steps.getVideoURL());

            fragmentManager.beginTransaction()
                    .add(R.id.framelayout_mediaplayer, playerFragment)
                    .commit();




            stepsBrowserFragment = new StepsBrowserFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.framelayout_steps_browser, stepsBrowserFragment)
                    .commit();

            stepsBrowserFragment.setData(mRecipeId,mStepId,mStepsTotal);


        }

        if(steps!=null && steps.getVideoURL()!=null && !steps.getVideoURL().isEmpty()) {
            fLayout.setVisibility(View.VISIBLE);
        }
        else {
            fLayout.setVisibility(View.GONE);
        }

    }


    void refreshFragments(int stepId){
        mStepId = stepId;
        Intent intent = new Intent(this,StepDetailsActivity.class);
        intent.putExtra(StepsModel.ID,mRecipeId);
        intent.putExtra(StepsModel.STEP_ID,mStepId);
        intent.putExtra(StepsModel.TOTAL_STEPS,mStepsTotal);
        startActivity(intent);
        this.finish();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP,mStepId);
        outState.putInt(RECIPE,mRecipeId);
        outState.putInt(TOTAL,mStepsTotal);
        outState.putParcelable(STEP_MODEL,steps);
    }
}
