package com.amst.g1.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistersActivity extends AppCompatActivity {

    DatabaseReference db_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registers);
        db_reference = FirebaseDatabase.getInstance().getReference().child("Registros");
        leerRegistros();
    }

    public void leerRegistros() {
        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mostrarRegistrosPorPantalla(snapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println(error.toException());
            }
        });
    }

    public void mostrarRegistrosPorPantalla(DataSnapshot snapshot) {
        LinearLayout contTemp =  findViewById(R.id.ContenedorTemp);
        LinearLayout contAxis =  findViewById(R.id.ContenedorAxis);
        String tempVal = String.valueOf(snapshot.child("temp").getValue());
        String axisVal = String.valueOf(snapshot.child("axis").getValue());
        TextView temp = new TextView(getApplicationContext());
        temp.setText(tempVal+" C");
        contTemp.addView(temp);
        TextView axis = new TextView(getApplicationContext());
        axis.setText(axisVal);contAxis.addView(axis);}
}
