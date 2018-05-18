package ca.alexbalt.gameup2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "main_activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
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
            Intent homeIntent = new Intent(AccountActivity.this, HomeActivity.class);
            startActivity(homeIntent);

        }

        if(id == R.id.action_notification){
            Intent notificationIntent = new Intent(AccountActivity.this, NotificationActivity.class);
            startActivity(notificationIntent);

        }

        if(id == R.id.action_events){
            Intent eventsIntent = new Intent(AccountActivity.this, EventActivity.class);
            startActivity(eventsIntent);
        }

        if(id == R.id.action_messages){
            Toast.makeText(getApplicationContext(),"messages page",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AccountActivity.this, MessagesActivity.class);
            if(i == null){
                Log.d(TAG, "intent null");
                Toast.makeText(getApplicationContext(), "intent null", Toast.LENGTH_SHORT).show();
            }
            else if(i != null){
                startActivity(i);
            }

        }

        if(id == R.id.action_account){
            Intent accountIntent = new Intent(AccountActivity.this, AccountActivity.class);
            startActivity(accountIntent);

        }
        return super.onOptionsItemSelected(item);
    }

}
