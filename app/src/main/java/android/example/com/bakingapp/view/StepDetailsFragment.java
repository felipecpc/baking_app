package android.example.com.bakingapp.view;

import android.example.com.bakingapp.R;
import android.example.com.bakingapp.model.StepsModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by felipe on 19/06/17.
 */

public class StepDetailsFragment extends Fragment {

    private static final String STEP_DETAILS = "STEP_DETAILS" ;
    private TextView tvStepTitle;
    private TextView tvStepShortDescription;
    private TextView tvStepDescription;

    StepsModel mStepModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_details, container, false);
        tvStepTitle = (TextView) rootView.findViewById(R.id.textView_step_count_details);
        tvStepShortDescription= (TextView) rootView.findViewById(R.id.textView_step_short_description);
        tvStepDescription=(TextView) rootView.findViewById(R.id.textView_step_description);

        if(savedInstanceState!=null){
            mStepModel = (StepsModel) savedInstanceState.getParcelable(STEP_DETAILS);
        }

        if(mStepModel!=null) {
            tvStepTitle.setText("Step " +(mStepModel.getId()+1));
            tvStepShortDescription.setText(mStepModel.getShortDescription());
            tvStepDescription.setText(mStepModel.getDescription());
        }else{
            tvStepTitle.setText(getResources().getString(R.string.step_not_available));
            tvStepShortDescription.setText(getResources().getString(R.string.step_not_available));
            tvStepDescription.setText(getResources().getString(R.string.step_missing));
        }


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_DETAILS,mStepModel);
    }

    public void setData(StepsModel stepModel){
        mStepModel = stepModel;
    }

}
