package tw.dh46.codelab.roomsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import tw.dh46.codelab.roomsample.database.Word;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private WordViewModel mWordViewModel;
    private WordListAdapter mWordListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewModel();
    }

    /**
     * 透過ViewModelProvider來取得ViewModel
     * 當Activity因為Configuration Changed而被銷毀時，
     * ViewModel會persist，等到Activity被重啟時，ViewModelProvider會返回這個原本的ViewModel
     */
    private void initViewModel() {
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                // Update the cached copy of the words in the adapter.
                mWordListAdapter.setWordList(words);
            }
        });
    }

    private void initView() {
        mWordListAdapter = new WordListAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(mWordListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAdd = findViewById(R.id.fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 承接NewWordActivity輸入的值
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // 插入新字
                Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLAY));
                mWordViewModel.insert(word);
            } else {
                Toast.makeText(MainActivity.this,
                        R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
            }
        }
    }
}