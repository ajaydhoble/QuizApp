package com.example.quizz;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Random;

public class QuestionsActivity extends AppCompatActivity {

    TextView question;
    Button opt1, opt2,opt3, opt4, next;
    String answer, collecID;
    Integer score = 0, numOfQues, index = 0;
    ArrayList<Integer> viewedQues = new ArrayList<>();
    Random rand;
    ArrayList<Question> questionArray = new ArrayList<>();

    FirebaseFirestore firestoreIns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        firestoreIns = FirebaseFirestore.getInstance();
        question = findViewById(R.id.questionTextView);
        opt1 = findViewById(R.id.option1Btn);
        opt2 = findViewById(R.id.option2Btn);
        opt3 = findViewById(R.id.option3Btn);
        opt4 = findViewById(R.id.option4Btn);
        next = findViewById(R.id.nxtBtn);

        collecID = getIntent().getExtras().getString("topic");
        if(collecID.equals("Kids")){
            collecID = "quizQuestions";
        }

        numOfQues = 7;
        rand = new Random();
        while (viewedQues.size() < numOfQues) {
            int a = rand.nextInt(10)+1;
            if (!viewedQues.contains(a)) {
                viewedQues.add(a);
            }
        }
        //Log.d("LIST",viewedQues.toString());
        for(int i = 0; i < viewedQues.size(); i++){
            getQuestion(Integer.toString(viewedQues.get(i)));
        }

        opt1.setOnClickListener(view -> {
            if(answer.equals("1")){
                score += 1;
                opt1.setBackgroundColor(Color.GREEN);
            }
            else {
                opt1.setBackgroundColor(Color.RED);
                if(answer.equals("2")){ opt2.setBackgroundColor(Color.GREEN);}
                if(answer.equals("3")){ opt3.setBackgroundColor(Color.GREEN);}
                if(answer.equals("4")){ opt4.setBackgroundColor(Color.GREEN);}
            }
        });
        opt2.setOnClickListener(view -> {
            if(answer.equals("2")){
                score += 1;
                opt2.setBackgroundColor(Color.GREEN);
            }
            else {
                opt2.setBackgroundColor(Color.RED);
                if(answer.equals("1")){ opt1.setBackgroundColor(Color.GREEN);}
                if(answer.equals("3")){ opt3.setBackgroundColor(Color.GREEN);}
                if(answer.equals("4")){ opt4.setBackgroundColor(Color.GREEN);}
            }
        });
        opt3.setOnClickListener(view -> {
            if(answer.equals("3")){
                score += 1;
                opt3.setBackgroundColor(Color.GREEN);
            }
            else {
                opt3.setBackgroundColor(Color.RED);
                if(answer.equals("1")){ opt1.setBackgroundColor(Color.GREEN);}
                if(answer.equals("2")){ opt2.setBackgroundColor(Color.GREEN);}
                if(answer.equals("4")){ opt4.setBackgroundColor(Color.GREEN);}
            }
        });
        opt4.setOnClickListener(view -> {
            if(answer.equals("4")){
                score += 1;
                opt4.setBackgroundColor(Color.GREEN);
            }
            else {
                opt4.setBackgroundColor(Color.RED);
                if(answer.equals("1")){ opt1.setBackgroundColor(Color.GREEN);}
                if(answer.equals("2")){ opt2.setBackgroundColor(Color.GREEN);}
                if(answer.equals("3")){ opt3.setBackgroundColor(Color.GREEN);}
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQues();
            }
        });

       }


    private void getQuestion(String id) {
        DocumentReference documentReference = firestoreIns.collection(collecID).document(id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                if(value.exists()){
                String q = value.getData().get("ques").toString();
                String a = value.getData().get("a").toString();
                String b = value.getData().get("b").toString();
                String c = value.getData().get("c").toString();
                String d = value.getData().get("d").toString();
                String ans = value.getData().get("ans").toString();
                Log.d("LOGGER",q+a+b+c+d+ans);
                Question newQues = new Question(q,a,b,c,d,ans);
                questionArray.add(newQues);
                if(questionArray.size() == 1){
                    question.setText(q);
                    opt1.setText(a);
                    opt2.setText(b);
                    opt3.setText(c);
                    opt4.setText(d);
                    answer = ans;
                }
                }
            }
        });
    }

    private void updateQues(){
        index = (index + 1) % questionArray.size();
        if(index == 0){
            AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
            alertBox.setCancelable(false);
            alertBox.setMessage("Your Score is " + score);
            alertBox.setPositiveButton("Close", (dialog, which) -> finish());
            alertBox.show();
        }
        question.setText(questionArray.get(index).getQuestion());
        opt1.setText(questionArray.get(index).getOptA());
        opt1.setBackgroundColor(getResources().getColor(R.color.light_blue));
        opt2.setText(questionArray.get(index).getOptB());
        opt2.setBackgroundColor(getResources().getColor(R.color.light_blue));
        opt3.setText(questionArray.get(index).getOptC());
        opt3.setBackgroundColor(getResources().getColor(R.color.light_blue));
        opt4.setText(questionArray.get(index).getOptD());
        opt4.setBackgroundColor(getResources().getColor(R.color.light_blue));
        answer = questionArray.get(index).getAns();
    }
}