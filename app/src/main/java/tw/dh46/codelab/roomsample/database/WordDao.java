package tw.dh46.codelab.roomsample.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * DAO: Data Access Object
 * - 會在編譯時期自動檢驗你的SQL語法，並與方法綁定。
 * - 利用Annotation來表示CRUD
 * - Room用DAO建立一個乾淨的API環境
 * - DAO 必須為Interface 介面 或是 Abstract Class 抽象類別
 * - 記得要加上Dao這個Annotation才有用
 * - 所有的Queries預設必須在不同方法
 *
 * WordDao介面將實作以下行為
 * - 取得資料表中所有單字並以字母升冪排序 (a~z)
 * - 插入一個新單字到資料表
 * - 刪除所有單字
 *
 */
@Dao // 告訴Room這是個DAO介面
public interface WordDao {

    /**
     * 插入一個單字
     * - 透過指定OnConflict，讓資料表可以重複插入相同的單字
     * - Insert這個Annotation很特別，你不需要寫任何SQL command。(Query就需要)
     * @param word
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    /**
     * 刪除所有單字
     * - 目前沒有方便的Annotation可以一次刪除多筆資料，因此用Query搭配SQL command來實現。
     * - Query的使用一定要給SQL command
     */
    @Query("DELETE FROM word_table")
    void deleteAll();

    /**
     * 取得所有單字並以字母a~z排序
     * @return
     */
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();

    /**
     * LiveData class
     * - 當資料有異動時，UI須即時更新，這代表我們必須觀察資料的變化。
     * - 資料儲存方式的不同，可能會使得Observing與元件間的相依性變得很亂，
     * 也讓測試與除錯變得不易。
     * - LiveData有多種不同的用法，此練習只專注在與Room的搭配。
     *
     * 如何與Room結合?
     * 在DAO方法的回傳類型中使用LiveData，Room會自動產生所有需要的CODE，
     * 並在SQLite的資料有異動時，自動更新LiveData。(要在UI層中使用Observer來負責聽)
     *
     * 如果是自己另外使用LiveData，而不是與Room搭配，
     * 要注意LiveData並沒有public的方法可以設值。
     * 因此，請參考MutableLiveData這個類別，它有setValue/postValue兩種方法，
     * 通常這個類別都是與ViewModel類搭配使用，
     * 而ViewModel將只會expose immutable 的 LiveData 物件給Observer。
     *
     */
}
