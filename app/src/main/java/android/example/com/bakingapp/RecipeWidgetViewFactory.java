package android.example.com.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.adapters.RecipeDetailsAdapter;
import android.example.com.bakingapp.database.DatabaseHelper;
import android.example.com.bakingapp.model.IngredientsModel;
import android.example.com.bakingapp.model.RecipeModel;
import android.example.com.bakingapp.model.StepsModel;
import android.example.com.bakingapp.view.RecipeInstructionsActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by felipe on 24/06/17.
 */

public class RecipeWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<RecipeModel> items;
    private Context ctxt=null;
    private int appWidgetId;
    private DatabaseHelper mDBHelper;
    private int recipe_id=0;

    public RecipeWidgetViewFactory(Context ctxt, Intent intent) {
        this.ctxt=ctxt;

        mDBHelper  = new DatabaseHelper(ctxt);
        items = mDBHelper.getRecipes();

        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDestroy() {
        // no-op
    }


    @Override
    public int getCount() {
        return(items.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                R.layout.widget_item);

        row.setTextViewText(R.id.textView_recipe, items.get(position).getName());


        Intent i=new Intent();
        i.putExtra(StepsModel.ID,  items.get(position).getId());
        i.putExtra(RecipeInstructionsActivity.RECIPE_NAME,  items.get(position).getName());
        row.setOnClickFillInIntent(R.id.textView_recipe, i);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }
}
