package com.example.careconsortium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.careconsortium.model.QuestionDB;
import com.example.careconsortium.model.Topics;
import com.example.careconsortium.util.CSVFile;
import com.example.careconsortium.util.FirestoreUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class AdminPageActivity extends AppCompatActivity {
    Button uploadFile;
    TextView successMsg;
    public static int MAX_QUESTIONS_PER_LEVEL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        successMsg = (TextView) findViewById(R.id.txtAdminSucccess);
        uploadFile = (Button)findViewById(R.id.uploadFile);
        uploadFile.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {

        InputStream inputStream = getResources().openRawResource(R.raw.quest);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> questList = csvFile.read();

        List<String> topics = new ArrayList<String>();
        Hashtable<String, Integer> no_of_levels_in_topic = new Hashtable<String, Integer>();
        Hashtable<String, Integer> level_counter = new Hashtable<String, Integer>();

        //elements : 1. topic, 2.question 3.question_type 4.choice1	5.choice2	6.choice3
        // 7.choice4	8.answer
        int count = 0;
        int current_level = 0;
        for (String[] elements :questList)
        {
            if(elements == null) {
                successMsg.setText( getString(R.string.excel_error));
                continue;
            }
            String topic_name = elements[0];

            if (count == 0) {
                count++;
                continue;
            }

            if (! topics.contains(topic_name)) {
                topics.add(topic_name);
                no_of_levels_in_topic.put(topic_name,1);
                level_counter.put(topic_name,1);
                current_level = 1;
            } else {
                int a = no_of_levels_in_topic.get(topic_name);
                int b = level_counter.get(topic_name);
                current_level = a;
                b++;
                if (b >= (MAX_QUESTIONS_PER_LEVEL+1)) {
                    a++;
                    current_level = a;
                    b = 1;
                }
                no_of_levels_in_topic.put(topic_name, a);
                level_counter.put(topic_name,b);
            }

            String question_type = elements[1];
            String question = elements[2];
            String choice1 = elements[3];
            String choice2 = elements[4];
            String choice3 = elements[5];
            String choice4 = elements[6];
            String answer = elements[7];

            QuestionDB db = new QuestionDB();
            db.setQuestion_text(question);
            db.setAnswer(answer);
            db.setChoices(List.of(choice1, choice2, choice3, choice4));
            db.setQuestion_type(question_type);
            db.setTopic_id(topics.indexOf(topic_name) + 1);
            db.setTopic_name(topic_name);
            db.setLevel(current_level);
            FirestoreUtil.getCollection("questions").add(db);
        }

        // create topics collection
        for (String topic :topics) {
            Topics tp = new Topics();
            tp.setTopic_name(topic);
            tp.setNo_of_levels(no_of_levels_in_topic.get(topic));
            tp.setTopic_id(topics.indexOf(topic) + 1);

            FirestoreUtil.getCollection("topics").add(tp);
        }

        successMsg.setText( getString(R.string.upload_success));

    }
}