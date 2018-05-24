package com.pelingulcinar.peelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mUsersList;

    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mToolbar = (Toolbar) findViewById(R.id.users_appBar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");



        mUsersList = (RecyclerView) findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
//Options yaptım
               /* Users.class,
                R.layout.users_single_layout,
                UsersViewHolder.class,
                mUsersDatabase*/
        ) {


            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                Users.class,
                        R.layout.users_single_layout,
                        UsersViewHolder.class,
                        mUsersDatabase

                //return null;
               return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int position, @NonNull Users users) {
                usersViewHolder.setDisplayName(users.getName());
                usersViewHolder.setUsersStatus(users.getStatus());

                final String user_id = getRef(position).getKey();
            //}

            //@Override
            //override yorum değildi
           // protected void populateViewHolder(UsersViewHolder usersViewHolder, Users users, int position) {


                /*usersViewHolder.setDisplayName(users.getName());
                usersViewHolder.setUsersStatus(users.getStatus());

                final String user_id = getRef(position).getKey();*/

                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent profileIntent = new Intent(UsersActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("user id", user_id);
                        startActivity(profileIntent);

                    }
                });

            }
        };

        mUsersList.setAdapter(firebaseRecyclerAdapter);
        //Adapteri options yaptım

        //Bir üst satırımda yoruma aldım çünkü çalışmıyor

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {


        View mView;

         public UsersViewHolder(View itemView) {
             super(itemView);

             mView = itemView;
         }

         void setDisplayName(String name) {


             TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
             userNameView.setText(name);

         }

         void setUsersStatus(String status) {


             TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
             userStatusView.setText(status);

         }

    }
}
