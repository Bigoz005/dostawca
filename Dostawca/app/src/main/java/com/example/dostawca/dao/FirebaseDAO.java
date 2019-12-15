package com.example.dostawca.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dostawca.dto.Route;
import com.example.dostawca.dto.RouteList;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDAO {
    private FirebaseDatabase firebaseDatabase;
    private List<Route> routes = new ArrayList<>();


    public FirebaseDAO() {
        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    public void saveRouteForCurrentUser(Route route) {
        //todo: get current user ID from service
        DatabaseReference pushedKey = firebaseDatabase.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push();
        pushedKey.setValue(route);
    }


    public void getRoutes() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count ", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Route route = postSnapshot.getValue(Route.class);
                    routes.add(route);
                    System.out.println("Got route: " + route.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Count ", "" + databaseError.getMessage());

            }

        });
    }


}


