package dev.vskelk.cdf.core.database.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import dev.vskelk.cdf.core.database.entity.NormFragmentEntity
import dev.vskelk.cdf.core.database.entity.NormSourceEntity
import dev.vskelk.cdf.core.database.entity.NormVersionEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class NormativeDao_Impl(
  __db: RoomDatabase,
) : NormativeDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfNormSourceEntity: EntityInsertAdapter<NormSourceEntity>

  private val __insertAdapterOfNormVersionEntity: EntityInsertAdapter<NormVersionEntity>

  private val __insertAdapterOfNormFragmentEntity: EntityInsertAdapter<NormFragmentEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfNormSourceEntity = object : EntityInsertAdapter<NormSourceEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `norm_sources` (`id`,`code`,`title`,`sourceType`,`issuer`,`jurisdiction`,`canonicalUrl`,`officialPublicationDate`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NormSourceEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.code)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.sourceType)
        statement.bindText(5, entity.issuer)
        statement.bindText(6, entity.jurisdiction)
        val _tmpCanonicalUrl: String? = entity.canonicalUrl
        if (_tmpCanonicalUrl == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpCanonicalUrl)
        }
        val _tmpOfficialPublicationDate: Long? = entity.officialPublicationDate
        if (_tmpOfficialPublicationDate == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpOfficialPublicationDate)
        }
        statement.bindLong(9, entity.createdAt)
        statement.bindLong(10, entity.updatedAt)
      }
    }
    this.__insertAdapterOfNormVersionEntity = object : EntityInsertAdapter<NormVersionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `norm_versions` (`id`,`sourceId`,`versionLabel`,`digest`,`validFrom`,`validTo`,`isCurrent`,`changeSummary`,`supersedesVersionId`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NormVersionEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.sourceId)
        statement.bindText(3, entity.versionLabel)
        statement.bindText(4, entity.digest)
        statement.bindLong(5, entity.validFrom)
        val _tmpValidTo: Long? = entity.validTo
        if (_tmpValidTo == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpValidTo)
        }
        val _tmp: Int = if (entity.isCurrent) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        val _tmpChangeSummary: String? = entity.changeSummary
        if (_tmpChangeSummary == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpChangeSummary)
        }
        val _tmpSupersedesVersionId: Long? = entity.supersedesVersionId
        if (_tmpSupersedesVersionId == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpSupersedesVersionId)
        }
        statement.bindLong(10, entity.createdAt)
      }
    }
    this.__insertAdapterOfNormFragmentEntity = object : EntityInsertAdapter<NormFragmentEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `norm_fragments` (`id`,`versionId`,`fragmentKey`,`parentFragmentId`,`fragmentType`,`ordinal`,`heading`,`body`,`normalizedBody`,`startOffset`,`endOffset`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NormFragmentEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.versionId)
        statement.bindText(3, entity.fragmentKey)
        val _tmpParentFragmentId: Long? = entity.parentFragmentId
        if (_tmpParentFragmentId == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpParentFragmentId)
        }
        statement.bindText(5, entity.fragmentType)
        val _tmpOrdinal: String? = entity.ordinal
        if (_tmpOrdinal == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpOrdinal)
        }
        val _tmpHeading: String? = entity.heading
        if (_tmpHeading == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpHeading)
        }
        statement.bindText(8, entity.body)
        statement.bindText(9, entity.normalizedBody)
        val _tmpStartOffset: Int? = entity.startOffset
        if (_tmpStartOffset == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpStartOffset.toLong())
        }
        val _tmpEndOffset: Int? = entity.endOffset
        if (_tmpEndOffset == null) {
          statement.bindNull(11)
        } else {
          statement.bindLong(11, _tmpEndOffset.toLong())
        }
        statement.bindLong(12, entity.createdAt)
      }
    }
  }

  public override suspend fun upsertSource(entity: NormSourceEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfNormSourceEntity.insertAndReturnId(_connection, entity)
    _result
  }

  public override suspend fun upsertVersion(entity: NormVersionEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfNormVersionEntity.insertAndReturnId(_connection, entity)
    _result
  }

  public override suspend fun upsertFragments(entities: List<NormFragmentEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfNormFragmentEntity.insert(_connection, entities)
  }

  public override fun observeSources(): Flow<List<NormSourceEntity>> {
    val _sql: String = "SELECT * FROM norm_sources ORDER BY title ASC"
    return createFlow(__db, false, arrayOf("norm_sources")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfCode: Int = getColumnIndexOrThrow(_stmt, "code")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfSourceType: Int = getColumnIndexOrThrow(_stmt, "sourceType")
        val _columnIndexOfIssuer: Int = getColumnIndexOrThrow(_stmt, "issuer")
        val _columnIndexOfJurisdiction: Int = getColumnIndexOrThrow(_stmt, "jurisdiction")
        val _columnIndexOfCanonicalUrl: Int = getColumnIndexOrThrow(_stmt, "canonicalUrl")
        val _columnIndexOfOfficialPublicationDate: Int = getColumnIndexOrThrow(_stmt,
            "officialPublicationDate")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<NormSourceEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: NormSourceEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpCode: String
          _tmpCode = _stmt.getText(_columnIndexOfCode)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpSourceType: String
          _tmpSourceType = _stmt.getText(_columnIndexOfSourceType)
          val _tmpIssuer: String
          _tmpIssuer = _stmt.getText(_columnIndexOfIssuer)
          val _tmpJurisdiction: String
          _tmpJurisdiction = _stmt.getText(_columnIndexOfJurisdiction)
          val _tmpCanonicalUrl: String?
          if (_stmt.isNull(_columnIndexOfCanonicalUrl)) {
            _tmpCanonicalUrl = null
          } else {
            _tmpCanonicalUrl = _stmt.getText(_columnIndexOfCanonicalUrl)
          }
          val _tmpOfficialPublicationDate: Long?
          if (_stmt.isNull(_columnIndexOfOfficialPublicationDate)) {
            _tmpOfficialPublicationDate = null
          } else {
            _tmpOfficialPublicationDate = _stmt.getLong(_columnIndexOfOfficialPublicationDate)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              NormSourceEntity(_tmpId,_tmpCode,_tmpTitle,_tmpSourceType,_tmpIssuer,_tmpJurisdiction,_tmpCanonicalUrl,_tmpOfficialPublicationDate,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeCurrentFragmentsBySource(sourceCode: String):
      Flow<List<NormFragmentEntity>> {
    val _sql: String = """
        |
        |        SELECT nf.* FROM norm_fragments nf
        |        INNER JOIN norm_versions nv ON nv.id = nf.versionId
        |        INNER JOIN norm_sources ns ON ns.id = nv.sourceId
        |        WHERE ns.code = ? AND nv.isCurrent = 1
        |        ORDER BY nf.fragmentKey ASC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("norm_fragments", "norm_versions", "norm_sources")) {
        _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, sourceCode)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVersionId: Int = getColumnIndexOrThrow(_stmt, "versionId")
        val _columnIndexOfFragmentKey: Int = getColumnIndexOrThrow(_stmt, "fragmentKey")
        val _columnIndexOfParentFragmentId: Int = getColumnIndexOrThrow(_stmt, "parentFragmentId")
        val _columnIndexOfFragmentType: Int = getColumnIndexOrThrow(_stmt, "fragmentType")
        val _columnIndexOfOrdinal: Int = getColumnIndexOrThrow(_stmt, "ordinal")
        val _columnIndexOfHeading: Int = getColumnIndexOrThrow(_stmt, "heading")
        val _columnIndexOfBody: Int = getColumnIndexOrThrow(_stmt, "body")
        val _columnIndexOfNormalizedBody: Int = getColumnIndexOrThrow(_stmt, "normalizedBody")
        val _columnIndexOfStartOffset: Int = getColumnIndexOrThrow(_stmt, "startOffset")
        val _columnIndexOfEndOffset: Int = getColumnIndexOrThrow(_stmt, "endOffset")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<NormFragmentEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: NormFragmentEntity
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
          _item =
              NormFragmentEntity(_tmpId,_tmpVersionId,_tmpFragmentKey,_tmpParentFragmentId,_tmpFragmentType,_tmpOrdinal,_tmpHeading,_tmpBody,_tmpNormalizedBody,_tmpStartOffset,_tmpEndOffset,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
