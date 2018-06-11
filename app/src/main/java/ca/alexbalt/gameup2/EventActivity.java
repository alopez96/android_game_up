package ca.alexbalt.gameup2;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";

    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mEventsReference, specificEventRef;
    private DatabaseReference mUsersReference, specificUserRef;
    private DatabaseReference mPostReference;
    private FirebaseAuth mFirebaseAuth;
    private String mUsername, key, userkey;
    TextView titleTextView, consoleTextView, gameTextView;
    TextView dateTextView, creatorTextView, descTextView;
    ListView joinedListView;
    List<String> listString;
    private Button joinButton, editButton;
    private String userEmail, bio, favGames, uid;
    private ArrayList<String> friends = new ArrayList<>();
    private ArrayList<String> eventsJoined = new ArrayList<>();
    private event Event;
    private User thisUser;
    private boolean exist = false;
    List<User> userList;
    public User userData;
    List<String> xList;
    int sizeOfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        titleTextView = findViewById(R.id.titleTV);
        consoleTextView = findViewById(R.id.consoleTV);
        gameTextView = findViewById(R.id.gameTV);
        dateTextView = findViewById(R.id.dateTV);
        creatorTextView = findViewById(R.id.creatorTV);
        descTextView = findViewById(R.id.descTV);
        joinedListView = findViewById(R.id.joinedLV);
        listString = new ArrayList<>();
        userList = new ArrayList<>();
        joinButton = findViewById(R.id.joinB);
        editButton = findViewById(R.id.editB);
        xList = new ArrayList<>();

        //ListView list = findViewById(R.id.event_post);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsReference = mFirebaseDatabase.getReference().child("events");
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUsername = user.getDisplayName();
        userEmail = user.getEmail();
        bio = "";
        favGames = "";
        uid = user.getUid();

        Intent intent = getIntent();
        key = intent.getStringExtra("data");
        if (key == null) {
            throw new IllegalArgumentException("Must pass EVENT KEY VALUE");
        }
        specificEventRef = mEventsReference.child(key);
        mUsersReference = mFirebaseDatabase.getReference().child("users");
        specificUserRef = mUsersReference.child(uid);

        specificEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event = dataSnapshot.getValue(event.class);
                titleTextView.setText(Event.title);
                consoleTextView.setText(Event.console);
                gameTextView.setText(Event.game);
                dateTextView.setText(Event.date);
                creatorTextView.setText(Event.creator);
                descTextView.setText(Event.body);
                listString.clear();
                listString = Event.getJoinedList();
                sizeOfList = listString.size();
                Log.i("size1", "size of = " + sizeOfList);
                for(int i = 0; i < sizeOfList; i++){
                    User userdata = new User();
                    userdata.userName = listString.get(i);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventActivity.this, android.R.layout.simple_list_item_1, listString);
                joinedListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Log.i("size3", "size of = " + sizeOfList);


    }

    @Override
    public void onStart(){
        super.onStart();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        specificUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thisUser = dataSnapshot.getValue(User.class);
                eventsJoined = thisUser.eventsJoined;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mUsersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        /*
        Log.i("size2", "size of = " + sizeOfList);

        for(int i = 0; i < sizeOfList; i++){
            specificUserRef = mUsersReference.child(listString.get(i));
            specificUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User tempUser = dataSnapshot.getValue(User.class);
                    String tempName = tempUser.getUserName();
                    xList.add(tempName);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("DATABASE ERROR");
                }
            });
        }*/
        //ArrayAdapter adapter2 = new ArrayAdapter(EventActivity.this, android.R.layout.simple_list_item_1, xList);
        //joinedListView.setAdapter(adapter2);
        //ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(EventActivity.this, android.R.layout.simple_list_item_1, xList);
        //joinedListView.setAdapter(adapter2);




        // Set an OnItemClickListener for each of the list items
        final Context context = this;
        joinedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  /*  User selected = userList.get(position);
                Intent detailIntent = new Intent(EventActivity.this, ProfilesActivity.class);
                detailIntent.putExtra("name", selected.userName);
                detailIntent.putExtra("email", selected.userEmail);
                detailIntent.putExtra("bio", selected.bio);
                detailIntent.putExtra("key", selected.key);
                detailIntent.putExtra("uid", selected.uid);
                startActivity(detailIntent);*/
                  String selected = listString.get(position);
                  Intent i = new Intent(EventActivity.this, ProfilesActivity.class);
                  i.putExtra("uid", selected);
                  startActivity(i);

            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user has been added to event, do not add again
                for(int i = 0; i < listString.size(); i++){
                    if(listString.get(i) == mUsername || listString.get(i) == uid) {
                        Toast.makeText(EventActivity.this, "you are already a part of the event " + titleTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                        exist = true;
                        break;
                    }
                }
                if(!exist) {
                    listString.add(uid);
                    specificEventRef.child("joinedList").setValue(listString);
                    eventsJoined.add(Event.title);
                    Toast.makeText(EventActivity.this, "you have joined event " + titleTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                specificUserRef.child("eventsJoined").setValue(eventsJoined);
                specificEventRef.child("joinedList").setValue(listString);
            }
        });



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("user", thisUser.userName);
                Log.i("creator", Event.creator);
                if (thisUser.userName == Event.creator) {
                    Toast.makeText(EventActivity.this, "you can to edit this event", Toast.LENGTH_SHORT).show();
                    Log.i("tag", "user matched creator");
                } else {
                    Toast.makeText(EventActivity.this, "you do not have access to edit this event", Toast.LENGTH_SHORT).show();
                    Log.i("tag", "user did not matched creator");
                }

                Intent i = new Intent(EventActivity.this, EditEventActivity.class);
                i.putExtra("key", Event.id);
                startActivity(i);
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
            Intent homeIntent = new Intent(EventActivity.this, MainActivity.class);
            startActivity(homeIntent);
            EventActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        if(id == R.id.action_events){
            Intent eventIntent = new Intent(EventActivity.this, EventspgActivity.class);
            startActivity(eventIntent);
            EventActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if(id == R.id.action_messages){
            Toast.makeText(getApplicationContext(),"messages page",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EventActivity.this, MessagesActivity.class);
            if(i == null){
                Log.d(TAG, "intent null");
                Toast.makeText(getApplicationContext(), "intent null", Toast.LENGTH_SHORT).show();
            }
            else if(i != null){
                startActivity(i);
            }
            EventActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if(id == R.id.action_account){
            Intent accountIntent = new Intent(EventActivity.this, AccountActivity.class);
            startActivity(accountIntent);
            EventActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if (id == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
