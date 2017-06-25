package android.example.com.bakingapp.adapters;

import android.content.Context;
import android.example.com.bakingapp.R;
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

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    ArrayList<RecipeModel> mRecipeItems = new ArrayList<RecipeModel>();
    ItemSelectedInterface mRecipeSelectedInterface;

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_NORMAL = 1;

    public RecipeAdapter(ItemSelectedInterface selectedInterface){
        mRecipeSelectedInterface = selectedInterface;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder vh;
        View view;

        if (viewType == VIEW_TYPE_EMPTY) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.empty_list, parent, false);
            vh = new EmptyHolder(view);
        }else {

            int layoutIdForListItem = R.layout.recipe_item;

            boolean shouldAttachToParentImmediately = false;

            view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
            vh = new RecipeViewHolder(view);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final int itemType = getItemViewType(position);

        if (itemType == VIEW_NORMAL) {

            ImageView imgView = ((RecipeViewHolder) holder).recipeImage;
            if (!mRecipeItems.get(position).getImage().isEmpty())
                Picasso.with(imgView.getContext()).load(mRecipeItems.get(position).getImage())
                        .fit()
                        .into(imgView);
            ((RecipeViewHolder) holder).recipeTitle.setText(mRecipeItems.get(position).getName());
        }
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView recipeImage;
        TextView recipeTitle;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeImage = (ImageView) itemView.findViewById(R.id.iv_embarassed);
            recipeTitle = (TextView) itemView.findViewById(R.id.textView_recipe_title);
            recipeImage.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mRecipeSelectedInterface.itemSelected(clickedPosition);
        }
    }

    public class EmptyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public EmptyHolder(View itemView) {
            super(itemView);


        }

        @Override
        public void onClick(View v) {

        }
    }


    public void setRecipes(ArrayList<RecipeModel> recipeItems){
        mRecipeItems = recipeItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mRecipeItems.size() == 0){
            return 1;
        }else {
            return mRecipeItems.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRecipeItems.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }else{
            return VIEW_NORMAL;
        }
    }


}
