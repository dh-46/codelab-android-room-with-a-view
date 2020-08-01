package tw.dh46.codelab.roomsample.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Room database
 * - 一種在SQLite database上的一個資料庫Layer
 * - 以往使用SQLiteOpenHelper要做的瑣事它都幫你處理
 * - 使用DAO物件操作database
 * - 預設來說，Room不會讓你在主執行緒執行Queries。
 * - 當使用LiveData為DAO回傳時，Room會自動以非同步的方式在背景緒執行該Query。
 * - Room提供編譯時期的SQL檢查
 * <p>
 * 實作WordRoomDatabase
 * - 繼承 RoomDatabase 類別，且必須為抽象abstract
 * - 使用Annotation指定資料庫中的資料表(Entity)、版本、是否匯出Schema
 * - 由於這個練習不會練到資料庫的版本升級處理，因此exportSchema設為false，避免IDE警告。
 * 正常來說，應該要指定一個路徑讓Room可以匯出，並讓git可以add進版控。
 *
 * - 如果有改database schema，一定要更改version並建立更版的機制。
 */
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    /**
     * 建立抽象的方法將DAO暴露出去
     *
     * @return
     */
    public abstract WordDao wordDao();

    private static volatile WordRoomDatabase INSTANCE;
    // 建立固定的背景緒限制，負責DB的背景非同步行為
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * 建立Singleton方法，確保一直取到的都是同一個物件實體，不會有同時開了很多個的情況。
     *
     * @param context
     * @return
     */
    public static WordRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // 第一次存取的時候會利用Builder來建立物件，並取名為word_database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
