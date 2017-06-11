package android.example.com.bakingapp.connection.data;

import android.example.com.bakingapp.model.RecipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by felipe on 10/06/17.
 */

public interface RecipeDataService {
    @GET("baking.json")
    Call<List<RecipeModel>> listRecipe();
}
