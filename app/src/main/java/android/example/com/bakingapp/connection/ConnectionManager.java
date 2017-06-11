package android.example.com.bakingapp.connection;

import android.example.com.bakingapp.connection.data.RecipeDataService;
import android.example.com.bakingapp.model.RecipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by felipe on 10/06/17.
 */

public class ConnectionManager {

    public static String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static ConnectionManager connectionManager = null;

    private ConnectionManager(){

    }

    public static ConnectionManager getInstance(){
        if (connectionManager==null) {
            connectionManager = new ConnectionManager();
        }

        return connectionManager;
    }

    public void request(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();

        RecipeDataService service = retrofit.create(RecipeDataService.class);
        Call<List<RecipeModel>> repos = service.listRecipe();

    }

}
