package ca.alexbalt.gameup2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private Button postButton;
    private Button filterBttn;
    private static final String TAG = "main_activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        postButton = findViewById(R.id.post_button);
        filterBttn = findViewById(R.id.filter_home);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostActivity();
            }
        });
        filterBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterActivity();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_home){
            Intent homeIntent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(homeIntent);

        }

        if(id == R.id.action_notification){
            Intent notificationIntent = new Intent(HomeActivity.this, NotificationActivity.class);
            startActivity(notificationIntent);

        }

        if(id == R.id.action_events){
            Intent eventsIntent = new Intent(HomeActivity.this, EventActivity.class);
            startActivity(eventsIntent);
        }

        if(id == R.id.action_messages){
            Toast.makeText(getApplicationContext(),"messages page",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(HomeActivity.this, MessagesActivity.class);
            if(i == null){
                Log.d(TAG, "intent null");
                Toast.makeText(getApplicationContext(), "intent null", Toast.LENGTH_SHORT).show();
            }
            else if(i != null){
                startActivity(i);
            }

        }

        if(id == R.id.action_account){
            Intent accountIntent = new Intent(HomeActivity.this, AccountActivity.class);
            startActivity(accountIntent);

        }
        return super.onOptionsItemSelected(item);
    }


    public void openPostActivity(){
        Intent i = new Intent(this, PostActivity.class);
        startActivity(i);

    }

    public void openFilterActivity(){
        Intent i = new Intent(this, FilterOptions.class);
        startActivity(i);
    }




}
