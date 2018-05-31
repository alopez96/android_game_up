package ca.alexbalt.gameup2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";


    private Button postButton;
    private Button filterBttn;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;
    public static final int RC_SIGN_IN = 1;     ///request code


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postButton = findViewById(R.id.post_button);
        filterBttn = findViewById(R.id.filter_home);

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


    //supposed to exit app when the back button is pressed on login
    //but doesn't work rn
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                //signed in succeeded, set up UI
                Toast.makeText(MainActivity.this, "signed in!", Toast.LENGTH_SHORT).show();
            } else if(requestCode == RESULT_CANCELED){
                //sign in was cancelled, finish activity
                Toast.makeText(MainActivity.this, "sign in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
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
            Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
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
       if (id == R.id.sign_out_menu){
           AuthUI.getInstance().signOut(this);
           return true;
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
