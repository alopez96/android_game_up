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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "main_activity";
    private TextView nameTextView;
    private TextView emailTextView;

    private String mUsername;
    private String mUserEmail;
    private Button editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize references to views
        nameTextView = (TextView) findViewById(R.id.user_name_tv);
        emailTextView = (TextView) findViewById(R.id.user_email_tv);
        editButton = findViewById(R.id.edit_account_bt);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mUsername = user.getDisplayName();
        mUserEmail = user.getEmail();

        nameTextView.setText(mUsername);
        emailTextView.setText(mUserEmail);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, EditAccountActivity.class);
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
        return super.onOptionsItemSelected(item);
    }

}
