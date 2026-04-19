package dev.vskelk.cdf.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.vskelk.cdf.core.database.entity.NormFragmentEntity
import dev.vskelk.cdf.core.database.entity.NormSourceEntity
import dev.vskelk.cdf.core.database.entity.NormVersionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NormativeDao {

    @Query("SELECT * FROM norm_sources ORDER BY title ASC")
    fun observeSources(): Flow<List<NormSourceEntity>>

    @Query("SELECT * FROM norm_sources ORDER BY title ASC")
    fun observeAllSources(): Flow<List<NormSourceEntity>>

    @Query("""
        SELECT nf.* FROM norm_fragments nf
        INNER JOIN norm_versions nv ON nv.id = nf.versionId
        WHERE nv.isCurrent = 1
          AND (nf.normalizedBody LIKE '%' || :keyword || '%'
               OR nf.heading LIKE '%' || :keyword || '%')
        LIMIT :limit
    """)
    suspend fun searchByKeyword(keyword: String, limit: Int = 10): List<NormFragmentEntity>

    @Query("""
        SELECT nf.* FROM norm_fragments nf
        INNER JOIN norm_versions nv ON nv.id = nf.versionId
        INNER JOIN norm_sources ns ON ns.id = nv.sourceId
        WHERE ns.code = :sourceCode AND nv.isCurrent = 1
        ORDER BY nf.fragmentKey ASC
    """)
    fun observeCurrentFragmentsBySource(sourceCode: String): Flow<List<NormFragmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSource(entity: NormSourceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertVersion(entity: NormVersionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFragments(entities: List<NormFragmentEntity>)
}
