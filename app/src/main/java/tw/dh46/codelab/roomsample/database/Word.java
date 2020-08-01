package tw.dh46.codelab.roomsample.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room 中的 Entity 物件
 * - 一個類別就代表一張資料表
 * - 類別屬性代表資料表的欄位
 * - 要加上Room的Annotation才會有作用
 * - 類別屬性不是設public就是要有Getter方法，才可被Room使用。
 *
 * Room will ultimately use these properties to both create the table
 * and instantiate objects from rows in the database.
 *
 * Annotation 介紹
 * - Entity: 告訴Room這是一個Entity類別，資料表名稱預設是類別名稱。這裡我們自訂成word_table。
 * - PrimaryKey: 每張資料表/每個Entity類別都需要一個主鍵。
 * - NonNull: 這個欄位/屬性不得為NULL值
 * - ColumnInfo(name = "word"): 自訂欄位名稱、預設值是屬性名稱。
 */
@Entity(tableName = "word_table")
public class Word {

    // 主鍵也可以像一般資料表一樣設置自動產生的流水號
//    @PrimaryKey(autoGenerate = true)
//    private int id;


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word) {
        mWord = word;
    }

    /**
     * 因為word屬性修飾字為private，所以要給一個public的getter方法
     * @return
     */
    public String getWord() {
        return mWord;
    }
}
