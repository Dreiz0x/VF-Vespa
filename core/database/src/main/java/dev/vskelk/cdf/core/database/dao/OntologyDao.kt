package dev.vskelk.cdf.core.database.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import dev.vskelk.cdf.core.database.entity.NodeFragmentCrossRef
import dev.vskelk.cdf.core.database.entity.NormFragmentEntity
import dev.vskelk.cdf.core.database.entity.OntologyEdgeEntity
import dev.vskelk.cdf.core.database.entity.OntologyNodeEntity
import kotlinx.coroutines.flow.Flow

data class NodeWithFragments(
    @Embedded val node: OntologyNodeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = NodeFragmentCrossRef::class,
            parentColumn = "nodeId",
            entityColumn = "fragmentId",
        ),
    )
    val fragments: List<NormFragmentEntity>,
)

@Dao
interface OntologyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNode(entity: OntologyNodeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNodes(entities: List<OntologyNodeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertEdge(entity: OntologyEdgeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNodeFragmentRefs(refs: List<NodeFragmentCrossRef>)

    @Transaction
    @Query("SELECT * FROM ontology_nodes WHERE id = :nodeId")
    suspend fun getNodeWithFragments(nodeId: Long): NodeWithFragments?

    @Query("SELECT * FROM ontology_nodes WHERE status = 'ACTIVE' ORDER BY updatedAt DESC")
    fun observeActiveNodes(): Flow<List<OntologyNodeEntity>>
}
