package ca.alexbalt.gameup2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditAccountActivity extends AppCompatActivity{

    private Button submitButton;
    private Button cancelButton;

    private EditText displayNameText, bioText, gamesText;


    private String mUsername, mUserEmail, mBio, mFavGames, uid, userKey, bio, favGames, mName, userName;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;             //entry point for our app to access the database
    private DatabaseReference mUsersReference;
    private DatabaseReference specificUserRef;
    private User thisUser;
    private ArrayList<String> eventsCreated = new ArrayList<>();
    private ArrayList<String> joinedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        submitButton = findViewById(R.id.submit_bt);
        cancelButton = findViewById(R.id.cancel_submit_bt);

        displayNameText = findViewById(R.id.edit_display_name_tv);
        bioText = findViewById(R.id.edit_bio_tv);
        gamesText = findViewById(R.id.edit_games_tv);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersReference = mFirebaseDatabase.getReference().child("users");


        Intent intent = getIntent();
        bio = intent.getStringExtra("bio");
        favGames = intent.getStringExtra("favGames");
        userName = intent.getStringExtra("userName");

        bioText.setText(bio, TextView.BufferType.EDITABLE);
        gamesText.setText(favGames, TextView.BufferType.EDITABLE);
        displayNameText.setText(userName, TextView.BufferType.EDITABLE);



        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mUsername = user.getDisplayName();
        mUserEmail = user.getEmail();
        uid = user.getUid();
        specificUserRef = mUsersReference.child(uid);

        //get the list of events already in firebase
        specificUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thisUser = dataSnapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChanges();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelChanges();
            }
        });
    }





    public void submitChanges(){
        mName = displayNameText.getText().toString();
        mBio = bioText.getText().toString();
        mFavGames = gamesText.getText().toString();

        mName = displayNameText.getText().toString();


        specificUserRef.child("userName").setValue(mName);

        specificUserRef.child("bio").setValue(mBio);
        specificUserRef.child("favGames").setValue(mFavGames);
        specificUserRef.child("userName").setValue(mName);
        Toast.makeText(this, "account updated",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, AccountActivity.class);
        startActivity(i);
        EditAccountActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public void cancelChanges(){
        Toast.makeText(this, "update cancelled",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, AccountActivity.class);
        startActivity(i);
        EditAccountActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
