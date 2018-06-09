package ca.alexbalt.gameup2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class EventspgActivity extends AppCompatActivity {
    private static final String TAG = "main_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_home){
            Intent homeIntent = new Intent(EventspgActivity.this, MainActivity.class);
            startActivity(homeIntent);
            EventspgActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }

        if(id == R.id.action_events){
            Intent eventIntent = new Intent(EventspgActivity.this, EventspgActivity.class);
            startActivity(eventIntent);
            EventspgActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }


        if(id == R.id.action_messages){
            Toast.makeText(getApplicationContext(),"messages page",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EventspgActivity.this, MessagesActivity.class);
            if(i == null){
                Log.d(TAG, "intent null");
                Toast.makeText(getApplicationContext(), "intent null", Toast.LENGTH_SHORT).show();
            }
            else if(i != null){
                startActivity(i);
            }
            EventspgActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }

        if(id == R.id.action_account){
            Intent accountIntent = new Intent(EventspgActivity.this, AccountActivity.class);
            startActivity(accountIntent);
            EventspgActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }
        return super.onOptionsItemSelected(item);
    }
}
