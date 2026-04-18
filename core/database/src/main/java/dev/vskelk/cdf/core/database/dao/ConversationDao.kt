package dev.vskelk.cdf.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.vskelk.cdf.core.database.entity.MessageEntity
import dev.vskelk.cdf.core.database.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE sessionId = :sessionId ORDER BY createdAt ASC")
    fun observeBySession(sessionId: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE sessionId = :sessionId ORDER BY createdAt DESC LIMIT :limit")
    suspend fun latest(sessionId: String, limit: Int): List<MessageEntity>

    @Query("SELECT * FROM messages WHERE pending = 1 ORDER BY createdAt ASC")
    suspend fun pending(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(message: MessageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(messages: List<MessageEntity>)

    @Query("UPDATE messages SET pending = :pending, failed = :failed WHERE id = :id")
    suspend fun markState(id: Long, pending: Boolean, failed: Boolean)

    @Query("SELECT COUNT(*) FROM messages WHERE pending = 1")
    fun observePendingCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM messages")
    fun observeTotalCount(): Flow<Int>
}

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(session: SessionEntity)

    @Query("SELECT * FROM sessions WHERE id = :id LIMIT 1")
    suspend fun session(id: String): SessionEntity?
}
