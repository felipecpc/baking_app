package android.example.com.bakingapp.connection;

import android.example.com.bakingapp.connection.data.RecipeDataService;
import android.example.com.bakingapp.model.RecipeModel;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by felipe on 10/06/17.
 */

public class ConnectionManager {

    public static int SUCCESS = 0;
    public static int FAIL = 1;

    private final String TAG = ConnectionManager.class.getSimpleName();
    public static String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static ConnectionManager connectionManager = null;
    private static MyExecutor myExecutor = null;

    private ConnectionManager(){

    }

    public static MyExecutor getMyExecutor(){
        return myExecutor;
    }

    public static ConnectionManager getInstance(){
        if (connectionManager==null) {
            connectionManager = new ConnectionManager();
            myExecutor = new MyExecutor();
        }

        return connectionManager;
    }

    public void request(String url, final ConnectionCallback callback){

        OkHttpClient.Builder okHttpClient_builder = new OkHttpClient().newBuilder();
        okHttpClient_builder.connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient_builder.readTimeout(20, TimeUnit.SECONDS);

        okHttpClient_builder.dispatcher(new Dispatcher(myExecutor));

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient_builder.build())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeDataService service = retrofit.create(RecipeDataService.class);
        service.listRecipe().enqueue(new Callback<List<RecipeModel>>() {
            @Override
            public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {
                myExecutor.setIdleState(true);
                callback.connectionResponse(SUCCESS,response.body());
            }

            @Override
            public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
                callback.connectionResponse(FAIL,t.getMessage());
            }
        });

    }

}
