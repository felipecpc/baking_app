package android.example.com.bakingapp.view;


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


    public StepDetailsActivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_details_activity);




        // Only create new fragments when there is no previously saved state
        if(savedInstanceState == null) {

            // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
            // Use setListindex(int index) to set the list index for all BodyPartFragments

            // Create a new head BodyPartFragment
            ExoPlayerFragment playerFragment = new ExoPlayerFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.framelayout_mediaplayer, playerFragment)
                    .commit();

            StepDetailsFragment stepsFragment = new StepDetailsFragment();

            StepsModel steps=null;
            if(getIntent().hasExtra(StepsModel.DATA)){
                steps = getIntent().getParcelableExtra(StepsModel.DATA);
            }

            stepsFragment.setData(steps);

            //FragmentManager fmStepDetails = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.framelayout_steps_details, stepsFragment)
                    .commit();


            //FragmentManager fmStepBrowser = getSupportFragmentManager();
            StepsBrowserFragment stepsBrowserFragment = new StepsBrowserFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.framelayout_steps_browser, stepsBrowserFragment)
                    .commit();


        }


    }





}
