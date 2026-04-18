package dev.vskelk.cdf.core.database.dao

import androidx.collection.LongSparseArray
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndex
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.getTotalChangedRows
import androidx.room.util.performSuspending
import androidx.room.util.recursiveFetchLongSparseArray
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteStatement
import dev.vskelk.cdf.core.database.entity.NormFragmentEntity
import dev.vskelk.cdf.core.database.entity.OntologyNodeEntity
import dev.vskelk.cdf.core.database.entity.ReactivoEntity
import dev.vskelk.cdf.core.database.entity.ReactivoFragmentCrossRef
import dev.vskelk.cdf.core.database.entity.ReactivoMetadataEntity
import dev.vskelk.cdf.core.database.entity.ReactivoNodeCrossRef
import dev.vskelk.cdf.core.database.entity.ReactivoOptionEntity
import dev.vskelk.cdf.core.database.entity.ReactivoValidityEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlin.text.StringBuilder
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ReactivoDao_Impl(
  __db: RoomDatabase,
) : ReactivoDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfReactivoEntity: EntityInsertAdapter<ReactivoEntity>

  private val __insertAdapterOfReactivoOptionEntity: EntityInsertAdapter<ReactivoOptionEntity>

  private val __insertAdapterOfReactivoMetadataEntity: EntityInsertAdapter<ReactivoMetadataEntity>

  private val __insertAdapterOfReactivoValidityEntity: EntityInsertAdapter<ReactivoValidityEntity>

  private val __insertAdapterOfReactivoNodeCrossRef: EntityInsertAdapter<ReactivoNodeCrossRef>

  private val __insertAdapterOfReactivoFragmentCrossRef:
      EntityInsertAdapter<ReactivoFragmentCrossRef>
  init {
    this.__db = __db
    this.__insertAdapterOfReactivoEntity = object : EntityInsertAdapter<ReactivoEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `reactivos` (`id`,`reactivoKey`,`primaryNodeId`,`stem`,`formatType`,`examArea`,`cognitiveLevel`,`status`,`sourceMode`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ReactivoEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.reactivoKey)
        statement.bindLong(3, entity.primaryNodeId)
        statement.bindText(4, entity.stem)
        statement.bindText(5, entity.formatType)
        statement.bindText(6, entity.examArea)
        statement.bindText(7, entity.cognitiveLevel)
        statement.bindText(8, entity.status)
        statement.bindText(9, entity.sourceMode)
        statement.bindLong(10, entity.createdAt)
        statement.bindLong(11, entity.updatedAt)
      }
    }
    this.__insertAdapterOfReactivoOptionEntity = object :
        EntityInsertAdapter<ReactivoOptionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `reactivo_options` (`id`,`reactivoId`,`position`,`label`,`text`,`isCorrect`,`distractorType`,`rationale`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ReactivoOptionEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.reactivoId)
        statement.bindLong(3, entity.position.toLong())
        statement.bindText(4, entity.label)
        statement.bindText(5, entity.text)
        val _tmp: Int = if (entity.isCorrect) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        val _tmpDistractorType: String? = entity.distractorType
        if (_tmpDistractorType == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpDistractorType)
        }
        val _tmpRationale: String? = entity.rationale
        if (_tmpRationale == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpRationale)
        }
      }
    }
    this.__insertAdapterOfReactivoMetadataEntity = object :
        EntityInsertAdapter<ReactivoMetadataEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `reactivo_metadata` (`id`,`reactivoId`,`difficulty`,`discrimination`,`estimatedTimeSec`,`reviewState`,`reviewerNotes`,`commonErrorPattern`,`blueprintWeight`,`lastReviewedAt`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ReactivoMetadataEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.reactivoId)
        statement.bindDouble(3, entity.difficulty)
        val _tmpDiscrimination: Double? = entity.discrimination
        if (_tmpDiscrimination == null) {
          statement.bindNull(4)
        } else {
          statement.bindDouble(4, _tmpDiscrimination)
        }
        statement.bindLong(5, entity.estimatedTimeSec.toLong())
        statement.bindText(6, entity.reviewState)
        val _tmpReviewerNotes: String? = entity.reviewerNotes
        if (_tmpReviewerNotes == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpReviewerNotes)
        }
        val _tmpCommonErrorPattern: String? = entity.commonErrorPattern
        if (_tmpCommonErrorPattern == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpCommonErrorPattern)
        }
        val _tmpBlueprintWeight: Double? = entity.blueprintWeight
        if (_tmpBlueprintWeight == null) {
          statement.bindNull(9)
        } else {
          statement.bindDouble(9, _tmpBlueprintWeight)
        }
        val _tmpLastReviewedAt: Long? = entity.lastReviewedAt
        if (_tmpLastReviewedAt == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpLastReviewedAt)
        }
        statement.bindLong(11, entity.createdAt)
      }
    }
    this.__insertAdapterOfReactivoValidityEntity = object :
        EntityInsertAdapter<ReactivoValidityEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `reactivo_validity` (`id`,`reactivoId`,`normVersionId`,`validFrom`,`validTo`,`isCurrent`,`invalidationReason`,`supersededByReactivoId`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ReactivoValidityEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.reactivoId)
        statement.bindLong(3, entity.normVersionId)
        statement.bindLong(4, entity.validFrom)
        val _tmpValidTo: Long? = entity.validTo
        if (_tmpValidTo == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpValidTo)
        }
        val _tmp: Int = if (entity.isCurrent) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        val _tmpInvalidationReason: String? = entity.invalidationReason
        if (_tmpInvalidationReason == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpInvalidationReason)
        }
        val _tmpSupersededByReactivoId: Long? = entity.supersededByReactivoId
        if (_tmpSupersededByReactivoId == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpSupersededByReactivoId)
        }
        statement.bindLong(9, entity.createdAt)
      }
    }
    this.__insertAdapterOfReactivoNodeCrossRef = object :
        EntityInsertAdapter<ReactivoNodeCrossRef>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `reactivo_node_cross_ref` (`reactivoId`,`nodeId`,`semanticRole`,`weight`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ReactivoNodeCrossRef) {
        statement.bindLong(1, entity.reactivoId)
        statement.bindLong(2, entity.nodeId)
        statement.bindText(3, entity.semanticRole)
        statement.bindDouble(4, entity.weight)
      }
    }
    this.__insertAdapterOfReactivoFragmentCrossRef = object :
        EntityInsertAdapter<ReactivoFragmentCrossRef>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `reactivo_fragment_cross_ref` (`reactivoId`,`fragmentId`,`referenceRole`,`citationOrder`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ReactivoFragmentCrossRef) {
        statement.bindLong(1, entity.reactivoId)
        statement.bindLong(2, entity.fragmentId)
        statement.bindText(3, entity.referenceRole)
        statement.bindLong(4, entity.citationOrder.toLong())
      }
    }
  }

  public override suspend fun upsertReactivo(entity: ReactivoEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfReactivoEntity.insertAndReturnId(_connection, entity)
    _result
  }

  public override suspend fun upsertOptions(options: List<ReactivoOptionEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfReactivoOptionEntity.insert(_connection, options)
  }

  public override suspend fun upsertMetadata(entity: ReactivoMetadataEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfReactivoMetadataEntity.insertAndReturnId(_connection,
        entity)
    _result
  }

  public override suspend fun upsertValidity(entity: ReactivoValidityEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfReactivoValidityEntity.insertAndReturnId(_connection,
        entity)
    _result
  }

  public override suspend fun upsertNodeRefs(refs: List<ReactivoNodeCrossRef>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfReactivoNodeCrossRef.insert(_connection, refs)
  }

  public override suspend fun upsertFragmentRefs(refs: List<ReactivoFragmentCrossRef>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfReactivoFragmentCrossRef.insert(_connection, refs)
  }

  public override suspend fun getReactivoAggregate(reactivoId: Long): ReactivoAggregate? {
    val _sql: String = "SELECT * FROM reactivos WHERE id = ?"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, reactivoId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReactivoKey: Int = getColumnIndexOrThrow(_stmt, "reactivoKey")
        val _columnIndexOfPrimaryNodeId: Int = getColumnIndexOrThrow(_stmt, "primaryNodeId")
        val _columnIndexOfStem: Int = getColumnIndexOrThrow(_stmt, "stem")
        val _columnIndexOfFormatType: Int = getColumnIndexOrThrow(_stmt, "formatType")
        val _columnIndexOfExamArea: Int = getColumnIndexOrThrow(_stmt, "examArea")
        val _columnIndexOfCognitiveLevel: Int = getColumnIndexOrThrow(_stmt, "cognitiveLevel")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfSourceMode: Int = getColumnIndexOrThrow(_stmt, "sourceMode")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _collectionOptions: LongSparseArray<MutableList<ReactivoOptionEntity>> =
            LongSparseArray<MutableList<ReactivoOptionEntity>>()
        val _collectionMetadata: LongSparseArray<MutableList<ReactivoMetadataEntity>> =
            LongSparseArray<MutableList<ReactivoMetadataEntity>>()
        val _collectionValidity: LongSparseArray<MutableList<ReactivoValidityEntity>> =
            LongSparseArray<MutableList<ReactivoValidityEntity>>()
        val _collectionNodes: LongSparseArray<MutableList<OntologyNodeEntity>> =
            LongSparseArray<MutableList<OntologyNodeEntity>>()
        val _collectionFragments: LongSparseArray<MutableList<NormFragmentEntity>> =
            LongSparseArray<MutableList<NormFragmentEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionOptions.containsKey(_tmpKey)) {
            _collectionOptions.put(_tmpKey, mutableListOf())
          }
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionMetadata.containsKey(_tmpKey_1)) {
            _collectionMetadata.put(_tmpKey_1, mutableListOf())
          }
          val _tmpKey_2: Long
          _tmpKey_2 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionValidity.containsKey(_tmpKey_2)) {
            _collectionValidity.put(_tmpKey_2, mutableListOf())
          }
          val _tmpKey_3: Long
          _tmpKey_3 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionNodes.containsKey(_tmpKey_3)) {
            _collectionNodes.put(_tmpKey_3, mutableListOf())
          }
          val _tmpKey_4: Long
          _tmpKey_4 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionFragments.containsKey(_tmpKey_4)) {
            _collectionFragments.put(_tmpKey_4, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshipreactivoOptionsAsdevVskelkCdfCoreDatabaseEntityReactivoOptionEntity(_connection,
            _collectionOptions)
        __fetchRelationshipreactivoMetadataAsdevVskelkCdfCoreDatabaseEntityReactivoMetadataEntity(_connection,
            _collectionMetadata)
        __fetchRelationshipreactivoValidityAsdevVskelkCdfCoreDatabaseEntityReactivoValidityEntity(_connection,
            _collectionValidity)
        __fetchRelationshipontologyNodesAsdevVskelkCdfCoreDatabaseEntityOntologyNodeEntity(_connection,
            _collectionNodes)
        __fetchRelationshipnormFragmentsAsdevVskelkCdfCoreDatabaseEntityNormFragmentEntity(_connection,
            _collectionFragments)
        val _result: ReactivoAggregate?
        if (_stmt.step()) {
          val _tmpReactivo: ReactivoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpReactivoKey: String
          _tmpReactivoKey = _stmt.getText(_columnIndexOfReactivoKey)
          val _tmpPrimaryNodeId: Long
          _tmpPrimaryNodeId = _stmt.getLong(_columnIndexOfPrimaryNodeId)
          val _tmpStem: String
          _tmpStem = _stmt.getText(_columnIndexOfStem)
          val _tmpFormatType: String
          _tmpFormatType = _stmt.getText(_columnIndexOfFormatType)
          val _tmpExamArea: String
          _tmpExamArea = _stmt.getText(_columnIndexOfExamArea)
          val _tmpCognitiveLevel: String
          _tmpCognitiveLevel = _stmt.getText(_columnIndexOfCognitiveLevel)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpSourceMode: String
          _tmpSourceMode = _stmt.getText(_columnIndexOfSourceMode)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _tmpReactivo =
              ReactivoEntity(_tmpId,_tmpReactivoKey,_tmpPrimaryNodeId,_tmpStem,_tmpFormatType,_tmpExamArea,_tmpCognitiveLevel,_tmpStatus,_tmpSourceMode,_tmpCreatedAt,_tmpUpdatedAt)
          val _tmpOptionsCollection: MutableList<ReactivoOptionEntity>
          val _tmpKey_5: Long
          _tmpKey_5 = _stmt.getLong(_columnIndexOfId)
          _tmpOptionsCollection = checkNotNull(_collectionOptions.get(_tmpKey_5))
          val _tmpMetadataCollection: MutableList<ReactivoMetadataEntity>
          val _tmpKey_6: Long
          _tmpKey_6 = _stmt.getLong(_columnIndexOfId)
          _tmpMetadataCollection = checkNotNull(_collectionMetadata.get(_tmpKey_6))
          val _tmpValidityCollection: MutableList<ReactivoValidityEntity>
          val _tmpKey_7: Long
          _tmpKey_7 = _stmt.getLong(_columnIndexOfId)
          _tmpValidityCollection = checkNotNull(_collectionValidity.get(_tmpKey_7))
          val _tmpNodesCollection: MutableList<OntologyNodeEntity>
          val _tmpKey_8: Long
          _tmpKey_8 = _stmt.getLong(_columnIndexOfId)
          _tmpNodesCollection = checkNotNull(_collectionNodes.get(_tmpKey_8))
          val _tmpFragmentsCollection: MutableList<NormFragmentEntity>
          val _tmpKey_9: Long
          _tmpKey_9 = _stmt.getLong(_columnIndexOfId)
          _tmpFragmentsCollection = checkNotNull(_collectionFragments.get(_tmpKey_9))
          _result =
              ReactivoAggregate(_tmpReactivo,_tmpOptionsCollection,_tmpMetadataCollection,_tmpValidityCollection,_tmpNodesCollection,_tmpFragmentsCollection)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeActiveReactivos(examArea: String?): Flow<List<ReactivoAggregate>> {
    val _sql: String = """
        |
        |        SELECT * FROM reactivos
        |        WHERE status = 'ACTIVE'
        |          AND (? IS NULL OR examArea = ?)
        |        ORDER BY updatedAt DESC
        |        
        """.trimMargin()
    return createFlow(__db, true, arrayOf("reactivo_options", "reactivo_metadata",
        "reactivo_validity", "reactivo_node_cross_ref", "ontology_nodes",
        "reactivo_fragment_cross_ref", "norm_fragments", "reactivos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        if (examArea == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, examArea)
        }
        _argIndex = 2
        if (examArea == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, examArea)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReactivoKey: Int = getColumnIndexOrThrow(_stmt, "reactivoKey")
        val _columnIndexOfPrimaryNodeId: Int = getColumnIndexOrThrow(_stmt, "primaryNodeId")
        val _columnIndexOfStem: Int = getColumnIndexOrThrow(_stmt, "stem")
        val _columnIndexOfFormatType: Int = getColumnIndexOrThrow(_stmt, "formatType")
        val _columnIndexOfExamArea: Int = getColumnIndexOrThrow(_stmt, "examArea")
        val _columnIndexOfCognitiveLevel: Int = getColumnIndexOrThrow(_stmt, "cognitiveLevel")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfSourceMode: Int = getColumnIndexOrThrow(_stmt, "sourceMode")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _collectionOptions: LongSparseArray<MutableList<ReactivoOptionEntity>> =
            LongSparseArray<MutableList<ReactivoOptionEntity>>()
        val _collectionMetadata: LongSparseArray<MutableList<ReactivoMetadataEntity>> =
            LongSparseArray<MutableList<ReactivoMetadataEntity>>()
        val _collectionValidity: LongSparseArray<MutableList<ReactivoValidityEntity>> =
            LongSparseArray<MutableList<ReactivoValidityEntity>>()
        val _collectionNodes: LongSparseArray<MutableList<OntologyNodeEntity>> =
            LongSparseArray<MutableList<OntologyNodeEntity>>()
        val _collectionFragments: LongSparseArray<MutableList<NormFragmentEntity>> =
            LongSparseArray<MutableList<NormFragmentEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionOptions.containsKey(_tmpKey)) {
            _collectionOptions.put(_tmpKey, mutableListOf())
          }
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionMetadata.containsKey(_tmpKey_1)) {
            _collectionMetadata.put(_tmpKey_1, mutableListOf())
          }
          val _tmpKey_2: Long
          _tmpKey_2 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionValidity.containsKey(_tmpKey_2)) {
            _collectionValidity.put(_tmpKey_2, mutableListOf())
          }
          val _tmpKey_3: Long
          _tmpKey_3 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionNodes.containsKey(_tmpKey_3)) {
            _collectionNodes.put(_tmpKey_3, mutableListOf())
          }
          val _tmpKey_4: Long
          _tmpKey_4 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionFragments.containsKey(_tmpKey_4)) {
            _collectionFragments.put(_tmpKey_4, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshipreactivoOptionsAsdevVskelkCdfCoreDatabaseEntityReactivoOptionEntity(_connection,
            _collectionOptions)
        __fetchRelationshipreactivoMetadataAsdevVskelkCdfCoreDatabaseEntityReactivoMetadataEntity(_connection,
            _collectionMetadata)
        __fetchRelationshipreactivoValidityAsdevVskelkCdfCoreDatabaseEntityReactivoValidityEntity(_connection,
            _collectionValidity)
        __fetchRelationshipontologyNodesAsdevVskelkCdfCoreDatabaseEntityOntologyNodeEntity(_connection,
            _collectionNodes)
        __fetchRelationshipnormFragmentsAsdevVskelkCdfCoreDatabaseEntityNormFragmentEntity(_connection,
            _collectionFragments)
        val _result: MutableList<ReactivoAggregate> = mutableListOf()
        while (_stmt.step()) {
          val _item: ReactivoAggregate
          val _tmpReactivo: ReactivoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpReactivoKey: String
          _tmpReactivoKey = _stmt.getText(_columnIndexOfReactivoKey)
          val _tmpPrimaryNodeId: Long
          _tmpPrimaryNodeId = _stmt.getLong(_columnIndexOfPrimaryNodeId)
          val _tmpStem: String
          _tmpStem = _stmt.getText(_columnIndexOfStem)
          val _tmpFormatType: String
          _tmpFormatType = _stmt.getText(_columnIndexOfFormatType)
          val _tmpExamArea: String
          _tmpExamArea = _stmt.getText(_columnIndexOfExamArea)
          val _tmpCognitiveLevel: String
          _tmpCognitiveLevel = _stmt.getText(_columnIndexOfCognitiveLevel)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpSourceMode: String
          _tmpSourceMode = _stmt.getText(_columnIndexOfSourceMode)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _tmpReactivo =
              ReactivoEntity(_tmpId,_tmpReactivoKey,_tmpPrimaryNodeId,_tmpStem,_tmpFormatType,_tmpExamArea,_tmpCognitiveLevel,_tmpStatus,_tmpSourceMode,_tmpCreatedAt,_tmpUpdatedAt)
          val _tmpOptionsCollection: MutableList<ReactivoOptionEntity>
          val _tmpKey_5: Long
          _tmpKey_5 = _stmt.getLong(_columnIndexOfId)
          _tmpOptionsCollection = checkNotNull(_collectionOptions.get(_tmpKey_5))
          val _tmpMetadataCollection: MutableList<ReactivoMetadataEntity>
          val _tmpKey_6: Long
          _tmpKey_6 = _stmt.getLong(_columnIndexOfId)
          _tmpMetadataCollection = checkNotNull(_collectionMetadata.get(_tmpKey_6))
          val _tmpValidityCollection: MutableList<ReactivoValidityEntity>
          val _tmpKey_7: Long
          _tmpKey_7 = _stmt.getLong(_columnIndexOfId)
          _tmpValidityCollection = checkNotNull(_collectionValidity.get(_tmpKey_7))
          val _tmpNodesCollection: MutableList<OntologyNodeEntity>
          val _tmpKey_8: Long
          _tmpKey_8 = _stmt.getLong(_columnIndexOfId)
          _tmpNodesCollection = checkNotNull(_collectionNodes.get(_tmpKey_8))
          val _tmpFragmentsCollection: MutableList<NormFragmentEntity>
          val _tmpKey_9: Long
          _tmpKey_9 = _stmt.getLong(_columnIndexOfId)
          _tmpFragmentsCollection = checkNotNull(_collectionFragments.get(_tmpKey_9))
          _item =
              ReactivoAggregate(_tmpReactivo,_tmpOptionsCollection,_tmpMetadataCollection,_tmpValidityCollection,_tmpNodesCollection,_tmpFragmentsCollection)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getRandomActiveReactivoAggregates(examArea: String?, limit: Int):
      List<ReactivoAggregate> {
    val _sql: String = """
        |
        |        SELECT * FROM reactivos
        |        WHERE status = 'ACTIVE'
        |          AND (? IS NULL OR examArea = ?)
        |        ORDER BY RANDOM()
        |        LIMIT ?
        |        
        """.trimMargin()
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        if (examArea == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, examArea)
        }
        _argIndex = 2
        if (examArea == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, examArea)
        }
        _argIndex = 3
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReactivoKey: Int = getColumnIndexOrThrow(_stmt, "reactivoKey")
        val _columnIndexOfPrimaryNodeId: Int = getColumnIndexOrThrow(_stmt, "primaryNodeId")
        val _columnIndexOfStem: Int = getColumnIndexOrThrow(_stmt, "stem")
        val _columnIndexOfFormatType: Int = getColumnIndexOrThrow(_stmt, "formatType")
        val _columnIndexOfExamArea: Int = getColumnIndexOrThrow(_stmt, "examArea")
        val _columnIndexOfCognitiveLevel: Int = getColumnIndexOrThrow(_stmt, "cognitiveLevel")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfSourceMode: Int = getColumnIndexOrThrow(_stmt, "sourceMode")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _collectionOptions: LongSparseArray<MutableList<ReactivoOptionEntity>> =
            LongSparseArray<MutableList<ReactivoOptionEntity>>()
        val _collectionMetadata: LongSparseArray<MutableList<ReactivoMetadataEntity>> =
            LongSparseArray<MutableList<ReactivoMetadataEntity>>()
        val _collectionValidity: LongSparseArray<MutableList<ReactivoValidityEntity>> =
            LongSparseArray<MutableList<ReactivoValidityEntity>>()
        val _collectionNodes: LongSparseArray<MutableList<OntologyNodeEntity>> =
            LongSparseArray<MutableList<OntologyNodeEntity>>()
        val _collectionFragments: LongSparseArray<MutableList<NormFragmentEntity>> =
            LongSparseArray<MutableList<NormFragmentEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionOptions.containsKey(_tmpKey)) {
            _collectionOptions.put(_tmpKey, mutableListOf())
          }
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionMetadata.containsKey(_tmpKey_1)) {
            _collectionMetadata.put(_tmpKey_1, mutableListOf())
          }
          val _tmpKey_2: Long
          _tmpKey_2 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionValidity.containsKey(_tmpKey_2)) {
            _collectionValidity.put(_tmpKey_2, mutableListOf())
          }
          val _tmpKey_3: Long
          _tmpKey_3 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionNodes.containsKey(_tmpKey_3)) {
            _collectionNodes.put(_tmpKey_3, mutableListOf())
          }
          val _tmpKey_4: Long
          _tmpKey_4 = _stmt.getLong(_columnIndexOfId)
          if (!_collectionFragments.containsKey(_tmpKey_4)) {
            _collectionFragments.put(_tmpKey_4, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshipreactivoOptionsAsdevVskelkCdfCoreDatabaseEntityReactivoOptionEntity(_connection,
            _collectionOptions)
        __fetchRelationshipreactivoMetadataAsdevVskelkCdfCoreDatabaseEntityReactivoMetadataEntity(_connection,
            _collectionMetadata)
        __fetchRelationshipreactivoValidityAsdevVskelkCdfCoreDatabaseEntityReactivoValidityEntity(_connection,
            _collectionValidity)
        __fetchRelationshipontologyNodesAsdevVskelkCdfCoreDatabaseEntityOntologyNodeEntity(_connection,
            _collectionNodes)
        __fetchRelationshipnormFragmentsAsdevVskelkCdfCoreDatabaseEntityNormFragmentEntity(_connection,
            _collectionFragments)
        val _result: MutableList<ReactivoAggregate> = mutableListOf()
        while (_stmt.step()) {
          val _item: ReactivoAggregate
          val _tmpReactivo: ReactivoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpReactivoKey: String
          _tmpReactivoKey = _stmt.getText(_columnIndexOfReactivoKey)
          val _tmpPrimaryNodeId: Long
          _tmpPrimaryNodeId = _stmt.getLong(_columnIndexOfPrimaryNodeId)
          val _tmpStem: String
          _tmpStem = _stmt.getText(_columnIndexOfStem)
          val _tmpFormatType: String
          _tmpFormatType = _stmt.getText(_columnIndexOfFormatType)
          val _tmpExamArea: String
          _tmpExamArea = _stmt.getText(_columnIndexOfExamArea)
          val _tmpCognitiveLevel: String
          _tmpCognitiveLevel = _stmt.getText(_columnIndexOfCognitiveLevel)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpSourceMode: String
          _tmpSourceMode = _stmt.getText(_columnIndexOfSourceMode)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _tmpReactivo =
              ReactivoEntity(_tmpId,_tmpReactivoKey,_tmpPrimaryNodeId,_tmpStem,_tmpFormatType,_tmpExamArea,_tmpCognitiveLevel,_tmpStatus,_tmpSourceMode,_tmpCreatedAt,_tmpUpdatedAt)
          val _tmpOptionsCollection: MutableList<ReactivoOptionEntity>
          val _tmpKey_5: Long
          _tmpKey_5 = _stmt.getLong(_columnIndexOfId)
          _tmpOptionsCollection = checkNotNull(_collectionOptions.get(_tmpKey_5))
          val _tmpMetadataCollection: MutableList<ReactivoMetadataEntity>
          val _tmpKey_6: Long
          _tmpKey_6 = _stmt.getLong(_columnIndexOfId)
          _tmpMetadataCollection = checkNotNull(_collectionMetadata.get(_tmpKey_6))
          val _tmpValidityCollection: MutableList<ReactivoValidityEntity>
          val _tmpKey_7: Long
          _tmpKey_7 = _stmt.getLong(_columnIndexOfId)
          _tmpValidityCollection = checkNotNull(_collectionValidity.get(_tmpKey_7))
          val _tmpNodesCollection: MutableList<OntologyNodeEntity>
          val _tmpKey_8: Long
          _tmpKey_8 = _stmt.getLong(_columnIndexOfId)
          _tmpNodesCollection = checkNotNull(_collectionNodes.get(_tmpKey_8))
          val _tmpFragmentsCollection: MutableList<NormFragmentEntity>
          val _tmpKey_9: Long
          _tmpKey_9 = _stmt.getLong(_columnIndexOfId)
          _tmpFragmentsCollection = checkNotNull(_collectionFragments.get(_tmpKey_9))
          _item =
              ReactivoAggregate(_tmpReactivo,_tmpOptionsCollection,_tmpMetadataCollection,_tmpValidityCollection,_tmpNodesCollection,_tmpFragmentsCollection)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun invalidateByNormVersion(
    normVersionId: Long,
    reason: String,
    invalidatedAt: Long,
  ): Int {
    val _sql: String = """
        |
        |        UPDATE reactivo_validity
        |        SET isCurrent = 0,
        |            validTo = ?,
        |            invalidationReason = ?
        |        WHERE normVersionId = ? AND isCurrent = 1
        |        
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, invalidatedAt)
        _argIndex = 2
        _stmt.bindText(_argIndex, reason)
        _argIndex = 3
        _stmt.bindLong(_argIndex, normVersionId)
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  private
      fun __fetchRelationshipreactivoOptionsAsdevVskelkCdfCoreDatabaseEntityReactivoOptionEntity(_connection: SQLiteConnection,
      _map: LongSparseArray<MutableList<ReactivoOptionEntity>>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, true) { _tmpMap ->
        __fetchRelationshipreactivoOptionsAsdevVskelkCdfCoreDatabaseEntityReactivoOptionEntity(_connection,
            _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`reactivoId`,`position`,`label`,`text`,`isCorrect`,`distractorType`,`rationale` FROM `reactivo_options` WHERE `reactivoId` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "reactivoId")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfReactivoId: Int = 1
      val _columnIndexOfPosition: Int = 2
      val _columnIndexOfLabel: Int = 3
      val _columnIndexOfText: Int = 4
      val _columnIndexOfIsCorrect: Int = 5
      val _columnIndexOfDistractorType: Int = 6
      val _columnIndexOfRationale: Int = 7
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        val _tmpRelation: MutableList<ReactivoOptionEntity>? = _map.get(_tmpKey)
        if (_tmpRelation != null) {
          val _item_1: ReactivoOptionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpReactivoId: Long
          _tmpReactivoId = _stmt.getLong(_columnIndexOfReactivoId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpIsCorrect: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          _tmpIsCorrect = _tmp != 0
          val _tmpDistractorType: String?
          if (_stmt.isNull(_columnIndexOfDistractorType)) {
            _tmpDistractorType = null
          } else {
            _tmpDistractorType = _stmt.getText(_columnIndexOfDistractorType)
          }
          val _tmpRationale: String?
          if (_stmt.isNull(_columnIndexOfRationale)) {
            _tmpRationale = null
          } else {
            _tmpRationale = _stmt.getText(_columnIndexOfRationale)
          }
          _item_1 =
              ReactivoOptionEntity(_tmpId,_tmpReactivoId,_tmpPosition,_tmpLabel,_tmpText,_tmpIsCorrect,_tmpDistractorType,_tmpRationale)
          _tmpRelation.add(_item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  private
      fun __fetchRelationshipreactivoMetadataAsdevVskelkCdfCoreDatabaseEntityReactivoMetadataEntity(_connection: SQLiteConnection,
      _map: LongSparseArray<MutableList<ReactivoMetadataEntity>>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, true) { _tmpMap ->
        __fetchRelationshipreactivoMetadataAsdevVskelkCdfCoreDatabaseEntityReactivoMetadataEntity(_connection,
            _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`reactivoId`,`difficulty`,`discrimination`,`estimatedTimeSec`,`reviewState`,`reviewerNotes`,`commonErrorPattern`,`blueprintWeight`,`lastReviewedAt`,`createdAt` FROM `reactivo_metadata` WHERE `reactivoId` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "reactivoId")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfReactivoId: Int = 1
      val _columnIndexOfDifficulty: Int = 2
      val _columnIndexOfDiscrimination: Int = 3
      val _columnIndexOfEstimatedTimeSec: Int = 4
      val _columnIndexOfReviewState: Int = 5
      val _columnIndexOfReviewerNotes: Int = 6
      val _columnIndexOfCommonErrorPattern: Int = 7
      val _columnIndexOfBlueprintWeight: Int = 8
      val _columnIndexOfLastReviewedAt: Int = 9
      val _columnIndexOfCreatedAt: Int = 10
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        val _tmpRelation: MutableList<ReactivoMetadataEntity>? = _map.get(_tmpKey)
        if (_tmpRelation != null) {
          val _item_1: ReactivoMetadataEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpReactivoId: Long
          _tmpReactivoId = _stmt.getLong(_columnIndexOfReactivoId)
          val _tmpDifficulty: Double
          _tmpDifficulty = _stmt.getDouble(_columnIndexOfDifficulty)
          val _tmpDiscrimination: Double?
          if (_stmt.isNull(_columnIndexOfDiscrimination)) {
            _tmpDiscrimination = null
          } else {
            _tmpDiscrimination = _stmt.getDouble(_columnIndexOfDiscrimination)
          }
          val _tmpEstimatedTimeSec: Int
          _tmpEstimatedTimeSec = _stmt.getLong(_columnIndexOfEstimatedTimeSec).toInt()
          val _tmpReviewState: String
          _tmpReviewState = _stmt.getText(_columnIndexOfReviewState)
          val _tmpReviewerNotes: String?
          if (_stmt.isNull(_columnIndexOfReviewerNotes)) {
            _tmpReviewerNotes = null
          } else {
            _tmpReviewerNotes = _stmt.getText(_columnIndexOfReviewerNotes)
          }
          val _tmpCommonErrorPattern: String?
          if (_stmt.isNull(_columnIndexOfCommonErrorPattern)) {
            _tmpCommonErrorPattern = null
          } else {
            _tmpCommonErrorPattern = _stmt.getText(_columnIndexOfCommonErrorPattern)
          }
          val _tmpBlueprintWeight: Double?
          if (_stmt.isNull(_columnIndexOfBlueprintWeight)) {
            _tmpBlueprintWeight = null
          } else {
            _tmpBlueprintWeight = _stmt.getDouble(_columnIndexOfBlueprintWeight)
          }
          val _tmpLastReviewedAt: Long?
          if (_stmt.isNull(_columnIndexOfLastReviewedAt)) {
            _tmpLastReviewedAt = null
          } else {
            _tmpLastReviewedAt = _stmt.getLong(_columnIndexOfLastReviewedAt)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item_1 =
              ReactivoMetadataEntity(_tmpId,_tmpReactivoId,_tmpDifficulty,_tmpDiscrimination,_tmpEstimatedTimeSec,_tmpReviewState,_tmpReviewerNotes,_tmpCommonErrorPattern,_tmpBlueprintWeight,_tmpLastReviewedAt,_tmpCreatedAt)
          _tmpRelation.add(_item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  private
      fun __fetchRelationshipreactivoValidityAsdevVskelkCdfCoreDatabaseEntityReactivoValidityEntity(_connection: SQLiteConnection,
      _map: LongSparseArray<MutableList<ReactivoValidityEntity>>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, true) { _tmpMap ->
        __fetchRelationshipreactivoValidityAsdevVskelkCdfCoreDatabaseEntityReactivoValidityEntity(_connection,
            _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `id`,`reactivoId`,`normVersionId`,`validFrom`,`validTo`,`isCurrent`,`invalidationReason`,`supersededByReactivoId`,`createdAt` FROM `reactivo_validity` WHERE `reactivoId` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      val _itemKeyIndex: Int = getColumnIndex(_stmt, "reactivoId")
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfReactivoId: Int = 1
      val _columnIndexOfNormVersionId: Int = 2
      val _columnIndexOfValidFrom: Int = 3
      val _columnIndexOfValidTo: Int = 4
      val _columnIndexOfIsCurrent: Int = 5
      val _columnIndexOfInvalidationReason: Int = 6
      val _columnIndexOfSupersededByReactivoId: Int = 7
      val _columnIndexOfCreatedAt: Int = 8
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        val _tmpRelation: MutableList<ReactivoValidityEntity>? = _map.get(_tmpKey)
        if (_tmpRelation != null) {
          val _item_1: ReactivoValidityEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpReactivoId: Long
          _tmpReactivoId = _stmt.getLong(_columnIndexOfReactivoId)
          val _tmpNormVersionId: Long
          _tmpNormVersionId = _stmt.getLong(_columnIndexOfNormVersionId)
          val _tmpValidFrom: Long
          _tmpValidFrom = _stmt.getLong(_columnIndexOfValidFrom)
          val _tmpValidTo: Long?
          if (_stmt.isNull(_columnIndexOfValidTo)) {
            _tmpValidTo = null
          } else {
            _tmpValidTo = _stmt.getLong(_columnIndexOfValidTo)
          }
          val _tmpIsCurrent: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCurrent).toInt()
          _tmpIsCurrent = _tmp != 0
          val _tmpInvalidationReason: String?
          if (_stmt.isNull(_columnIndexOfInvalidationReason)) {
            _tmpInvalidationReason = null
          } else {
            _tmpInvalidationReason = _stmt.getText(_columnIndexOfInvalidationReason)
          }
          val _tmpSupersededByReactivoId: Long?
          if (_stmt.isNull(_columnIndexOfSupersededByReactivoId)) {
            _tmpSupersededByReactivoId = null
          } else {
            _tmpSupersededByReactivoId = _stmt.getLong(_columnIndexOfSupersededByReactivoId)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item_1 =
              ReactivoValidityEntity(_tmpId,_tmpReactivoId,_tmpNormVersionId,_tmpValidFrom,_tmpValidTo,_tmpIsCurrent,_tmpInvalidationReason,_tmpSupersededByReactivoId,_tmpCreatedAt)
          _tmpRelation.add(_item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  private
      fun __fetchRelationshipontologyNodesAsdevVskelkCdfCoreDatabaseEntityOntologyNodeEntity(_connection: SQLiteConnection,
      _map: LongSparseArray<MutableList<OntologyNodeEntity>>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, true) { _tmpMap ->
        __fetchRelationshipontologyNodesAsdevVskelkCdfCoreDatabaseEntityOntologyNodeEntity(_connection,
            _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `ontology_nodes`.`id` AS `id`,`ontology_nodes`.`nodeKey` AS `nodeKey`,`ontology_nodes`.`nodeType` AS `nodeType`,`ontology_nodes`.`label` AS `label`,`ontology_nodes`.`description` AS `description`,`ontology_nodes`.`status` AS `status`,`ontology_nodes`.`confidence` AS `confidence`,`ontology_nodes`.`createdAt` AS `createdAt`,`ontology_nodes`.`updatedAt` AS `updatedAt`,_junction.`reactivoId` FROM `reactivo_node_cross_ref` AS _junction INNER JOIN `ontology_nodes` ON (_junction.`nodeId` = `ontology_nodes`.`id`) WHERE _junction.`reactivoId` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      // _junction.reactivoId
      val _itemKeyIndex: Int = 9
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfNodeKey: Int = 1
      val _columnIndexOfNodeType: Int = 2
      val _columnIndexOfLabel: Int = 3
      val _columnIndexOfDescription: Int = 4
      val _columnIndexOfStatus: Int = 5
      val _columnIndexOfConfidence: Int = 6
      val _columnIndexOfCreatedAt: Int = 7
      val _columnIndexOfUpdatedAt: Int = 8
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        val _tmpRelation: MutableList<OntologyNodeEntity>? = _map.get(_tmpKey)
        if (_tmpRelation != null) {
          val _item_1: OntologyNodeEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpNodeKey: String
          _tmpNodeKey = _stmt.getText(_columnIndexOfNodeKey)
          val _tmpNodeType: String
          _tmpNodeType = _stmt.getText(_columnIndexOfNodeType)
          val _tmpLabel: String
          _tmpLabel = _stmt.getText(_columnIndexOfLabel)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpConfidence: Double
          _tmpConfidence = _stmt.getDouble(_columnIndexOfConfidence)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item_1 =
              OntologyNodeEntity(_tmpId,_tmpNodeKey,_tmpNodeType,_tmpLabel,_tmpDescription,_tmpStatus,_tmpConfidence,_tmpCreatedAt,_tmpUpdatedAt)
          _tmpRelation.add(_item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  private
      fun __fetchRelationshipnormFragmentsAsdevVskelkCdfCoreDatabaseEntityNormFragmentEntity(_connection: SQLiteConnection,
      _map: LongSparseArray<MutableList<NormFragmentEntity>>) {
    if (_map.isEmpty()) {
      return
    }
    if (_map.size() > 999) {
      recursiveFetchLongSparseArray(_map, true) { _tmpMap ->
        __fetchRelationshipnormFragmentsAsdevVskelkCdfCoreDatabaseEntityNormFragmentEntity(_connection,
            _tmpMap)
      }
      return
    }
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT `norm_fragments`.`id` AS `id`,`norm_fragments`.`versionId` AS `versionId`,`norm_fragments`.`fragmentKey` AS `fragmentKey`,`norm_fragments`.`parentFragmentId` AS `parentFragmentId`,`norm_fragments`.`fragmentType` AS `fragmentType`,`norm_fragments`.`ordinal` AS `ordinal`,`norm_fragments`.`heading` AS `heading`,`norm_fragments`.`body` AS `body`,`norm_fragments`.`normalizedBody` AS `normalizedBody`,`norm_fragments`.`startOffset` AS `startOffset`,`norm_fragments`.`endOffset` AS `endOffset`,`norm_fragments`.`createdAt` AS `createdAt`,_junction.`reactivoId` FROM `reactivo_fragment_cross_ref` AS _junction INNER JOIN `norm_fragments` ON (_junction.`fragmentId` = `norm_fragments`.`id`) WHERE _junction.`reactivoId` IN (")
    val _inputSize: Int = _map.size()
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    val _stmt: SQLiteStatement = _connection.prepare(_sql)
    var _argIndex: Int = 1
    for (i in 0 until _map.size()) {
      val _item: Long = _map.keyAt(i)
      _stmt.bindLong(_argIndex, _item)
      _argIndex++
    }
    try {
      // _junction.reactivoId
      val _itemKeyIndex: Int = 12
      if (_itemKeyIndex == -1) {
        return
      }
      val _columnIndexOfId: Int = 0
      val _columnIndexOfVersionId: Int = 1
      val _columnIndexOfFragmentKey: Int = 2
      val _columnIndexOfParentFragmentId: Int = 3
      val _columnIndexOfFragmentType: Int = 4
      val _columnIndexOfOrdinal: Int = 5
      val _columnIndexOfHeading: Int = 6
      val _columnIndexOfBody: Int = 7
      val _columnIndexOfNormalizedBody: Int = 8
      val _columnIndexOfStartOffset: Int = 9
      val _columnIndexOfEndOffset: Int = 10
      val _columnIndexOfCreatedAt: Int = 11
      while (_stmt.step()) {
        val _tmpKey: Long
        _tmpKey = _stmt.getLong(_itemKeyIndex)
        val _tmpRelation: MutableList<NormFragmentEntity>? = _map.get(_tmpKey)
        if (_tmpRelation != null) {
          val _item_1: NormFragmentEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVersionId: Long
          _tmpVersionId = _stmt.getLong(_columnIndexOfVersionId)
          val _tmpFragmentKey: String
          _tmpFragmentKey = _stmt.getText(_columnIndexOfFragmentKey)
          val _tmpParentFragmentId: Long?
          if (_stmt.isNull(_columnIndexOfParentFragmentId)) {
            _tmpParentFragmentId = null
          } else {
            _tmpParentFragmentId = _stmt.getLong(_columnIndexOfParentFragmentId)
          }
          val _tmpFragmentType: String
          _tmpFragmentType = _stmt.getText(_columnIndexOfFragmentType)
          val _tmpOrdinal: String?
          if (_stmt.isNull(_columnIndexOfOrdinal)) {
            _tmpOrdinal = null
          } else {
            _tmpOrdinal = _stmt.getText(_columnIndexOfOrdinal)
          }
          val _tmpHeading: String?
          if (_stmt.isNull(_columnIndexOfHeading)) {
            _tmpHeading = null
          } else {
            _tmpHeading = _stmt.getText(_columnIndexOfHeading)
          }
          val _tmpBody: String
          _tmpBody = _stmt.getText(_columnIndexOfBody)
          val _tmpNormalizedBody: String
          _tmpNormalizedBody = _stmt.getText(_columnIndexOfNormalizedBody)
          val _tmpStartOffset: Int?
          if (_stmt.isNull(_columnIndexOfStartOffset)) {
            _tmpStartOffset = null
          } else {
            _tmpStartOffset = _stmt.getLong(_columnIndexOfStartOffset).toInt()
          }
          val _tmpEndOffset: Int?
          if (_stmt.isNull(_columnIndexOfEndOffset)) {
            _tmpEndOffset = null
          } else {
            _tmpEndOffset = _stmt.getLong(_columnIndexOfEndOffset).toInt()
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item_1 =
              NormFragmentEntity(_tmpId,_tmpVersionId,_tmpFragmentKey,_tmpParentFragmentId,_tmpFragmentType,_tmpOrdinal,_tmpHeading,_tmpBody,_tmpNormalizedBody,_tmpStartOffset,_tmpEndOffset,_tmpCreatedAt)
          _tmpRelation.add(_item_1)
        }
      }
    } finally {
      _stmt.close()
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
