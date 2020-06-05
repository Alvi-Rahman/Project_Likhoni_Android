package com.example.alvirahman.version02;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {



    private static final String TAG = "PostDetailActivity";

    public static final String EXTRA_POST_KEY = "post_key";

    //private static final String TAG = "HomeFragment";
    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
    //private CommentAdapter mAdapter;

    private TextView mAuthorView;
    private TextView mTitleView;
    private TextView mBodyView;
    private String currentuser;


    // new



    //DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<Post> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //mAuthorView=view.findViewById(R.id.post_author);
        //mBodyView=view.findViewById(R.id.post_body);
        //mTitleView=view.findViewById(R.id.post_title);



        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new RecyclerViewAdapter(context, list);

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Loading Data from Firebase Database");

        //progressDialog.show();

        //databaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.Database_Path);
        //currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("posts");

        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Toast.makeText(context, "Home activity", Toast.LENGTH_LONG).show();

                progressDialog.show();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Post post = dataSnapshot.getValue(Post.class);

                    list.add(post);

                    adapter.notifyDataSetChanged();
                }



                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });



        //Toast.makeText(context, "home fragment started", Toast.LENGTH_LONG).show();
        

        return view;

        //return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}