package android.example.com.bakingapp.view;


import android.example.com.bakingapp.model.StepsModel;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.bakingapp.R;

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

            //Fake data
            StepsModel fake = new StepsModel();
            fake.setShortDescription("Short Description");
            fake.setDescription("This is the full description porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra porra ");
            stepsFragment.setData(fake);

            FragmentManager fmStepDetails = getSupportFragmentManager();

            fmStepDetails.beginTransaction()
                    .add(R.id.framelayout_steps_details, stepsFragment)
                    .commit();



        }
    }

}
