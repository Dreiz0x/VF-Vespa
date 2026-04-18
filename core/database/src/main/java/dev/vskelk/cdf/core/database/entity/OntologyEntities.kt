package dev.vskelk.cdf.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "norm_sources",
    indices = [
        Index(value = ["code"], unique = true),
        Index(value = ["sourceType"]),
    ],
)
data class NormSourceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val code: String,
    val title: String,
    val sourceType: String,
    val issuer: String,
    val jurisdiction: String,
    val canonicalUrl: String?,
    val officialPublicationDate: Long?,
    val createdAt: Long,
    val updatedAt: Long,
)

@Entity(
    tableName = "norm_versions",
    foreignKeys = [
        ForeignKey(
            entity = NormSourceEntity::class,
            parentColumns = ["id"],
            childColumns = ["sourceId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("sourceId"),
        Index(value = ["sourceId", "versionLabel"], unique = true),
        Index("validFrom"),
        Index("validTo"),
        Index("isCurrent"),
    ],
)
data class NormVersionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceId: Long,
    val versionLabel: String,
    val digest: String,
    val validFrom: Long,
    val validTo: Long?,
    val isCurrent: Boolean,
    val changeSummary: String?,
    val supersedesVersionId: Long?,
    val createdAt: Long,
)

@Entity(
    tableName = "norm_fragments",
    foreignKeys = [
        ForeignKey(
            entity = NormVersionEntity::class,
            parentColumns = ["id"],
            childColumns = ["versionId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("versionId"),
        Index(value = ["versionId", "fragmentKey"], unique = true),
        Index("fragmentType"),
        Index("parentFragmentId"),
    ],
)
data class NormFragmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val versionId: Long,
    val fragmentKey: String,
    val parentFragmentId: Long?,
    val fragmentType: String,
    val ordinal: String?,
    val heading: String?,
    val body: String,
    val normalizedBody: String,
    val startOffset: Int?,
    val endOffset: Int?,
    val createdAt: Long,
)

@Entity(
    tableName = "ontology_nodes",
    indices = [
        Index(value = ["nodeKey"], unique = true),
        Index("nodeType"),
        Index("status"),
    ],
)
data class OntologyNodeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nodeKey: String,
    val nodeType: String,
    val label: String,
    val description: String,
    val status: String,
    val confidence: Double,
    val createdAt: Long,
    val updatedAt: Long,
)

@Entity(
    tableName = "ontology_edges",
    foreignKeys = [
        ForeignKey(
            entity = OntologyNodeEntity::class,
            parentColumns = ["id"],
            childColumns = ["fromNodeId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = OntologyNodeEntity::class,
            parentColumns = ["id"],
            childColumns = ["toNodeId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("fromNodeId"),
        Index("toNodeId"),
        Index("relationType"),
        Index(value = ["fromNodeId", "relationType", "toNodeId"], unique = true),
    ],
)
data class OntologyEdgeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fromNodeId: Long,
    val toNodeId: Long,
    val relationType: String,
    val weight: Double,
    val createdAt: Long,
)

@Entity(
    tableName = "node_fragment_cross_ref",
    primaryKeys = ["nodeId", "fragmentId"],
    foreignKeys = [
        ForeignKey(
            entity = OntologyNodeEntity::class,
            parentColumns = ["id"],
            childColumns = ["nodeId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = NormFragmentEntity::class,
            parentColumns = ["id"],
            childColumns = ["fragmentId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("fragmentId")],
)
data class NodeFragmentCrossRef(
    val nodeId: Long,
    val fragmentId: Long,
    val mappingType: String,
    val priority: Int,
    val createdAt: Long,
)

@Entity(
    tableName = "reactivos",
    foreignKeys = [
        ForeignKey(
            entity = OntologyNodeEntity::class,
            parentColumns = ["id"],
            childColumns = ["primaryNodeId"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
    indices = [
        Index(value = ["reactivoKey"], unique = true),
        Index("primaryNodeId"),
        Index("formatType"),
        Index("status"),
        Index(value = ["examArea", "status", "cognitiveLevel"]),
    ],
)
data class ReactivoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reactivoKey: String,
    val primaryNodeId: Long,
    val stem: String,
    val formatType: String,
    val examArea: String,
    val cognitiveLevel: String,
    val status: String,
    val sourceMode: String,
    val createdAt: Long,
    val updatedAt: Long,
)

@Entity(
    tableName = "reactivo_options",
    foreignKeys = [
        ForeignKey(
            entity = ReactivoEntity::class,
            parentColumns = ["id"],
            childColumns = ["reactivoId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("reactivoId"),
        Index(value = ["reactivoId", "position"], unique = true),
    ],
)
data class ReactivoOptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reactivoId: Long,
    val position: Int,
    val label: String,
    val text: String,
    val isCorrect: Boolean,
    val distractorType: String?,
    val rationale: String?,
)

@Entity(
    tableName = "reactivo_metadata",
    foreignKeys = [
        ForeignKey(
            entity = ReactivoEntity::class,
            parentColumns = ["id"],
            childColumns = ["reactivoId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["reactivoId"], unique = true),
        Index("difficulty"),
        Index("reviewState"),
    ],
)
data class ReactivoMetadataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reactivoId: Long,
    val difficulty: Double,
    val discrimination: Double?,
    val estimatedTimeSec: Int,
    val reviewState: String,
    val reviewerNotes: String?,
    val commonErrorPattern: String?,
    val blueprintWeight: Double?,
    val lastReviewedAt: Long?,
    val createdAt: Long,
)

@Entity(
    tableName = "reactivo_validity",
    foreignKeys = [
        ForeignKey(
            entity = ReactivoEntity::class,
            parentColumns = ["id"],
            childColumns = ["reactivoId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = NormVersionEntity::class,
            parentColumns = ["id"],
            childColumns = ["normVersionId"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
    indices = [
        Index(value = ["reactivoId"], unique = true),
        Index("normVersionId"),
        Index("validFrom"),
        Index("validTo"),
        Index("isCurrent"),
    ],
)
data class ReactivoValidityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reactivoId: Long,
    val normVersionId: Long,
    val validFrom: Long,
    val validTo: Long?,
    val isCurrent: Boolean,
    val invalidationReason: String?,
    val supersededByReactivoId: Long?,
    val createdAt: Long,
)

@Entity(
    tableName = "reactivo_fragment_cross_ref",
    primaryKeys = ["reactivoId", "fragmentId"],
    foreignKeys = [
        ForeignKey(
            entity = ReactivoEntity::class,
            parentColumns = ["id"],
            childColumns = ["reactivoId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = NormFragmentEntity::class,
            parentColumns = ["id"],
            childColumns = ["fragmentId"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
    indices = [Index("fragmentId")],
)
data class ReactivoFragmentCrossRef(
    val reactivoId: Long,
    val fragmentId: Long,
    val referenceRole: String,
    val citationOrder: Int,
)

@Entity(
    tableName = "reactivo_node_cross_ref",
    primaryKeys = ["reactivoId", "nodeId"],
    foreignKeys = [
        ForeignKey(
            entity = ReactivoEntity::class,
            parentColumns = ["id"],
            childColumns = ["reactivoId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = OntologyNodeEntity::class,
            parentColumns = ["id"],
            childColumns = ["nodeId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("nodeId")],
)
data class ReactivoNodeCrossRef(
    val reactivoId: Long,
    val nodeId: Long,
    val semanticRole: String,
    val weight: Double,
)
