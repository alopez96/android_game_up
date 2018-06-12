package ca.alexbalt.gameup2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.ArrayList;


public class EventspgActivity extends AppCompatActivity {
    private static final String TAG = "main_activity";
    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mUsersReference, specificUserRef;
    private FirebaseAuth mFirebaseAuth;
    private String mUsername, mUserEmail, bio, favGames, uid;
    private ArrayList<String> eventsJoined = new ArrayList<>();
    private ArrayList<String> eventsCreated = new ArrayList<>();
    ListView JoinedList, CreatedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventspage);

        CreatedList = findViewById(R.id.listViewCreated);
        JoinedList = findViewById(R.id.listViewJoined);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersReference = mFirebaseDatabase.getReference().child("users");
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUsername = user.getDisplayName();
        mUserEmail = user.getEmail();
        uid = user.getUid();
        specificUserRef = mUsersReference.child(uid);
    }

    @Override
    protected void onStart() {
        super.onStart();

        specificUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                eventsJoined = user.eventsJoined;
                eventsCreated = user.eventsCreated;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventspgActivity.this, android.R.layout.simple_list_item_1, eventsJoined);
                JoinedList.setAdapter(adapter);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EventspgActivity.this, android.R.layout.simple_list_item_1, eventsCreated);
                CreatedList.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

        if (id == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
