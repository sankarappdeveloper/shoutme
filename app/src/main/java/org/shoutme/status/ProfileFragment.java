package org.shoutme.status;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.Collections;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午1:31
 * Mail: specialcyci@gmail.com
 */
public class ProfileFragment extends Fragment {
    private View parentView;
    private ListView listView;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference mref1;
    StorageReference ds;
    ArrayList<Video> calendarList;
    ViewPager mViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.profile, container, false);


 recyclerView=(RecyclerView)view.findViewById(R.id.view);

        initView();
//        CustomAdapter customAdapter=new CustomAdapter(getContext(),calendarList);
//        recyclerView.setAdapter(customAdapter);
        return view;
    }



    private void initView(){
        database = FirebaseDatabase.getInstance();

        calendarList=new ArrayList<>();
        mref1 = database.getReference().child("Videos");
        mref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Video video = postSnapshot.getValue(Video.class);
//                    Toast.makeText(getActivity(),key, Toast.LENGTH_SHORT).show();
//                Post post = new Post("Mad Max: Fury Road
                    calendarList.add(video);
                    Collections.reverse(calendarList);
                }
//        ArrayList<String> calendarList = getCalendarData();
//
//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "Clicked item!", Toast.LENGTH_LONG).show();
//            }
//        });
//                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
                recyclerView.setLayoutManager(layoutManager);
                VideoAdapter customAdapter=new VideoAdapter(getContext(),calendarList);
                recyclerView.setAdapter(customAdapter);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

}
