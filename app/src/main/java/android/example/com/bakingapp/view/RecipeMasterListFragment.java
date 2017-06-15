package android.example.com.bakingapp.view;

import android.content.Context;
import android.example.com.bakingapp.adapters.RecipeAdapter;
import android.example.com.bakingapp.adapters.RecipeDetailsAdapter;
import android.example.com.bakingapp.model.IngredientsModel;
import android.example.com.bakingapp.model.RecipeModel;
import android.example.com.bakingapp.model.StepsModel;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeMasterListFragment extends Fragment {

    RecyclerView mRecipeRecyclerViewList = null;
    RecipeDetailsAdapter mRecipeDetailsAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_master_list,container,false);

        mRecipeRecyclerViewList = (RecyclerView) rootView.findViewById(R.id.rv_recipe_details);

        LinearLayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());
        mRecipeRecyclerViewList.setLayoutManager(layoutManager);

        mRecipeRecyclerViewList.setHasFixedSize(true);

        mRecipeDetailsAdapter = new RecipeDetailsAdapter();
        mRecipeRecyclerViewList.setAdapter(mRecipeDetailsAdapter);

        //Creating some fake data
        RecipeModel rfake = new RecipeModel();

        StepsModel sFake = new StepsModel();
        sFake.setDescription("Step 1 - Porra!!");
        List<StepsModel> listStepsModel = new ArrayList<StepsModel>();
        listStepsModel.add(sFake);

        IngredientsModel iFake = new IngredientsModel();
        iFake.setIngredient("Ingredients");
        List<IngredientsModel> listIngredientsModel = new ArrayList<IngredientsModel>();
        listIngredientsModel.add(iFake);


        rfake.setSteps(listStepsModel);
        rfake.setIngredients(listIngredientsModel);

        mRecipeDetailsAdapter.setData(rfake);

        return rootView;

    }


    public interface OnRecipeItemClickListener {
        void onRecipeItemClickListener(int position);
    }


    OnRecipeItemClickListener mCallback;

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


}
