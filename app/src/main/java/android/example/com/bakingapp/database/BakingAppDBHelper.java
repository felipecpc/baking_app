package android.example.com.bakingapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.example.com.bakingapp.database.BakingContract;

/**
 * Created by felipe on 17/06/17.
 */

public class BakingAppDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "baking.db";

    private static final int DATABASE_VERSION = 1;

    public BakingAppDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_RECIPE_TABLE = "CREATE TABLE " + BakingContract.RecipeEntry.TABLE_NAME + " (" +
                BakingContract.RecipeEntry._ID + " INTEGER PRIMARY KEY," +
                BakingContract.RecipeEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                BakingContract.RecipeEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                BakingContract.RecipeEntry.COLUMN_SERVING + " TEXT NOT NULL);";

        final String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + BakingContract.IngredientsEntry.TABLE_NAME + " (" +
                BakingContract.IngredientsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BakingContract.IngredientsEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                BakingContract.IngredientsEntry.COLUMN_INGREDIENT + " TEXT NOT NULL, " +
                BakingContract.IngredientsEntry.COLUMN_MEASURE + " TEXT NOT NULL," +
                BakingContract.IngredientsEntry.COLUMN_QTD + " TEXT NOT NULL);";


        final String SQL_CREATE_STEPS_TABLE = "CREATE TABLE " + BakingContract.StepsEntry.TABLE_NAME + " (" +
                BakingContract.StepsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BakingContract.StepsEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                BakingContract.StepsEntry.COLUMN_STEP_ID + " INTEGER NOT NULL, " +
                BakingContract.StepsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                BakingContract.StepsEntry.COLUMN_FULL_DESCRIPTION + " TEXT NOT NULL, " +
                BakingContract.StepsEntry.COLUMN_THUMBNAIL + " TEXT NOT NULL, " +
                BakingContract.StepsEntry.COLUMN_VIDEO + " TEXT NOT NULL);";


        sqLiteDatabase.execSQL(SQL_CREATE_RECIPE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_STEPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BakingContract.RecipeEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BakingContract.IngredientsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BakingContract.StepsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


}
