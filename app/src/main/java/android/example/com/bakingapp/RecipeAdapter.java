package android.example.com.bakingapp;

import android.content.Context;
import android.example.com.bakingapp.model.RecipeModel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by felipe on 13/06/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {

    ArrayList<RecipeModel> mRecipeItems = new ArrayList<RecipeModel>();
    RecipeSelectedInterface mRecipeSelectedInterface;

    public RecipeAdapter(RecipeSelectedInterface selectedInterface){
        mRecipeSelectedInterface = selectedInterface;
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        ImageView imgView = holder.recipeImage;
        if(!mRecipeItems.get(position).getImage().isEmpty())
            Picasso.with(imgView.getContext()).load(mRecipeItems.get(position).getImage())
                .fit()
                .into(imgView);
        holder.recipeTitle.setText(mRecipeItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mRecipeItems.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView recipeImage;
        TextView recipeTitle;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeImage = (ImageView) itemView.findViewById(R.id.iv_recipe_image);
            recipeTitle = (TextView) itemView.findViewById(R.id.textView_recipe_title);
            recipeImage.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mRecipeSelectedInterface.recipeSelected(clickedPosition);
        }
    }

    public void setRecipes(ArrayList<RecipeModel> recipeItems){
        mRecipeItems = recipeItems;
        notifyDataSetChanged();
    }

}
