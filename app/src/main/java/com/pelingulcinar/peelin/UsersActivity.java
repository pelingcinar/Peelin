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
import com.google.firebase.database.Query;


public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mUsersList;

    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mToolbar = (Toolbar) findViewById(R.id.users_appBar);

        if (getSupportActionBar()!=null) {
            this.setSupportActionBar(mToolbar);
            this.getSupportActionBar().setTitle("All Users");
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mUsersDatabase = FirebaseDatabase.getInstance().getReference();

        mUsersList = (RecyclerView) findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query=mUsersDatabase.child("Users").orderByChild("name");
        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .build();

        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {


                    @NonNull
                    @Override
                    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        return null;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
                        holder.setDisplayName(model.getName());
                        holder.setUsersStatus(model.getStatus());
                    }

                    protected void populateViewHolder(UsersViewHolder usersViewHolder, Users users, int position) {


                        usersViewHolder.setDisplayName(users.getName());
                        usersViewHolder.setUsersStatus(users.getStatus());

                        final String user_id = getRef(position).getKey();

                        usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent profileIntent = new Intent(UsersActivity.this, ProfileActivity.class);
                                profileIntent.putExtra("user id", user_id);
                                startActivity(profileIntent);

                            }
                        });

                    }
                };

        mUsersList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {


        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDisplayName(String name) {


            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setUsersStatus(String status) {


            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(status);

        }

    }
}