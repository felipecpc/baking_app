package android.example.com.bakingapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by felipe on 17/06/17.
 */

public class BakingContract {

    public static final String CONTENT_AUTHORITY = "android.eample.com.bakingapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RECIPES = "recipes";
    public static final String PATH_INGREDIENTS = "ingredients";
    public static final String PATH_STEPS = "steps";


    /* Inner class that defines the table contents of the weather table */
    public static final class RecipeEntry implements BaseColumns {

        /* The base CONTENT_URI used to query the Weather table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_RECIPES)
                .build();

        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVING= "serving";
        public static final String COLUMN_IMAGE = "image";

        public static Uri buildWeatherUriWithID(String id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id)
                    .build();
        }

    }

    public static class IngredientsEntry implements BaseColumns{

        /* The base CONTENT_URI used to query the Weather table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_INGREDIENTS)
                .build();


        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_QTD = "qtd";
        public static final String COLUMN_MEASURE= "measure";
        public static final String COLUMN_INGREDIENT = "ingredient";

        public static Uri buildWeatherUriWithID(String id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id)
                    .build();
        }

    }

    public static class StepsEntry implements BaseColumns{

        /* The base CONTENT_URI used to query the Weather table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_STEPS)
                .build();


        public static final String TABLE_NAME = "steps";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_FULL_DESCRIPTION= "full_description";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_THUMBNAIL= "thumbnail";


        public static Uri buildWeatherUriWithID(String id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id)
                    .build();
        }

    }
}
