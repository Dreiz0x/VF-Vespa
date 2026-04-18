package dev.vskelk.cdf.core.database.di

import dev.vskelk.cdf.core.database.AppDatabase
import dev.vskelk.cdf.core.database.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseSeeder @Inject constructor(
    private val db: AppDatabase
) {
    suspend fun seed() = withContext(Dispatchers.IO) {
        if (db.ontologyDao().observeActiveNodes().firstOrNull()?.isNotEmpty() == true) return@withContext

        val now = System.currentTimeMillis()

        // 1. Ontology Nodes
        val node1Id = db.ontologyDao().upsertNode(
            OntologyNodeEntity(
                nodeKey = "COMP_001",
                nodeType = "COMPETENCIA",
                label = "Pensamiento Analítico",
                description = "Capacidad de descomponer problemas complejos.",
                status = "ACTIVE",
                confidence = 0.95,
                createdAt = now,
                updatedAt = now
            )
        )

        val node2Id = db.ontologyDao().upsertNode(
            OntologyNodeEntity(
                nodeKey = "COMP_002",
                nodeType = "BEHAVIOR",
                label = "Entrevista Técnica",
                description = "Habilidades de comunicación en entrevistas.",
                status = "ACTIVE",
                confidence = 0.88,
                createdAt = now,
                updatedAt = now
            )
        )

        // 2. Reactivos
        val reactivo1Id = db.reactivoDao().upsertReactivo(
            ReactivoEntity(
                reactivoKey = "REA_001",
                primaryNodeId = node1Id,
                stem = "¿Cuál es el primer paso del método científico?",
                formatType = "MULTIPLE_CHOICE",
                examArea = "TECNICO",
                cognitiveLevel = "CONOCIMIENTO",
                status = "ACTIVE",
                sourceMode = "MANUAL",
                createdAt = now,
                updatedAt = now
            )
        )

        db.reactivoDao().upsertOptions(listOf(
            ReactivoOptionEntity(reactivoId = reactivo1Id, position = 1, label = "A", text = "Observación", isCorrect = true, rationale = "La observación es el inicio."),
            ReactivoOptionEntity(reactivoId = reactivo1Id, position = 2, label = "B", text = "Hipótesis", isCorrect = false, rationale = "Viene después."),
            ReactivoOptionEntity(reactivoId = reactivo1Id, position = 3, label = "C", text = "Experimentación", isCorrect = false, rationale = "Es un paso intermedio.")
        ))

        db.reactivoDao().upsertMetadata(
            ReactivoMetadataEntity(
                reactivoId = reactivo1Id,
                difficulty = 0.3,
                discrimination = 0.5,
                estimatedTimeSec = 60,
                reviewState = "APPROVED",
                createdAt = now
            )
        )

        // 3. Normative
        val sourceId = db.normativeDao().upsertSource(
            NormSourceEntity(
                code = "CONST_01",
                title = "Constitución Política",
                sourceType = "LEY",
                issuer = "Congreso",
                jurisdiction = "Nacional",
                createdAt = now,
                updatedAt = now,
                canonicalUrl = null,
                officialPublicationDate = null
            )
        )

        val versionId = db.normativeDao().upsertVersion(
            NormVersionEntity(
                sourceId = sourceId,
                versionLabel = "2024-V1",
                digest = "abc",
                validFrom = now,
                validTo = null,
                isCurrent = true,
                changeSummary = "Initial",
                supersedesVersionId = null,
                createdAt = now
            )
        )

        db.normativeDao().upsertFragments(listOf(
            NormFragmentEntity(
                versionId = versionId,
                fragmentKey = "ART_1",
                fragmentType = "ARTICULO",
                heading = "Artículo 1",
                body = "El derecho a la educación es fundamental.",
                normalizedBody = "derecho educacion fundamental",
                createdAt = now,
                parentFragmentId = null,
                ordinal = "1",
                startOffset = null,
                endOffset = null
            )
        ))
    }
}
