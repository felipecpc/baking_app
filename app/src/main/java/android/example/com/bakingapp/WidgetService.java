package android.example.com.bakingapp;

/**
 * Created by felipe on 24/06/17.
 */
import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new RecipeWidgetViewFactory(this.getApplicationContext(),
                intent));
    }
}

