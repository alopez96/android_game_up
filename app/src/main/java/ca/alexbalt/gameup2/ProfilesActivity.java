package ca.alexbalt.gameup2;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfilesActivity extends AppCompatActivity{
    private String key, uid, favGames, bio, name, email;
    private static final String TAG = "Profiles_activity";
    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mUsersReference, specificUserRef;
    private DatabaseReference mPostReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);


        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        if (uid == null) {
            throw new IllegalArgumentException("Must pass EVENT KEY VALUE");
        }
       // mFirebaseDatabase = FirebaseDatabase.getInstance();
       // mUsersReference = mFirebaseDatabase.getReference().child("users");
       // specificUserRef = mUsersReference.child(uid);
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
            Intent homeIntent = new Intent(ProfilesActivity.this, MainActivity.class);
            startActivity(homeIntent);
            ProfilesActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }

        if(id == R.id.action_events){
            Intent eventIntent = new Intent(ProfilesActivity.this, EventspgActivity.class);
            startActivity(eventIntent);
            ProfilesActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if(id == R.id.action_messages){
            Toast.makeText(getApplicationContext(),"messages page",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ProfilesActivity.this, MessagesActivity.class);
            if(i == null){
                Log.d(TAG, "intent null");
                Toast.makeText(getApplicationContext(), "intent null", Toast.LENGTH_SHORT).show();
            }
            else if(i != null){
                startActivity(i);
            }
            ProfilesActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if(id == R.id.action_account){
            Intent accountIntent = new Intent(ProfilesActivity.this, AccountActivity.class);
            startActivity(accountIntent);
            ProfilesActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }

        if (id == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
