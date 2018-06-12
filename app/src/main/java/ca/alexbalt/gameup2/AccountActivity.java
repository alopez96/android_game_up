package ca.alexbalt.gameup2;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "Account_activity";
    private TextView nameTextView;
    private TextView emailTextView;


    private String mUsername, mUserEmail, uid;
    private Button editButton;

    private String mUserbio;
    private String mUsergames;

    TextView gameTextView, bioTextView;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersReference, specificUserReference;
    private User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize references to views
        nameTextView = findViewById(R.id.user_name_tv);
        emailTextView = findViewById(R.id.user_email_tv);
        bioTextView = findViewById(R.id.user_bio_tv);
        gameTextView = findViewById(R.id.user_games_tv);
        editButton = findViewById(R.id.edit_account_bt);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        mUsername = user.getDisplayName();
        mUserEmail = user.getEmail();
        uid = user.getUid();
//        nameTextView.setText(mUsername);
        emailTextView.setText(mUserEmail);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersReference = mFirebaseDatabase.getReference().child("users");
        specificUserReference = mUsersReference.child(uid);

        specificUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thisUser = dataSnapshot.getValue(User.class);
                bioTextView.setText(thisUser.bio);
                gameTextView.setText(thisUser.favGames);
                nameTextView.setText(thisUser.userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, EditAccountActivity.class);
                intent.putExtra("bio", thisUser.bio);
                intent.putExtra("favGames", thisUser.favGames);
                intent.putExtra("userName",thisUser.userName);
                startActivity(intent);

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
            Intent homeIntent = new Intent(AccountActivity.this, MainActivity.class);
            startActivity(homeIntent);
            AccountActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        }

        if(id == R.id.action_events){
            Intent eventIntent = new Intent(AccountActivity.this, EventspgActivity.class);
            startActivity(eventIntent);
            AccountActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if(id == R.id.action_messages){
            Toast.makeText(getApplicationContext(),"messages page",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AccountActivity.this, MessagesActivity.class);
            if(i == null){
                Log.d(TAG, "intent null");
                Toast.makeText(getApplicationContext(), "intent null", Toast.LENGTH_SHORT).show();
            }
            else if(i != null){
                startActivity(i);
            }
            AccountActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if(id == R.id.action_account){
            Intent accountIntent = new Intent(AccountActivity.this, AccountActivity.class);
            startActivity(accountIntent);
            AccountActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }

        if (id == R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
