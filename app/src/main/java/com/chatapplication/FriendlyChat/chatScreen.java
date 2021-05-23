package com.chatapplication.FriendlyChat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class chatScreen extends AppCompatActivity {

    static EditText code;
    Button create;
    private static final String TAG = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);


        create = findViewById(R.id.create_new);
        code =findViewById(R.id.edit_code);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser.getDisplayName() != null){
                    createChatRoom();
                    Intent intent = new Intent(chatScreen.this, chatBox.class);
                    intent.putExtra("codeName", code.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(chatScreen.this, "Can't connect Chat Room!!\nPlease Provide Authorised mail Id", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
    }
    private void createChatRoom(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        room newRoom = new room(code.getText().toString(),firebaseUser.getUid());

        db.collection("code").document(code.getText().toString()).set(newRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText(chatScreen.this, "success :)", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(chatScreen.this, "fail :(", Toast.LENGTH_LONG).show();
            }
        });
    }
    private static long back_pressed;
    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}