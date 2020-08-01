package tw.dh46.codelab.roomsample;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tw.dh46.codelab.roomsample.database.Word;
import tw.dh46.codelab.roomsample.database.WordDao;
import tw.dh46.codelab.roomsample.database.WordRoomDatabase;

/**
 * Repository
 * - 資料的調變者/中介者，負責控管不同來源的資料(Remote/Local)
 * - 雖然不屬於 Architecture Components libraries，但是是官方建議的最佳實作。
 * - 一般來說，可由Repository實作取網路還是取本地的資料邏輯
 *
 * Repository的深入實作可看
 * https://github.com/android/architecture-components-samples/tree/master/BasicSample
 */
public class WordRepository {

    /**
     * DAO被傳入而不是整個Database物件，因為所有操作的方法都在DAO身上，
     * Repository沒必要知道整個database物件。
     *
     * The DAO is passed into the repository constructor as opposed to the whole database.
     * This is because you only need access to the DAO,
     * since it contains all the read/write methods for the database.
     * There's no need to expose the entire database to the repository.
     */
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;


    /**
     * 注意:
     * 如果要針對Repository進行單元測試，必須要移除對Application的相依。
     * 如此一來會增加Code的複雜性，因此本練習中暫不處理。
     * 延伸可看: https://github.com/android/architecture-components-samples
     * @param application
     */
    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    /**
     * Room 會自動在非主緒執行
     * UI只要observe LiveData就好，LiveData會在資料異動時notify。
     * @return
     */
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    /**
     * 記得不可在主執行緒呼叫DAO方法!!
     *
     * Room為了確保耗時工作不在主執行緒執行，
     * 當你在主執行緒呼叫DAO方法時，會拋出例外。
     * @param word
     */
    public  void insert(Word word) {
        // 這裡利用在WordRoomDatabase中所建立的executor來處理背景工作
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}
