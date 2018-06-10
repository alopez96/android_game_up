package ca.alexbalt.gameup2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";


    private Button postButton;
    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mEventsReference;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mUserReference;
    public static final int RC_SIGN_IN = 1;     ///request code
    private String mUsername, mUserEmail, bio, favGames, uid, key;
    private ArrayList<String> friends = new ArrayList<>();
    private ArrayList<String> eventsJoined = new ArrayList<>();
    private ArrayList<String> eventsCreated = new ArrayList<>();


    ListView listViewEvents;
    List<event> eventList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postButton = findViewById(R.id.post_button);




        listViewEvents =findViewById(R.id.listViewEvents);
        //TextView text = findViewById(R.id.nothing_tv);
        //text.setVisibility(View.INVISIBLE);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");
        eventList = new ArrayList<>();
       // bio = "";
        //favGames = "";
        uid = "";
        //friends.add("");
        //eventsJoined.add("");

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostActivity();
            }
        });


        mFirebaseAuth = FirebaseAuth.getInstance();
        mUserReference = mFirebaseDatabase.getReference().child("users");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //user is signed in
                    Toast.makeText(MainActivity.this,"Now signed in!", Toast.LENGTH_SHORT).show();
                    mUsername = user.getDisplayName();
                    mUserEmail = user.getEmail();
                    uid = user.getUid();
                    key = mUserReference.push().getKey();

                    if (user.getUid() != null){
                        User currentUser = new User(mUsername, mUserEmail, bio, favGames, friends, eventsJoined, eventsCreated, uid, key);
//                        mUserReference.child(uid).setValue(currentUser);
                    }
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
    protected void onStart(){
        super.onStart();
        mEventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    event Event = snapshot.getValue(event.class);
                    eventList.add(Event);
                }
                EventList adapter = new EventList(MainActivity.this, eventList);
                listViewEvents.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



        // Set an OnItemClickListener for each of the list items
        final Context context = this;
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                event selected = eventList.get(position);
                Intent detailIntent = new Intent(MainActivity.this, EventActivity.class);
                detailIntent.putExtra("data", selected.id);
                Toast.makeText(MainActivity.this, "event key " + selected.id, Toast.LENGTH_SHORT).show();
                startActivity(detailIntent);
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(homeIntent);
            MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }
        if (id == R.id.action_events) {
            Intent eventIntent = new Intent(MainActivity.this, EventspgActivity.class);
            startActivity(eventIntent);
            MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if (id == R.id.action_messages) {
            Toast.makeText(getApplicationContext(), "messages page", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, MessagesActivity.class);
            if (i == null) {
                Log.d(TAG, "intent null");
                Toast.makeText(getApplicationContext(), "intent null", Toast.LENGTH_SHORT).show();
            } else if (i != null) {
                startActivity(i);
            }
            MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
        if (id == R.id.action_account) {
            Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(accountIntent);
            MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
        if (id == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openPostActivity(){
        Intent i = new Intent(this, PostActivity.class);
        startActivity(i);

    }

}
