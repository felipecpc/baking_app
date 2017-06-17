package android.example.com.bakingapp.database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by felipe on 17/06/17.
 */

public class BakingContentProvider extends ContentProvider{

    public static final int CODE_RECIPE = 100;

    public static final int CODE_INGREDIENTS = 200;
    public static final int CODE_INGREDIENTS_ID = 201;

    public static final int CODE_STEPS = 300;
    public static final int CODE_STEPS_ID = 301;


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BakingAppDBHelper mOpenHelper;


    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BakingContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, BakingContract.PATH_RECIPES, CODE_RECIPE);
        matcher.addURI(authority, BakingContract.PATH_INGREDIENTS, CODE_INGREDIENTS);
        matcher.addURI(authority, BakingContract.PATH_STEPS, CODE_STEPS);

        matcher.addURI(authority, BakingContract.PATH_INGREDIENTS + "/#", CODE_INGREDIENTS_ID);
        matcher.addURI(authority, BakingContract.PATH_STEPS + "/#", CODE_STEPS_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {

        mOpenHelper = new BakingAppDBHelper(getContext());
        return true;
    }



    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {

            case CODE_INGREDIENTS_ID: {

                String id = uri.getPathSegments().get(1);
                cursor = mOpenHelper.getReadableDatabase().query(
                        BakingContract.IngredientsEntry.TABLE_NAME,
                        null,
                        BakingContract.IngredientsEntry.COLUMN_RECIPE_ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        null,
                        null);
                break;
            }

            case CODE_STEPS_ID: {

                String id = uri.getPathSegments().get(1);
                cursor = mOpenHelper.getReadableDatabase().query(
                        BakingContract.StepsEntry.TABLE_NAME,
                        null,
                        BakingContract.StepsEntry.COLUMN_RECIPE_ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        null,
                        null);
                break;
            }

            case CODE_RECIPE: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        BakingContract.RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int removedRows = 0;
        switch (sUriMatcher.match(uri)) {

            case CODE_RECIPE:
                //String id = uri.getPathSegments().get(1);
                //removedRows = db.delete(TABLE_NAME, MovieGuideDbContract.MovieEntry.COLUMN_MOVIE_ID+"=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return removedRows;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in this app.");
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        long id;
        switch (match) {

            case CODE_RECIPE:
                id = db.insert(BakingContract.RecipeEntry.TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(BakingContract.BASE_CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case CODE_INGREDIENTS:
                id = db.insert(BakingContract.IngredientsEntry.TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(BakingContract.BASE_CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            case CODE_STEPS:
                id = db.insert(BakingContract.StepsEntry.TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(BakingContract.BASE_CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new RuntimeException("We are not implementing update in this app");
    }


    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
