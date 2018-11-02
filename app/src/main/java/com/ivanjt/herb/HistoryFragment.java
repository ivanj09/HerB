package com.ivanjt.herb;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ivanjt.herb.Model.HeartRate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;

public class HistoryFragment extends Fragment {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        //Get reference to Users
        DatabaseReference ref = database.getReference("Users").child(mAuth.getUid());
        Query query = ref.child("HeartRate");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<DataPoint> dataPointsList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HeartRate heartRate = dataSnapshot1.getValue(HeartRate.class);

                    int bpm = heartRate.getBpm();

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis((Long) dataSnapshot1.child("created_at").getValue());
                    double sec = calendar.get(Calendar.SECOND);

                    dataPointsList.add(new DataPoint(sec, bpm));
                }

                DataPoint[] dataPoints = dataPointsList.toArray(new DataPoint[0]);

                GraphView graph = view.findViewById(R.id.graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

                graph.getGridLabelRenderer().setHorizontalAxisTitle("minutes");
                graph.getGridLabelRenderer().setVerticalAxisTitle("bpm");
                // enable scaling and scrolling
                graph.getViewport().setScalable(true);
                graph.getViewport().setScalableY(true);

                graph.addSeries(series);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
