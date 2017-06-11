package android.example.com.bakingapp;

import android.example.com.bakingapp.connection.ConnectionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectionManager cManager = ConnectionManager.getInstance();
        cManager.request(ConnectionManager.URL);
    }
}
