package com.ivanjt.herb;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.herb.Model.HeartRate;

import java.util.Iterator;

public class HomeFragment extends Fragment {
    private TextView mHeartRate;
    private FirebaseAuth mAuth;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mHeartRate = view.findViewById(R.id.tv_heart_rate);

        //Get reference to Users
        DatabaseReference ref = database.getReference("Users").child(mAuth.getUid());
        Query query = ref.child("HeartRate").limitToLast(1);

        //Add ValueEventListener to query
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                HeartRate heartRate = iterator.next().getValue(HeartRate.class);

                if (heartRate.getBpm() != 0){
                    mHeartRate.setText(String.valueOf(heartRate.getBpm()));
                } else {
                    mHeartRate.setText("-");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }
}
