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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfilesActivity extends AppCompatActivity{
    private String key, uid, favGames, bio, name, email;
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
}
