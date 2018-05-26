package org.shoutme.status;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

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
 * Time: 下午3:26
 * Mail: specialcyci@gmail.com
 */
public class CalendarFragment extends Fragment {
    private View parentView;
    private ListView listView;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference mref1;
    StorageReference ds;
    ArrayList<Post> calendarList;
    ViewPager mViewPager;
    String category;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.calendar, container, false);

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("category", Context.MODE_PRIVATE);

         category=sharedpreferences.getString("name","");


        Toast.makeText(getActivity(),category,Toast.LENGTH_SHORT).show();
//        listView   = (ListView) parentView.findViewById(R.id.listView);
         recyclerView = (RecyclerView)parentView.findViewById(R.id.listView);
        initView();
//        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getActivity(),calendarList);
//
//        mViewPager = (ViewPager) parentView.findViewById(R.id.pager);
//        mViewPager.setAdapter(mCustomPagerAdapter);


        return parentView;
    }

    private void initView(){
        database = FirebaseDatabase.getInstance();

        calendarList=new ArrayList<>();
        mref1 = database.getReference().child("Posts");
        mref1.orderByChild("category").equalTo(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
//                    Toast.makeText(getActivity(),key, Toast.LENGTH_SHORT).show();
//                Post post = new Post("Mad Max: Fury Road
                    calendarList.add(post);
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
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
                recyclerView.setLayoutManager(layoutManager);
                CustomAdapter customAdapter=new CustomAdapter(getContext(),calendarList);
                recyclerView.setAdapter(customAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
////            private ArrayList<String> getCalendarData(){
//        ArrayList<String> calendarList = new ArrayList<String>();
//        calendarList.add("New Year's Day");
//        calendarList.add("St. Valentine's Day");
//        calendarList.add("Easter Day");
//        calendarList.add("April Fool's Day");
//        calendarList.add("Mother's Day");
//        calendarList.add("Memorial Day");
//        calendarList.add("National Flag Day");
//        calendarList.add("Father's Day");
//        calendarList.add("Independence Day");
//        calendarList.add("Labor Day");
//        calendarList.add("Columbus Day");
//        calendarList.add("Halloween");
//        calendarList.add("All Soul's Day");
//        calendarList.add("Veterans Day");
//        calendarList.add("Thanksgiving Day");
//        calendarList.add("Election Day");
//        calendarList.add("Forefather's Day");
//        calendarList.add("Christmas Day");
//        return calendarList;
//    }

    }