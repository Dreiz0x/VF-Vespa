package dev.vskelk.cdf.domain.model

data class NormSource(
    val id: Long,
    val code: String,
    val title: String,
    val sourceType: String,
    val issuer: String,
    val jurisdiction: String,
    val canonicalUrl: String?,
    val officialPublicationDate: Long?,
)

data class NormFragment(
    val id: Long,
    val versionId: Long,
    val fragmentKey: String,
    val parentFragmentId: Long?,
    val fragmentType: String,
    val ordinal: String?,
    val heading: String?,
    val body: String,
    val normalizedBody: String,
)

data class OntologyNode(
    val id: Long,
    val nodeKey: String,
    val nodeType: String,
    val label: String,
    val description: String,
    val status: String,
    val confidence: Double,
)

data class NodeWithNormativeFragments(
    val node: OntologyNode,
    val fragments: List<NormFragment>,
)

data class ReactivoOption(
    val id: Long,
    val position: Int,
    val label: String,
    val text: String,
    val isCorrect: Boolean,
    val distractorType: String?,
    val rationale: String?,
)

data class ReactivoMetadata(
    val difficulty: Double,
    val discrimination: Double?,
    val estimatedTimeSec: Int,
    val reviewState: String,
    val reviewerNotes: String?,
    val commonErrorPattern: String?,
    val blueprintWeight: Double?,
    val lastReviewedAt: Long?,
)

data class ReactivoValidity(
    val normVersionId: Long,
    val validFrom: Long,
    val validTo: Long?,
    val isCurrent: Boolean,
    val invalidationReason: String?,
    val supersededByReactivoId: Long?,
)

data class Reactivo(
    val id: Long,
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

data class ReactivoAggregateModel(
    val reactivo: Reactivo,
    val options: List<ReactivoOption>,
    val metadata: ReactivoMetadata?,
    val validity: ReactivoValidity?,
    val nodes: List<OntologyNode>,
    val fragments: List<NormFragment>,
)

data class ReactivoDiagnostic(
    val examArea: String,
    val count: Int,
    val byCognitiveLevel: Map<String, Int>,
)
