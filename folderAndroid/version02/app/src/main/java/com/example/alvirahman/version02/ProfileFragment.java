package com.example.alvirahman.version02;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment{


    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;




    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };


        Log.d("Fragment ", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);

        //signOut=(Button)view.findViewById(R.id.sign_out)

        ((Button) view.findViewById(R.id.sign_out)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();


            }
        });


        ((Button) view.findViewById(R.id.post_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getActivity(), PostActivity.class));



            }
        });





        return view;

    }


        public void signOut() {
            Toast.makeText(getActivity(), "Signing Out", Toast.LENGTH_SHORT).show();
            auth.signOut();
            authListener.onAuthStateChanged(auth);
        }


}
