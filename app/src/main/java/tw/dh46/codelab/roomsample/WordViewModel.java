package tw.dh46.codelab.roomsample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tw.dh46.codelab.roomsample.database.Word;

/**
 * ViewModel
 * - 提供資料給UI
 * - 幫助你活過config changed (像是螢幕旋轉就是)
 * - 是UI與Repository的溝通者
 * - 也可以用來在Fragments間傳遞資料
 * - 是有生命週期意識的元件
 * - 幫助開發者把資料從Activity/Fragment上抽離
 *
 * 為什麼要用LiveData搭配ViewModel
 * - 可以使用observer來監聽資料異動，當資料真的有更動時在改UI
 * - Repository跟UI被ViewModel分開，增加可測試性。
 * - ViewModel不須直接與database交涉，增加可測試性。
 *
 *
 * 要注意的第一件事:
 * - Don't keep a reference to a context that has a shorter lifecycle than your ViewModel!
 *  - Activity, Fragment, View
 *  - 這樣會造成Memory Leak。
 *  (因為有可能viewModel會持有一個被摧毀的activity reference。像是旋轉螢幕時)
 *  - 如果需要context，就繼承AndroidViewModel
 *
 * 要注意的第二件事:
 * ViewModels don't survive the app's process being killed in the background
 * when the OS needs more resources.
 * For UI data that needs to survive process death
 * due to running out of resources,
 * you can use the Saved State module for ViewModels
 */
public class WordViewModel extends AndroidViewModel {

    // a private member variable to hold a reference to the repository.
    private WordRepository mRepository;

    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        // 透過repository的方法初始化allWords
        mAllWords = mRepository.getAllWords();
    }

    /**
     * 取得Repository cached的資料
     * @return
     */
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    /**
     * 將新增資料的行為封裝起來(對UI層)
     * @param word
     */
    public void insert(Word word) {
        mRepository.insert(word);
    }
}
