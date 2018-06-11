package ca.alexbalt.gameup2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private String mUsername, mUserEmail, mBio, mFavGames, uid, userKey;


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
        mBio = bioText.getText().toString();
        mFavGames = gamesText.getText().toString();
        specificUserRef.child("bio").setValue(mBio);
        specificUserRef.child("favGames").setValue(mFavGames);
        Toast.makeText(this, "account updated",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, AccountActivity.class);
        startActivity(i);
    }

    public void cancelChanges(){
        Toast.makeText(this, "update cancelled",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, AccountActivity.class);
        startActivity(i);
    }
}
