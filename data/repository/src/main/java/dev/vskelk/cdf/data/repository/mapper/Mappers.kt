package dev.vskelk.cdf.data.repository.mapper

import dev.vskelk.cdf.core.database.dao.NodeWithFragments
import dev.vskelk.cdf.core.database.dao.ReactivoAggregate
import dev.vskelk.cdf.core.database.entity.MessageEntity
import dev.vskelk.cdf.core.database.entity.NormFragmentEntity
import dev.vskelk.cdf.core.database.entity.NormSourceEntity
import dev.vskelk.cdf.core.database.entity.OntologyNodeEntity
import dev.vskelk.cdf.core.database.entity.ReactivoEntity
import dev.vskelk.cdf.core.database.entity.ReactivoMetadataEntity
import dev.vskelk.cdf.core.database.entity.ReactivoOptionEntity
import dev.vskelk.cdf.core.database.entity.ReactivoValidityEntity
import dev.vskelk.cdf.domain.model.Message
import dev.vskelk.cdf.domain.model.MessageRole
import dev.vskelk.cdf.domain.model.NodeWithNormativeFragments
import dev.vskelk.cdf.domain.model.NormFragment
import dev.vskelk.cdf.domain.model.NormSource
import dev.vskelk.cdf.domain.model.OntologyNode
import dev.vskelk.cdf.domain.model.Reactivo
import dev.vskelk.cdf.domain.model.ReactivoAggregateModel
import dev.vskelk.cdf.domain.model.ReactivoMetadata
import dev.vskelk.cdf.domain.model.ReactivoOption
import dev.vskelk.cdf.domain.model.ReactivoValidity

// Conversation mappers
fun MessageEntity.toDomain(): Message = Message(
    id = id, sessionId = sessionId, role = MessageRole.valueOf(role),
    content = content, createdAt = createdAt, pending = pending, failed = failed,
)

fun Message.toEntity(): MessageEntity = MessageEntity(
    id = id, sessionId = sessionId, role = role.name,
    content = content, createdAt = createdAt, pending = pending, failed = failed,
)

// Normative mappers
fun NormSourceEntity.toDomain(): NormSource = NormSource(
    id = id, code = code, title = title, sourceType = sourceType,
    issuer = issuer, jurisdiction = jurisdiction, canonicalUrl = canonicalUrl,
    officialPublicationDate = officialPublicationDate,
)

fun NormFragmentEntity.toDomain(): NormFragment = NormFragment(
    id = id, versionId = versionId, fragmentKey = fragmentKey,
    parentFragmentId = parentFragmentId, fragmentType = fragmentType,
    ordinal = ordinal, heading = heading, body = body, normalizedBody = normalizedBody,
)

// Ontology mappers
fun OntologyNodeEntity.toDomain(): OntologyNode = OntologyNode(
    id = id, nodeKey = nodeKey, nodeType = nodeType, label = label,
    description = description, status = status, confidence = confidence,
)

fun ReactivoEntity.toDomain(): Reactivo = Reactivo(
    id = id, reactivoKey = reactivoKey, primaryNodeId = primaryNodeId, stem = stem,
    formatType = formatType, examArea = examArea, cognitiveLevel = cognitiveLevel,
    status = status, sourceMode = sourceMode, createdAt = createdAt, updatedAt = updatedAt,
)

fun ReactivoOptionEntity.toDomain(): ReactivoOption = ReactivoOption(
    id = id, position = position, label = label, text = text,
    isCorrect = isCorrect, distractorType = distractorType, rationale = rationale,
)

fun ReactivoMetadataEntity.toDomain(): ReactivoMetadata = ReactivoMetadata(
    difficulty = difficulty, discrimination = discrimination,
    estimatedTimeSec = estimatedTimeSec, reviewState = reviewState,
    reviewerNotes = reviewerNotes, commonErrorPattern = commonErrorPattern,
    blueprintWeight = blueprintWeight, lastReviewedAt = lastReviewedAt,
)

fun ReactivoValidityEntity.toDomain(): ReactivoValidity = ReactivoValidity(
    normVersionId = normVersionId, validFrom = validFrom, validTo = validTo,
    isCurrent = isCurrent, invalidationReason = invalidationReason,
    supersededByReactivoId = supersededByReactivoId,
)

fun NodeWithFragments.toDomain(): NodeWithNormativeFragments = NodeWithNormativeFragments(
    node = node.toDomain(), fragments = fragments.map { it.toDomain() },
)

fun ReactivoAggregate.toDomain(): ReactivoAggregateModel = ReactivoAggregateModel(
    reactivo = reactivo.toDomain(),
    options = options.sortedBy { it.position }.map { it.toDomain() },
    metadata = metadata.firstOrNull()?.toDomain(),
    validity = validity.firstOrNull()?.toDomain(),
    nodes = nodes.map { it.toDomain() },
    fragments = fragments.map { it.toDomain() },
)
