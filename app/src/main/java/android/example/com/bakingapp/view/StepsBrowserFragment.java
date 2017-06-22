package android.example.com.bakingapp.view;

import android.example.com.bakingapp.R;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Created by felipe on 22/06/17.
 */

public class StepsBrowserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_browser, container, false);

        return rootView;
    }

    public void setData(int mRecipeId, int mStepId, int mStepsTotal) {

    }
}
