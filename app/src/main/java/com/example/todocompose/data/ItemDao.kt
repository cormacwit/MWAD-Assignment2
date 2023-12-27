import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todocompose.data.ToDoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ToDoItem)

    @Update
    suspend fun update(item: ToDoItem)

    @Delete
    suspend fun delete(item: ToDoItem)

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<ToDoItem>

    @Query("SELECT * from items ORDER BY task ASC")
    fun getAllItems(): Flow<List<ToDoItem>>
}
