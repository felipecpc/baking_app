package android.example.com.bakingapp.adapters;

import android.example.com.bakingapp.R;
import android.example.com.bakingapp.model.IngredientsModel;
import android.example.com.bakingapp.model.RecipeModel;
import android.example.com.bakingapp.model.StepsModel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felipe on 15/06/17.
 */

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RecipeModel mRecipeModel;
    List<StepsModel> steps = new ArrayList<>();
    ItemSelectedInterface mClickListener;

    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_NORMAL = 1;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            View normalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_igredients, null);
            return new RecipeIngredientsViewHolder(normalView); // view holder for normal items
        } else {
            View headerRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_steps, null);
            return new RecipeStepsViewHolder(headerRow); // view holder for header items
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);

        if (itemType == ITEM_TYPE_NORMAL) {
            ((RecipeStepsViewHolder)holder).setText(String.valueOf(position),steps.get(position-1).getShortDescription());

        } else if (itemType == ITEM_TYPE_HEADER) {
            String ingredients="";
            for(IngredientsModel ingredModel:  mRecipeModel.getIngredients()){
                ingredients = ingredients + ingredModel.getQuantity() + " " + ingredModel.getMeasure() + " " + ingredModel.getIngredient() + "\n";
            }
            ((RecipeIngredientsViewHolder)holder).setText(ingredients);
        }
    }


    public void setData(RecipeModel recipeItems, ItemSelectedInterface event){
        mRecipeModel = recipeItems;
        steps = recipeItems.getSteps();
        notifyDataSetChanged();
        mClickListener = event;
    }



    @Override
    public int getItemViewType(int position) {
        if (position==0) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }



    @Override
    public int getItemCount() {
        if (steps.isEmpty())
            return 0;
        else
            return steps.size()+1;
    }

    public class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvIngredientsTitle;

        public RecipeIngredientsViewHolder(View view) {
            super(view);

            tvIngredientsTitle = (TextView) view.findViewById(R.id.textView_ingredients_details);
        }

        public void setText(String text){
            tvIngredientsTitle.setText(text);
        }

    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvStepDescription;
        private TextView tvStepCounter;

        public RecipeStepsViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    mClickListener.itemSelected(clickedPosition);
                }
            });
            tvStepDescription = (TextView) view.findViewById(R.id.textView_step_description);
            tvStepCounter = (TextView) view.findViewById(R.id.textView_step_counter);
        }

        public void setText(String position, String text){
            tvStepDescription.setText(text);
            tvStepCounter.setText(position);
        }

    }

}
