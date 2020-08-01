package tw.dh46.codelab.roomsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLAY = BuildConfig.APPLICATION_ID + "wordlistsql.REPLY";

    private EditText mEdtWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        initView();
    }

    private void initView() {
        mEdtWord = findViewById(R.id.edit_word);
        Button btnSubmit = findViewById(R.id.button_save);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEdtWord.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // 有輸入拋回去
                    String word = mEdtWord.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLAY,word);
                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }
        });
    }
}