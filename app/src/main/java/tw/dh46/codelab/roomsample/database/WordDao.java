package tw.dh46.codelab.roomsample.database;

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
    List<Word> getAlphabetizedWords();
}
