package com.example.drbot;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Dataget {
    public ArrayList<String> s1;
    public Map<String,String> map;
    public Map<String,String> map1;
    private DatabaseReference databaseReference;
    public Dataget(String key){
        System.out.println(key);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        databaseReference=db.getReference("dr").child("ill").child(key);
        System.out.println(key);
        map1=new HashMap<String,String>();
        s1=new ArrayList<String>();
        map=new HashMap<String,String>();
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for(DataSnapshot ds:snapshot.getChildren()) {
                    s1.add(ds.getKey());
                        map.put(s1.get(i)+'1', ds.getValue().toString().substring(1, 5));
                        map.put(s1.get(i)+'2',ds.getValue().toString().substring(34,38));
                        map.put(s1.get(i)+'3',ds.getValue().toString().substring(68,75));

                    //map.put(s1.get(i),ds.getValue().toString().substring(25,28));
                    map1.put(s1.get(i)+'1',ds.getValue().toString().substring(6,33).trim());
                    map1.put(s1.get(i)+'2',ds.getValue().toString().substring(39,67).trim());
                    map1.put(s1.get(i)+'3',ds.getValue().toString().substring(76,100).trim());

                    i++;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);

    }
    }


