package android.example.com.bakingapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.database.DatabaseHelper;
import android.example.com.bakingapp.model.IngredientsModel;
import android.example.com.bakingapp.model.RecipeModel;
import android.example.com.bakingapp.model.StepsModel;
import android.example.com.bakingapp.view.RecipeInstructionsActivity;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    private static int recipe_id = 0;
    private static String recipe_name = "";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = "Baking app";
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        Intent svcIntent=new Intent(context, WidgetService.class);

        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.appwidget_recipe_listview,
                svcIntent);
        if(recipe_id!=0) {
            DatabaseHelper mDBHelper  = new DatabaseHelper(context);
            RecipeModel model = mDBHelper.getRecipeDetails(recipe_id);

            String ingredients=recipe_name+"\n";
            for(IngredientsModel ingredModel:  model.getIngredients()){
                ingredients = ingredients + ingredModel.getQuantity() + " " + ingredModel.getMeasure() + " " + ingredModel.getIngredient() + "\n";
            }
            views.setTextViewText(R.id.textview_widget_ingredients,ingredients);
            views.setViewVisibility(R.id.appwidget_recipe_listview, View.GONE);
            views.setViewVisibility(R.id.textview_widget_ingredients,View.VISIBLE);
        }else{
            views.setViewVisibility(R.id.appwidget_recipe_listview, View.VISIBLE);
            views.setViewVisibility(R.id.textview_widget_ingredients,View.GONE);
        }


        Intent clickIntent=new Intent(context, RecipeInstructionsActivity.class);
        PendingIntent clickPI=PendingIntent
                .getActivity(context, 0,
                        clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.appwidget_recipe_listview, clickPI);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra(RecipeInstructionsActivity.RECIPE)) {
            recipe_id = intent.getIntExtra(RecipeInstructionsActivity.RECIPE,0);
            recipe_name = intent.getStringExtra(StepsModel.RECIPE_NAME);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

