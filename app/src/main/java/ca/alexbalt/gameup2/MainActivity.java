package ca.alexbalt.gameup2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;
    public static final int RC_SIGN_IN = 123;     ///request code


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in
                    Toast.makeText(MainActivity.this,"Now signed in!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //user is signed out
                    startActivityForResult(
                            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


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
           Toast.makeText(getApplicationContext(),"messages page",Toast.LENGTH_SHORT).show();
           Intent i = new Intent(MainActivity.this, MessagesActivity.class);
           if(i == null){
               Log.d(TAG, "intent null");
               Toast.makeText(getApplicationContext(), "intent null", Toast.LENGTH_SHORT).show();
           }
           else if(i != null){
               startActivity(i);
           }

       }

       if(id == R.id.action_account){
           Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
           startActivity(accountIntent);

       }
       return super.onOptionsItemSelected(item);
   }






    @Override
    protected void onResume(){
        super.onResume();
        //ad listener
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    protected void onPause(){
        super.onPause();
        //remove listener
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

   
}
