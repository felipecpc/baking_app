package android.example.com.bakingapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.example.com.bakingapp.connection.ConnectionCallback;
import android.example.com.bakingapp.connection.ConnectionManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);

        ConnectionManager cManager = ConnectionManager.getInstance();
        cManager.request(ConnectionManager.URL, new ConnectionCallback() {
            @Override
            public void connectionResponse(int status, Object data) {
                dialog.dismiss();

                if(status == ConnectionManager.FAIL){
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage((String) data);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }else{

                }

            }
        });


    }
}
