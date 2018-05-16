package ca.alexbalt.gameup2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeIntent);

        }

       if(id == R.id.action_notification){
           Intent notificationIntent = new Intent(MainActivity.this, NotificationActivity.class);
           startActivity(notificationIntent);

       }

       if(id == R.id.action_events){
           Intent eventsIntent = new Intent(MainActivity.this, EventActivity.class);
           startActivity(eventsIntent);
       }

       if(id == R.id.action_messages){
           Intent messagesIntent = new Intent(MainActivity.this, MessagesActivity.class);
           startActivity(messagesIntent);

       }

       if(id == R.id.action_account){
           Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
           startActivity(accountIntent);

       }
       return super.onOptionsItemSelected(item);
   }


   
}
