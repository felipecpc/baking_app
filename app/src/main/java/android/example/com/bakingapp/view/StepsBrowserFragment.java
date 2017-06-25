package android.example.com.bakingapp.view;

import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.model.StepsModel;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Created by felipe on 22/06/17.
 */

public class StepsBrowserFragment extends Fragment {

    private Button buttonNext;
    private Button buttonPrevious;

    private int mStepId;
    private int mRecipeId;
    private int mStepsTotal;
    private boolean dualPane=false;

    private final String STEP = "STEP";
    private final String RECIPE = "RECIPE";
    private final String TOTAL = "TOTAL";
    private final String DUAL_PANE = "DUAL_PANE";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_browser, container, false);
        buttonNext = (Button) rootView.findViewById(R.id.button_next_step);
        buttonPrevious = (Button) rootView.findViewById(R.id.button_previous_step);

        if(savedInstanceState!=null){
            mStepId=savedInstanceState.getInt(STEP);
            mRecipeId=savedInstanceState.getInt(RECIPE);
            mStepsTotal=savedInstanceState.getInt(TOTAL);
            dualPane=savedInstanceState.getBoolean(DUAL_PANE);
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStepId<mStepsTotal){
                    mStepId++;
                }
                refreshFragments(mStepId);
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStepId>0){
                    mStepId--;
                }
                refreshFragments(mStepId);
            }
        });

        if (mStepId==0){
            buttonPrevious.setEnabled(false);
        }else{
            buttonPrevious.setEnabled(true);
        }

        if(mStepId<mStepsTotal-1){
            buttonNext.setEnabled(true);
        }
        else{
            buttonNext.setEnabled(false);
        }

        return rootView;
    }


    private void refreshFragments(int mStepId){

        if (dualPane){
            RecipeInstructionsActivity sIntructionActivity = (RecipeInstructionsActivity) getActivity();
            sIntructionActivity.refreshFragments(mStepId);
        }else{
            StepDetailsActivity sDetailsActivity = (StepDetailsActivity) getActivity();
            sDetailsActivity.refreshFragments(mStepId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STEP,mStepId);
        outState.putInt(RECIPE,mRecipeId);
        outState.putInt(TOTAL,mStepsTotal);
        outState.putBoolean(DUAL_PANE,dualPane);

    }

    public void setData(int recipeId, int stepId, int stepsTotal) {
        mStepId=stepId;
        mRecipeId=recipeId;
        mStepsTotal=stepsTotal;
    }

    public void setDualPane(boolean dualPane) {
        this.dualPane = dualPane;
    }
}
