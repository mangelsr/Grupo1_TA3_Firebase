package com.amst.g1.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import ec.edu.espol.fiec.android.grupo1_ta3_firebase.objetos.Tweet;

public class ProfileActivity extends AppCompatActivity {

    TextView txt_id,txt_name,txt_email;
    ImageView imv_photo;
    Button btn_logout;
    DatabaseReference db_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        HashMap<String,String> info_user = (HashMap<String,String>) intent
                .getSerializableExtra("info_user");
        txt_id = findViewById(R.id.txt_userId);
        txt_name = findViewById(R.id.txt_nombre);
        txt_email = findViewById(R.id.txt_correo);
        imv_photo = findViewById(R.id.imv_foto);
        txt_id.setText(info_user.get("user_id"));
        txt_name.setText(info_user.get("user_name"));
        txt_email.setText(info_user.get("user_email"));
        String photo = info_user.get("user_photo");
        Picasso.get().load(photo).into(imv_photo);
        iniciarBaseDeDatos();
        leerTweets();
        escribirTweets(info_user.get("user_name"));
    }

    public void iniciarBaseDeDatos(){
        db_reference = FirebaseDatabase.getInstance().getReference().child("Grupo");
    }

    public void leerTweets() {
        db_reference.child("Grupo1").child("tweets").addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                    System.out.println(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println(error.toException());
            }
        });
    }

    public void escribirTweets(String autor) {
        Tweet tweet = new Tweet(autor, "Test tweet from method");
        tweet.publicarTweet();
        DatabaseReference tweets = db_reference.child("Grupo1").child("tweets");
        tweets.setValue(tweet.getTweet());
        tweets.child(tweet.getTweet()).child("autor").setValue(tweet.getAutor());
        tweets.child(tweet.getTweet()).child("fecha").setValue(tweet.getFecha());
    }

    public void cerrarSesion(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("msg","cerrarSesion");
        startActivity(intent);
    }
}
