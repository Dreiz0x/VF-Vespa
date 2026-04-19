package dev.vskelk.cdf.core.database.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import dev.vskelk.cdf.core.database.entity.NormFragmentEntity
import dev.vskelk.cdf.core.database.entity.OntologyNodeEntity
import dev.vskelk.cdf.core.database.entity.ReactivoEntity
import dev.vskelk.cdf.core.database.entity.ReactivoFragmentCrossRef
import dev.vskelk.cdf.core.database.entity.ReactivoMetadataEntity
import dev.vskelk.cdf.core.database.entity.ReactivoNodeCrossRef
import dev.vskelk.cdf.core.database.entity.ReactivoOptionEntity
import dev.vskelk.cdf.core.database.entity.ReactivoValidityEntity
import kotlinx.coroutines.flow.Flow

data class ReactivoAggregate(
    @Embedded val reactivo: ReactivoEntity,
    @Relation(parentColumn = "id", entityColumn = "reactivoId")
    val options: List<ReactivoOptionEntity>,
    @Relation(parentColumn = "id", entityColumn = "reactivoId")
    val metadata: List<ReactivoMetadataEntity>,
    @Relation(parentColumn = "id", entityColumn = "reactivoId")
    val validity: List<ReactivoValidityEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ReactivoNodeCrossRef::class,
            parentColumn = "reactivoId",
            entityColumn = "nodeId",
        ),
    )
    val nodes: List<OntologyNodeEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ReactivoFragmentCrossRef::class,
            parentColumn = "reactivoId",
            entityColumn = "fragmentId",
        ),
    )
    val fragments: List<NormFragmentEntity>,
)

@Dao
interface ReactivoDao {

    @Transaction
    @Query("SELECT * FROM reactivos WHERE id = :reactivoId")
    suspend fun getReactivoAggregate(reactivoId: Long): ReactivoAggregate?

    @Transaction
    @Query(
        """
        SELECT * FROM reactivos
        WHERE status = 'ACTIVE'
          AND (:examArea IS NULL OR examArea = :examArea)
        ORDER BY updatedAt DESC
        """,
    )
    fun observeActiveReactivos(examArea: String?): Flow<List<ReactivoAggregate>>

    @Query("SELECT * FROM reactivos")
    fun observeAllReactivos(): Flow<List<ReactivoEntity>>

    @Transaction
    @Query(
        """
        SELECT * FROM reactivos
        WHERE status = 'ACTIVE'
          AND (:examArea IS NULL OR examArea = :examArea)
        ORDER BY RANDOM()
        LIMIT :limit
        """,
    )
    suspend fun getRandomActiveReactivoAggregates(
        examArea: String?,
        limit: Int,
    ): List<ReactivoAggregate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertReactivo(entity: ReactivoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOptions(options: List<ReactivoOptionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMetadata(entity: ReactivoMetadataEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertValidity(entity: ReactivoValidityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNodeRefs(refs: List<ReactivoNodeCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFragmentRefs(refs: List<ReactivoFragmentCrossRef>)

    @Query(
        """
        UPDATE reactivo_validity
        SET isCurrent = 0,
            validTo = :invalidatedAt,
            invalidationReason = :reason
        WHERE normVersionId = :normVersionId AND isCurrent = 1
        """,
    )
    suspend fun invalidateByNormVersion(
        normVersionId: Long,
        reason: String,
        invalidatedAt: Long,
    ): Int
}
