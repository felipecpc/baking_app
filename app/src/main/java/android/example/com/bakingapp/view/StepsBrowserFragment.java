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

    Button buttonNext;
    Button buttonPrevious;

    int mStepId;
    int mRecipeId;
    int mStepsTotal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_browser, container, false);
        buttonNext = (Button) rootView.findViewById(R.id.button_next_step);
        buttonPrevious = (Button) rootView.findViewById(R.id.button_previous_step);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepDetailsActivity sDetailsActivity = (StepDetailsActivity) getActivity();
                if (mStepId<mStepsTotal){
                    mStepId++;
                    sDetailsActivity.changeStep(mStepId);
                }
                sDetailsActivity.refreshFragments();
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepDetailsActivity sDetailsActivity = (StepDetailsActivity) getActivity();
                if (mStepId>0){
                    mStepId--;
                    sDetailsActivity.changeStep(mStepId);
                }
                sDetailsActivity.refreshFragments();
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

    public void setData(int recipeId, int stepId, int stepsTotal) {
        mStepId=stepId;
        mRecipeId=recipeId;
        mStepsTotal=stepsTotal;
    }
}
