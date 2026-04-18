package dev.vskelk.cdf.core.database.dao

import androidx.collection.LongSparseArray
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.room.util.recursiveFetchLongSparseArray
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteStatement
import dev.vskelk.cdf.core.database.entity.NodeFragmentCrossRef
import dev.vskelk.cdf.core.database.entity.NormFragmentEntity
import dev.vskelk.cdf.core.database.entity.OntologyEdgeEntity
import dev.vskelk.cdf.core.database.entity.OntologyNodeEntity
import javax.`annotation`.processing.Generated
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
public class OntologyDao_Impl(
  __db: RoomDatabase,
) : OntologyDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfOntologyNodeEntity: EntityInsertAdapter<OntologyNodeEntity>

  private val __insertAdapterOfOntologyEdgeEntity: EntityInsertAdapter<OntologyEdgeEntity>

  private val __insertAdapterOfNodeFragmentCrossRef: EntityInsertAdapter<NodeFragmentCrossRef>
  init {
    this.__db = __db
    this.__insertAdapterOfOntologyNodeEntity = object : EntityInsertAdapter<OntologyNodeEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `ontology_nodes` (`id`,`nodeKey`,`nodeType`,`label`,`description`,`status`,`confidence`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: OntologyNodeEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.nodeKey)
        statement.bindText(3, entity.nodeType)
        statement.bindText(4, entity.label)
        statement.bindText(5, entity.description)
        statement.bindText(6, entity.status)
        statement.bindDouble(7, entity.confidence)
        statement.bindLong(8, entity.createdAt)
        statement.bindLong(9, entity.updatedAt)
      }
    }
    this.__insertAdapterOfOntologyEdgeEntity = object : EntityInsertAdapter<OntologyEdgeEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `ontology_edges` (`id`,`fromNodeId`,`toNodeId`,`relationType`,`weight`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: OntologyEdgeEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.fromNodeId)
        statement.bindLong(3, entity.toNodeId)
        statement.bindText(4, entity.relationType)
        statement.bindDouble(5, entity.weight)
        statement.bindLong(6, entity.createdAt)
      }
    }
    this.__insertAdapterOfNodeFragmentCrossRef = object :
        EntityInsertAdapter<NodeFragmentCrossRef>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `node_fragment_cross_ref` (`nodeId`,`fragmentId`,`mappingType`,`priority`,`createdAt`) VALUES (?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NodeFragmentCrossRef) {
        statement.bindLong(1, entity.nodeId)
        statement.bindLong(2, entity.fragmentId)
        statement.bindText(3, entity.mappingType)
        statement.bindLong(4, entity.priority.toLong())
        statement.bindLong(5, entity.createdAt)
      }
    }
  }

  public override suspend fun upsertNode(entity: OntologyNodeEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfOntologyNodeEntity.insertAndReturnId(_connection, entity)
    _result
  }

  public override suspend fun upsertNodes(entities: List<OntologyNodeEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfOntologyNodeEntity.insert(_connection, entities)
  }

  public override suspend fun upsertEdge(entity: OntologyEdgeEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfOntologyEdgeEntity.insertAndReturnId(_connection, entity)
    _result
  }

  public override suspend fun upsertNodeFragmentRefs(refs: List<NodeFragmentCrossRef>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfNodeFragmentCrossRef.insert(_connection, refs)
  }

  public override suspend fun getNodeWithFragments(nodeId: Long): NodeWithFragments? {
    val _sql: String = "SELECT * FROM ontology_nodes WHERE id = ?"
    return performSuspending(__db, true, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, nodeId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNodeKey: Int = getColumnIndexOrThrow(_stmt, "nodeKey")
        val _columnIndexOfNodeType: Int = getColumnIndexOrThrow(_stmt, "nodeType")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfConfidence: Int = getColumnIndexOrThrow(_stmt, "confidence")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _collectionFragments: LongSparseArray<MutableList<NormFragmentEntity>> =
            LongSparseArray<MutableList<NormFragmentEntity>>()
        while (_stmt.step()) {
          val _tmpKey: Long
          _tmpKey = _stmt.getLong(_columnIndexOfId)
          if (!_collectionFragments.containsKey(_tmpKey)) {
            _collectionFragments.put(_tmpKey, mutableListOf())
          }
        }
        _stmt.reset()
        __fetchRelationshipnormFragmentsAsdevVskelkCdfCoreDatabaseEntityNormFragmentEntity(_connection,
            _collectionFragments)
        val _result: NodeWithFragments?
        if (_stmt.step()) {
          val _tmpNode: OntologyNodeEntity
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
          _tmpNode =
              OntologyNodeEntity(_tmpId,_tmpNodeKey,_tmpNodeType,_tmpLabel,_tmpDescription,_tmpStatus,_tmpConfidence,_tmpCreatedAt,_tmpUpdatedAt)
          val _tmpFragmentsCollection: MutableList<NormFragmentEntity>
          val _tmpKey_1: Long
          _tmpKey_1 = _stmt.getLong(_columnIndexOfId)
          _tmpFragmentsCollection = checkNotNull(_collectionFragments.get(_tmpKey_1))
          _result = NodeWithFragments(_tmpNode,_tmpFragmentsCollection)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeActiveNodes(): Flow<List<OntologyNodeEntity>> {
    val _sql: String =
        "SELECT * FROM ontology_nodes WHERE status = 'ACTIVE' ORDER BY updatedAt DESC"
    return createFlow(__db, false, arrayOf("ontology_nodes")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfNodeKey: Int = getColumnIndexOrThrow(_stmt, "nodeKey")
        val _columnIndexOfNodeType: Int = getColumnIndexOrThrow(_stmt, "nodeType")
        val _columnIndexOfLabel: Int = getColumnIndexOrThrow(_stmt, "label")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfConfidence: Int = getColumnIndexOrThrow(_stmt, "confidence")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<OntologyNodeEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: OntologyNodeEntity
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
          _item =
              OntologyNodeEntity(_tmpId,_tmpNodeKey,_tmpNodeType,_tmpLabel,_tmpDescription,_tmpStatus,_tmpConfidence,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
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
    _stringBuilder.append("SELECT `norm_fragments`.`id` AS `id`,`norm_fragments`.`versionId` AS `versionId`,`norm_fragments`.`fragmentKey` AS `fragmentKey`,`norm_fragments`.`parentFragmentId` AS `parentFragmentId`,`norm_fragments`.`fragmentType` AS `fragmentType`,`norm_fragments`.`ordinal` AS `ordinal`,`norm_fragments`.`heading` AS `heading`,`norm_fragments`.`body` AS `body`,`norm_fragments`.`normalizedBody` AS `normalizedBody`,`norm_fragments`.`startOffset` AS `startOffset`,`norm_fragments`.`endOffset` AS `endOffset`,`norm_fragments`.`createdAt` AS `createdAt`,_junction.`nodeId` FROM `node_fragment_cross_ref` AS _junction INNER JOIN `norm_fragments` ON (_junction.`fragmentId` = `norm_fragments`.`id`) WHERE _junction.`nodeId` IN (")
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
      // _junction.nodeId
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
