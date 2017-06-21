package android.example.com.bakingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.example.com.bakingapp.model.IngredientsModel;
import android.example.com.bakingapp.model.RecipeModel;
import android.example.com.bakingapp.model.StepsModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felipe on 17/06/17.
 */

public class DatabaseHelper {

    private static String TAG = DatabaseHelper.class.getSimpleName();

    Context mContext;

    public DatabaseHelper(Context ctx){
        mContext = ctx;
    }

    public void addRecipe(RecipeModel recipeModel){

        try {
            ContentValues cv = new ContentValues();
            cv.put(BakingContract.RecipeEntry.COLUMN_ID,recipeModel.getId());
            cv.put(BakingContract.RecipeEntry.COLUMN_IMAGE,recipeModel.getImage());
            cv.put(BakingContract.RecipeEntry.COLUMN_NAME,recipeModel.getName());
            cv.put(BakingContract.RecipeEntry.COLUMN_SERVING,recipeModel.getServings());
            mContext.getContentResolver().insert(BakingContract.RecipeEntry.CONTENT_URI,cv);


            List<IngredientsModel> ingData = recipeModel.getIngredients();
            for (IngredientsModel ingredients : ingData) {
                ContentValues cv2 = new ContentValues();
                cv2.put(BakingContract.IngredientsEntry.COLUMN_RECIPE_ID, recipeModel.getId());
                cv2.put(BakingContract.IngredientsEntry.COLUMN_INGREDIENT, ingredients.getIngredient());
                cv2.put(BakingContract.IngredientsEntry.COLUMN_MEASURE, ingredients.getMeasure());
                cv2.put(BakingContract.IngredientsEntry.COLUMN_QTD, String.valueOf(ingredients.getQuantity()));
                mContext.getContentResolver().insert(BakingContract.IngredientsEntry.CONTENT_URI, cv2);
            }

            List<StepsModel> stepsData = recipeModel.getSteps();
            for (StepsModel steps : stepsData) {
                ContentValues cv2 = new ContentValues();
                cv2.put(BakingContract.StepsEntry.COLUMN_RECIPE_ID, recipeModel.getId());
                cv2.put(BakingContract.StepsEntry.COLUMN_FULL_DESCRIPTION, steps.getDescription());
                cv2.put(BakingContract.StepsEntry.COLUMN_DESCRIPTION, steps.getShortDescription());
                cv2.put(BakingContract.StepsEntry.COLUMN_THUMBNAIL, steps.getThumbnailURL());
                cv2.put(BakingContract.StepsEntry.COLUMN_VIDEO, steps.getVideoURL());
                mContext.getContentResolver().insert(BakingContract.StepsEntry.CONTENT_URI, cv2);
            }
        }catch (Exception e){

        }

    }

    public ArrayList<RecipeModel> getRecipes(){
        ArrayList<RecipeModel> recipeModelList = new ArrayList<>();

        Cursor cursor = mContext.getContentResolver().query(BakingContract.RecipeEntry.CONTENT_URI,null, null, null, null);
        Log.d(TAG,"Cursor " + cursor.getCount());
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                RecipeModel recipeModel = new RecipeModel();
                recipeModel.setName(cursor.getString(cursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_NAME)));
                recipeModel.setImage(cursor.getString(cursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_IMAGE)));
                recipeModel.setServings(cursor.getString(cursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_SERVING)));
                recipeModelList.add(recipeModel);
            }
        }

        return recipeModelList;
    }

    public RecipeModel getRecipeDetails(int id){
        RecipeModel recipeModel = new RecipeModel();
        ArrayList<IngredientsModel> ingredientsList = new ArrayList<>();
        ArrayList<StepsModel> stepList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(BakingContract.IngredientsEntry.buildWeatherUriWithID(String.valueOf(id)),null, null, null, null);
        Log.d(TAG,"Cursor " + cursor.getCount());

        if (cursor!=null) {
            while (cursor.moveToNext()) {
                IngredientsModel ingredientsModel = new IngredientsModel();
                ingredientsModel.setIngredient(cursor.getString(cursor.getColumnIndex(BakingContract.IngredientsEntry.COLUMN_INGREDIENT)));
                ingredientsModel.setMeasure(cursor.getString(cursor.getColumnIndex(BakingContract.IngredientsEntry.COLUMN_MEASURE)));
                ingredientsModel.setQuantity(Float.parseFloat(cursor.getString(cursor.getColumnIndex(BakingContract.IngredientsEntry.COLUMN_QTD))));
                ingredientsList.add(ingredientsModel);
            }
        }

        Cursor cursor2 = mContext.getContentResolver().query(BakingContract.StepsEntry.buildWeatherUriWithID(String.valueOf(id)),null, null, null, null);
        Log.d(TAG,"Cursor2 " + cursor2.getCount());
        if (cursor2!=null) {
            while (cursor2.moveToNext()) {
                StepsModel stepsModel = new StepsModel(0,
                        cursor2.getString(cursor2.getColumnIndex(BakingContract.StepsEntry.COLUMN_DESCRIPTION)),
                        cursor2.getString(cursor2.getColumnIndex(BakingContract.StepsEntry.COLUMN_FULL_DESCRIPTION)),
                        cursor2.getString(cursor2.getColumnIndex(BakingContract.StepsEntry.COLUMN_THUMBNAIL)),
                        cursor2.getString(cursor2.getColumnIndex(BakingContract.StepsEntry.COLUMN_VIDEO)));
                stepList.add(stepsModel);
            }

        }

        recipeModel.setIngredients(ingredientsList);
        recipeModel.setSteps(stepList);
        return recipeModel;
    }

    public void removeRecipe(RecipeModel recipeModel){

    }
}
