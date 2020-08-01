package tw.dh46.codelab.roomsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tw.dh46.codelab.roomsample.database.Word;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Word> mWordList; // Cached copy of words

    public WordListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mWordList != null && mWordList.size() > 0) {
            Word currentWord = mWordList.get(position);
            holder.tvWord.setText(currentWord.getWord());
        } else {
            // Covers the case of data not being ready yet.
            holder.tvWord.setText("No words available");
        }
    }

    /**
     * 會被呼叫好幾次，包含第一次執行。
     * 所以要做null檢查，避免資料還沒設定。
     * @return
     */
    @Override
    public int getItemCount() {
        if (mWordList != null) {
            return mWordList.size();
        }
        return 0;
    }

    /**
     * 外部更新資料進來
     * @param wordList
     */
    public void setWordList(List<Word> wordList) {
        mWordList = wordList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvWord;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.textView);
        }
    }
}
