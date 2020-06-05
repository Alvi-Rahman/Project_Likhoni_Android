package com.example.alvirahman.version02;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

//import com.google.firebase.quickstart.database.models.Post;
//import com.google.firebase.quickstart.database.models.User;

public class PostActivity extends AppCompatActivity {

    private static final String TAG = "PostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    private DatabaseReference journalCloudEndPoint;

    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mDescField;
    private Button mSubmitButton;
    private String currentuser;


    Bundle bundle = getIntent().getExtras();
    public String username = bundle.getString("message");







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mTitleField = (EditText) findViewById(R.id.textTitle);
        mDescField = (EditText) findViewById(R.id.textDesc);
        mSubmitButton = (Button) findViewById(R.id.postBtn);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PostActivity.this, MainActivity.class));
        finish();

    }

    private void submitPost() {


        final String title = mTitleField.getText().toString();
        final String description = mDescField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(description)) {
            mDescField.setError(REQUIRED);
            return;
        }


        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //writeNewUser(currentuser, title, description);
        NewPost(currentuser,username,title,description);

        // Disable button so there are no multi-posts
        //setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

    }



    private void NewPost (String userId, String username, String title, String body){
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
        mDatabase.child("users").child(userId).setValue(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(PostActivity.this, "Posted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PostActivity.this, MainActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed

                        Toast.makeText(PostActivity.this, "Post failed", Toast.LENGTH_SHORT).show();

                    }
                });

    }



/*

    private void writeNewUser(final String userId, String name, String email) {

        User user = new User(name, email);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();
        mDatabase.child("users").child(userId).setValue(user);
        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed

                    }
                });

    }
*/
    /*

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }
    */


}
