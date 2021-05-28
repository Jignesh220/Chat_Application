package com.chatapplication.FriendlyChat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class chatBox extends AppCompatActivity {
    int a=1;
    private ListView MsgListView;
    private msgAdapter mMessageAdapter;
    private EditText getMessage;
    private ImageButton sendButton;
    private TextView roomName;
    long max =0 ;
//    private List<messageChat> messageList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        MsgListView =(ListView)findViewById(R.id.messageListView);
        getMessage = findViewById(R.id.messageTextView);
        sendButton = findViewById(R.id.messageSend);
        roomName = findViewById(R.id.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmessage();
                getMessage.setText("");
                get_msg_onclick();
            }
        });

    }
    public void get_Message_count(){
        db.collection("code").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

            }
        });
    }

    public void get_msg_onStart(){
        List<chat> messageList = new ArrayList<>();
        mMessageAdapter = new msgAdapter(this ,R.layout.activity_message, messageList);
        MsgListView.setAdapter(mMessageAdapter);

        Bundle bn = getIntent().getExtras();
        String code = bn.getString("codeName");

        db.collection("code").document(code)
                .collection("Message").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot j : list){
                                chat c = j.toObject(chat.class);
                                messageList.add(c);
                            }
                            mMessageAdapter.notifyDataSetChanged();
                        }else{
//                            Toast.makeText(chatBox.this,"fail",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void get_msg_onclick(){
        MsgListView.setAdapter(null);
        List<chat> messageList = new ArrayList<>();
        mMessageAdapter = new msgAdapter(this ,R.layout.activity_message, messageList);
        MsgListView.setAdapter(mMessageAdapter);

        Bundle bn = getIntent().getExtras();
        String code = bn.getString("codeName");

        db.collection("code").document(code)
                .collection("Message").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot j : list){
                                chat c = j.toObject(chat.class);
                                messageList.add(c);
                            }
                            mMessageAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(chatBox.this,"fail",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void getmessage(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String message = getMessage.getText().toString();
        int test =getMessage.getText().hashCode();
        Bundle bn = getIntent().getExtras();
        String code = bn.getString("codeName");

        DocumentReference store = db.collection("code").document(code);
        store.update("Message", FieldValue.increment(1));
        assert firebaseUser != null;
        if (firebaseUser.getDisplayName() != null) {
            chat newMessage = new chat(firebaseUser.getDisplayName(), message);
            db.collection("code").document(code).
                    collection("Message").add(newMessage);
        }else {
            Toast.makeText(chatBox.this, "can't send message please provide authorised MailId", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bn = getIntent().getExtras();
        String code = bn.getString("codeName");
        roomName.setText(code);
        get_msg_onStart();
    }

}