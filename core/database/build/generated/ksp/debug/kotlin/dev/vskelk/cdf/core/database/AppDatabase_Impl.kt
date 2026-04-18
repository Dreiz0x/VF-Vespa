package dev.vskelk.cdf.core.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import dev.vskelk.cdf.core.database.dao.MessageDao
import dev.vskelk.cdf.core.database.dao.MessageDao_Impl
import dev.vskelk.cdf.core.database.dao.NormativeDao
import dev.vskelk.cdf.core.database.dao.NormativeDao_Impl
import dev.vskelk.cdf.core.database.dao.OntologyDao
import dev.vskelk.cdf.core.database.dao.OntologyDao_Impl
import dev.vskelk.cdf.core.database.dao.ReactivoDao
import dev.vskelk.cdf.core.database.dao.ReactivoDao_Impl
import dev.vskelk.cdf.core.database.dao.SessionDao
import dev.vskelk.cdf.core.database.dao.SessionDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _messageDao: Lazy<MessageDao> = lazy {
    MessageDao_Impl(this)
  }

  private val _sessionDao: Lazy<SessionDao> = lazy {
    SessionDao_Impl(this)
  }

  private val _normativeDao: Lazy<NormativeDao> = lazy {
    NormativeDao_Impl(this)
  }

  private val _ontologyDao: Lazy<OntologyDao> = lazy {
    OntologyDao_Impl(this)
  }

  private val _reactivoDao: Lazy<ReactivoDao> = lazy {
    ReactivoDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2,
        "d602a89e9f8ef817a6ff5b3d2e09933a", "ddd2aedff197c7f0f955245c434f7448") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `messages` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` TEXT NOT NULL, `role` TEXT NOT NULL, `content` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `pending` INTEGER NOT NULL, `failed` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `sessions` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `norm_sources` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `code` TEXT NOT NULL, `title` TEXT NOT NULL, `sourceType` TEXT NOT NULL, `issuer` TEXT NOT NULL, `jurisdiction` TEXT NOT NULL, `canonicalUrl` TEXT, `officialPublicationDate` INTEGER, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_norm_sources_code` ON `norm_sources` (`code`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_norm_sources_sourceType` ON `norm_sources` (`sourceType`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `norm_versions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sourceId` INTEGER NOT NULL, `versionLabel` TEXT NOT NULL, `digest` TEXT NOT NULL, `validFrom` INTEGER NOT NULL, `validTo` INTEGER, `isCurrent` INTEGER NOT NULL, `changeSummary` TEXT, `supersedesVersionId` INTEGER, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`sourceId`) REFERENCES `norm_sources`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_norm_versions_sourceId` ON `norm_versions` (`sourceId`)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_norm_versions_sourceId_versionLabel` ON `norm_versions` (`sourceId`, `versionLabel`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_norm_versions_validFrom` ON `norm_versions` (`validFrom`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_norm_versions_validTo` ON `norm_versions` (`validTo`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_norm_versions_isCurrent` ON `norm_versions` (`isCurrent`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `norm_fragments` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `versionId` INTEGER NOT NULL, `fragmentKey` TEXT NOT NULL, `parentFragmentId` INTEGER, `fragmentType` TEXT NOT NULL, `ordinal` TEXT, `heading` TEXT, `body` TEXT NOT NULL, `normalizedBody` TEXT NOT NULL, `startOffset` INTEGER, `endOffset` INTEGER, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`versionId`) REFERENCES `norm_versions`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_norm_fragments_versionId` ON `norm_fragments` (`versionId`)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_norm_fragments_versionId_fragmentKey` ON `norm_fragments` (`versionId`, `fragmentKey`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_norm_fragments_fragmentType` ON `norm_fragments` (`fragmentType`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_norm_fragments_parentFragmentId` ON `norm_fragments` (`parentFragmentId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `ontology_nodes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nodeKey` TEXT NOT NULL, `nodeType` TEXT NOT NULL, `label` TEXT NOT NULL, `description` TEXT NOT NULL, `status` TEXT NOT NULL, `confidence` REAL NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_ontology_nodes_nodeKey` ON `ontology_nodes` (`nodeKey`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_ontology_nodes_nodeType` ON `ontology_nodes` (`nodeType`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_ontology_nodes_status` ON `ontology_nodes` (`status`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `ontology_edges` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fromNodeId` INTEGER NOT NULL, `toNodeId` INTEGER NOT NULL, `relationType` TEXT NOT NULL, `weight` REAL NOT NULL, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`fromNodeId`) REFERENCES `ontology_nodes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`toNodeId`) REFERENCES `ontology_nodes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_ontology_edges_fromNodeId` ON `ontology_edges` (`fromNodeId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_ontology_edges_toNodeId` ON `ontology_edges` (`toNodeId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_ontology_edges_relationType` ON `ontology_edges` (`relationType`)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_ontology_edges_fromNodeId_relationType_toNodeId` ON `ontology_edges` (`fromNodeId`, `relationType`, `toNodeId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `node_fragment_cross_ref` (`nodeId` INTEGER NOT NULL, `fragmentId` INTEGER NOT NULL, `mappingType` TEXT NOT NULL, `priority` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`nodeId`, `fragmentId`), FOREIGN KEY(`nodeId`) REFERENCES `ontology_nodes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`fragmentId`) REFERENCES `norm_fragments`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_node_fragment_cross_ref_fragmentId` ON `node_fragment_cross_ref` (`fragmentId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `reactivos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `reactivoKey` TEXT NOT NULL, `primaryNodeId` INTEGER NOT NULL, `stem` TEXT NOT NULL, `formatType` TEXT NOT NULL, `examArea` TEXT NOT NULL, `cognitiveLevel` TEXT NOT NULL, `status` TEXT NOT NULL, `sourceMode` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, FOREIGN KEY(`primaryNodeId`) REFERENCES `ontology_nodes`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_reactivos_reactivoKey` ON `reactivos` (`reactivoKey`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivos_primaryNodeId` ON `reactivos` (`primaryNodeId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivos_formatType` ON `reactivos` (`formatType`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivos_status` ON `reactivos` (`status`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivos_examArea_status_cognitiveLevel` ON `reactivos` (`examArea`, `status`, `cognitiveLevel`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `reactivo_options` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `reactivoId` INTEGER NOT NULL, `position` INTEGER NOT NULL, `label` TEXT NOT NULL, `text` TEXT NOT NULL, `isCorrect` INTEGER NOT NULL, `distractorType` TEXT, `rationale` TEXT, FOREIGN KEY(`reactivoId`) REFERENCES `reactivos`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivo_options_reactivoId` ON `reactivo_options` (`reactivoId`)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_reactivo_options_reactivoId_position` ON `reactivo_options` (`reactivoId`, `position`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `reactivo_metadata` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `reactivoId` INTEGER NOT NULL, `difficulty` REAL NOT NULL, `discrimination` REAL, `estimatedTimeSec` INTEGER NOT NULL, `reviewState` TEXT NOT NULL, `reviewerNotes` TEXT, `commonErrorPattern` TEXT, `blueprintWeight` REAL, `lastReviewedAt` INTEGER, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`reactivoId`) REFERENCES `reactivos`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_reactivo_metadata_reactivoId` ON `reactivo_metadata` (`reactivoId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivo_metadata_difficulty` ON `reactivo_metadata` (`difficulty`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivo_metadata_reviewState` ON `reactivo_metadata` (`reviewState`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `reactivo_validity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `reactivoId` INTEGER NOT NULL, `normVersionId` INTEGER NOT NULL, `validFrom` INTEGER NOT NULL, `validTo` INTEGER, `isCurrent` INTEGER NOT NULL, `invalidationReason` TEXT, `supersededByReactivoId` INTEGER, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`reactivoId`) REFERENCES `reactivos`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`normVersionId`) REFERENCES `norm_versions`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_reactivo_validity_reactivoId` ON `reactivo_validity` (`reactivoId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivo_validity_normVersionId` ON `reactivo_validity` (`normVersionId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivo_validity_validFrom` ON `reactivo_validity` (`validFrom`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivo_validity_validTo` ON `reactivo_validity` (`validTo`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivo_validity_isCurrent` ON `reactivo_validity` (`isCurrent`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `reactivo_fragment_cross_ref` (`reactivoId` INTEGER NOT NULL, `fragmentId` INTEGER NOT NULL, `referenceRole` TEXT NOT NULL, `citationOrder` INTEGER NOT NULL, PRIMARY KEY(`reactivoId`, `fragmentId`), FOREIGN KEY(`reactivoId`) REFERENCES `reactivos`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`fragmentId`) REFERENCES `norm_fragments`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivo_fragment_cross_ref_fragmentId` ON `reactivo_fragment_cross_ref` (`fragmentId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `reactivo_node_cross_ref` (`reactivoId` INTEGER NOT NULL, `nodeId` INTEGER NOT NULL, `semanticRole` TEXT NOT NULL, `weight` REAL NOT NULL, PRIMARY KEY(`reactivoId`, `nodeId`), FOREIGN KEY(`reactivoId`) REFERENCES `reactivos`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`nodeId`) REFERENCES `ontology_nodes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_reactivo_node_cross_ref_nodeId` ON `reactivo_node_cross_ref` (`nodeId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd602a89e9f8ef817a6ff5b3d2e09933a')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `messages`")
        connection.execSQL("DROP TABLE IF EXISTS `sessions`")
        connection.execSQL("DROP TABLE IF EXISTS `norm_sources`")
        connection.execSQL("DROP TABLE IF EXISTS `norm_versions`")
        connection.execSQL("DROP TABLE IF EXISTS `norm_fragments`")
        connection.execSQL("DROP TABLE IF EXISTS `ontology_nodes`")
        connection.execSQL("DROP TABLE IF EXISTS `ontology_edges`")
        connection.execSQL("DROP TABLE IF EXISTS `node_fragment_cross_ref`")
        connection.execSQL("DROP TABLE IF EXISTS `reactivos`")
        connection.execSQL("DROP TABLE IF EXISTS `reactivo_options`")
        connection.execSQL("DROP TABLE IF EXISTS `reactivo_metadata`")
        connection.execSQL("DROP TABLE IF EXISTS `reactivo_validity`")
        connection.execSQL("DROP TABLE IF EXISTS `reactivo_fragment_cross_ref`")
        connection.execSQL("DROP TABLE IF EXISTS `reactivo_node_cross_ref`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsMessages: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsMessages.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("sessionId", TableInfo.Column("sessionId", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("role", TableInfo.Column("role", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("content", TableInfo.Column("content", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("pending", TableInfo.Column("pending", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMessages.put("failed", TableInfo.Column("failed", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysMessages: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesMessages: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoMessages: TableInfo = TableInfo("messages", _columnsMessages, _foreignKeysMessages,
            _indicesMessages)
        val _existingMessages: TableInfo = read(connection, "messages")
        if (!_infoMessages.equals(_existingMessages)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |messages(dev.vskelk.cdf.core.database.entity.MessageEntity).
              | Expected:
              |""".trimMargin() + _infoMessages + """
              |
              | Found:
              |""".trimMargin() + _existingMessages)
        }
        val _columnsSessions: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSessions.put("id", TableInfo.Column("id", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSessions.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSessions.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSessions: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSessions: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSessions: TableInfo = TableInfo("sessions", _columnsSessions, _foreignKeysSessions,
            _indicesSessions)
        val _existingSessions: TableInfo = read(connection, "sessions")
        if (!_infoSessions.equals(_existingSessions)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |sessions(dev.vskelk.cdf.core.database.entity.SessionEntity).
              | Expected:
              |""".trimMargin() + _infoSessions + """
              |
              | Found:
              |""".trimMargin() + _existingSessions)
        }
        val _columnsNormSources: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNormSources.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormSources.put("code", TableInfo.Column("code", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormSources.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormSources.put("sourceType", TableInfo.Column("sourceType", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormSources.put("issuer", TableInfo.Column("issuer", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormSources.put("jurisdiction", TableInfo.Column("jurisdiction", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormSources.put("canonicalUrl", TableInfo.Column("canonicalUrl", "TEXT", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormSources.put("officialPublicationDate",
            TableInfo.Column("officialPublicationDate", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormSources.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormSources.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNormSources: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesNormSources: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesNormSources.add(TableInfo.Index("index_norm_sources_code", true, listOf("code"),
            listOf("ASC")))
        _indicesNormSources.add(TableInfo.Index("index_norm_sources_sourceType", false,
            listOf("sourceType"), listOf("ASC")))
        val _infoNormSources: TableInfo = TableInfo("norm_sources", _columnsNormSources,
            _foreignKeysNormSources, _indicesNormSources)
        val _existingNormSources: TableInfo = read(connection, "norm_sources")
        if (!_infoNormSources.equals(_existingNormSources)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |norm_sources(dev.vskelk.cdf.core.database.entity.NormSourceEntity).
              | Expected:
              |""".trimMargin() + _infoNormSources + """
              |
              | Found:
              |""".trimMargin() + _existingNormSources)
        }
        val _columnsNormVersions: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNormVersions.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormVersions.put("sourceId", TableInfo.Column("sourceId", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormVersions.put("versionLabel", TableInfo.Column("versionLabel", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormVersions.put("digest", TableInfo.Column("digest", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormVersions.put("validFrom", TableInfo.Column("validFrom", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormVersions.put("validTo", TableInfo.Column("validTo", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormVersions.put("isCurrent", TableInfo.Column("isCurrent", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormVersions.put("changeSummary", TableInfo.Column("changeSummary", "TEXT", false,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormVersions.put("supersedesVersionId", TableInfo.Column("supersedesVersionId",
            "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormVersions.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNormVersions: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysNormVersions.add(TableInfo.ForeignKey("norm_sources", "CASCADE", "NO ACTION",
            listOf("sourceId"), listOf("id")))
        val _indicesNormVersions: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesNormVersions.add(TableInfo.Index("index_norm_versions_sourceId", false,
            listOf("sourceId"), listOf("ASC")))
        _indicesNormVersions.add(TableInfo.Index("index_norm_versions_sourceId_versionLabel", true,
            listOf("sourceId", "versionLabel"), listOf("ASC", "ASC")))
        _indicesNormVersions.add(TableInfo.Index("index_norm_versions_validFrom", false,
            listOf("validFrom"), listOf("ASC")))
        _indicesNormVersions.add(TableInfo.Index("index_norm_versions_validTo", false,
            listOf("validTo"), listOf("ASC")))
        _indicesNormVersions.add(TableInfo.Index("index_norm_versions_isCurrent", false,
            listOf("isCurrent"), listOf("ASC")))
        val _infoNormVersions: TableInfo = TableInfo("norm_versions", _columnsNormVersions,
            _foreignKeysNormVersions, _indicesNormVersions)
        val _existingNormVersions: TableInfo = read(connection, "norm_versions")
        if (!_infoNormVersions.equals(_existingNormVersions)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |norm_versions(dev.vskelk.cdf.core.database.entity.NormVersionEntity).
              | Expected:
              |""".trimMargin() + _infoNormVersions + """
              |
              | Found:
              |""".trimMargin() + _existingNormVersions)
        }
        val _columnsNormFragments: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNormFragments.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("versionId", TableInfo.Column("versionId", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("fragmentKey", TableInfo.Column("fragmentKey", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("parentFragmentId", TableInfo.Column("parentFragmentId",
            "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("fragmentType", TableInfo.Column("fragmentType", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("ordinal", TableInfo.Column("ordinal", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("heading", TableInfo.Column("heading", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("body", TableInfo.Column("body", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("normalizedBody", TableInfo.Column("normalizedBody", "TEXT", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("startOffset", TableInfo.Column("startOffset", "INTEGER", false,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("endOffset", TableInfo.Column("endOffset", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNormFragments.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNormFragments: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysNormFragments.add(TableInfo.ForeignKey("norm_versions", "CASCADE", "NO ACTION",
            listOf("versionId"), listOf("id")))
        val _indicesNormFragments: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesNormFragments.add(TableInfo.Index("index_norm_fragments_versionId", false,
            listOf("versionId"), listOf("ASC")))
        _indicesNormFragments.add(TableInfo.Index("index_norm_fragments_versionId_fragmentKey",
            true, listOf("versionId", "fragmentKey"), listOf("ASC", "ASC")))
        _indicesNormFragments.add(TableInfo.Index("index_norm_fragments_fragmentType", false,
            listOf("fragmentType"), listOf("ASC")))
        _indicesNormFragments.add(TableInfo.Index("index_norm_fragments_parentFragmentId", false,
            listOf("parentFragmentId"), listOf("ASC")))
        val _infoNormFragments: TableInfo = TableInfo("norm_fragments", _columnsNormFragments,
            _foreignKeysNormFragments, _indicesNormFragments)
        val _existingNormFragments: TableInfo = read(connection, "norm_fragments")
        if (!_infoNormFragments.equals(_existingNormFragments)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |norm_fragments(dev.vskelk.cdf.core.database.entity.NormFragmentEntity).
              | Expected:
              |""".trimMargin() + _infoNormFragments + """
              |
              | Found:
              |""".trimMargin() + _existingNormFragments)
        }
        val _columnsOntologyNodes: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsOntologyNodes.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyNodes.put("nodeKey", TableInfo.Column("nodeKey", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyNodes.put("nodeType", TableInfo.Column("nodeType", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyNodes.put("label", TableInfo.Column("label", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyNodes.put("description", TableInfo.Column("description", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyNodes.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyNodes.put("confidence", TableInfo.Column("confidence", "REAL", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyNodes.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyNodes.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysOntologyNodes: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesOntologyNodes: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesOntologyNodes.add(TableInfo.Index("index_ontology_nodes_nodeKey", true,
            listOf("nodeKey"), listOf("ASC")))
        _indicesOntologyNodes.add(TableInfo.Index("index_ontology_nodes_nodeType", false,
            listOf("nodeType"), listOf("ASC")))
        _indicesOntologyNodes.add(TableInfo.Index("index_ontology_nodes_status", false,
            listOf("status"), listOf("ASC")))
        val _infoOntologyNodes: TableInfo = TableInfo("ontology_nodes", _columnsOntologyNodes,
            _foreignKeysOntologyNodes, _indicesOntologyNodes)
        val _existingOntologyNodes: TableInfo = read(connection, "ontology_nodes")
        if (!_infoOntologyNodes.equals(_existingOntologyNodes)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |ontology_nodes(dev.vskelk.cdf.core.database.entity.OntologyNodeEntity).
              | Expected:
              |""".trimMargin() + _infoOntologyNodes + """
              |
              | Found:
              |""".trimMargin() + _existingOntologyNodes)
        }
        val _columnsOntologyEdges: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsOntologyEdges.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyEdges.put("fromNodeId", TableInfo.Column("fromNodeId", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyEdges.put("toNodeId", TableInfo.Column("toNodeId", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyEdges.put("relationType", TableInfo.Column("relationType", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyEdges.put("weight", TableInfo.Column("weight", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsOntologyEdges.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysOntologyEdges: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysOntologyEdges.add(TableInfo.ForeignKey("ontology_nodes", "CASCADE", "NO ACTION",
            listOf("fromNodeId"), listOf("id")))
        _foreignKeysOntologyEdges.add(TableInfo.ForeignKey("ontology_nodes", "CASCADE", "NO ACTION",
            listOf("toNodeId"), listOf("id")))
        val _indicesOntologyEdges: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesOntologyEdges.add(TableInfo.Index("index_ontology_edges_fromNodeId", false,
            listOf("fromNodeId"), listOf("ASC")))
        _indicesOntologyEdges.add(TableInfo.Index("index_ontology_edges_toNodeId", false,
            listOf("toNodeId"), listOf("ASC")))
        _indicesOntologyEdges.add(TableInfo.Index("index_ontology_edges_relationType", false,
            listOf("relationType"), listOf("ASC")))
        _indicesOntologyEdges.add(TableInfo.Index("index_ontology_edges_fromNodeId_relationType_toNodeId",
            true, listOf("fromNodeId", "relationType", "toNodeId"), listOf("ASC", "ASC", "ASC")))
        val _infoOntologyEdges: TableInfo = TableInfo("ontology_edges", _columnsOntologyEdges,
            _foreignKeysOntologyEdges, _indicesOntologyEdges)
        val _existingOntologyEdges: TableInfo = read(connection, "ontology_edges")
        if (!_infoOntologyEdges.equals(_existingOntologyEdges)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |ontology_edges(dev.vskelk.cdf.core.database.entity.OntologyEdgeEntity).
              | Expected:
              |""".trimMargin() + _infoOntologyEdges + """
              |
              | Found:
              |""".trimMargin() + _existingOntologyEdges)
        }
        val _columnsNodeFragmentCrossRef: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNodeFragmentCrossRef.put("nodeId", TableInfo.Column("nodeId", "INTEGER", true, 1,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNodeFragmentCrossRef.put("fragmentId", TableInfo.Column("fragmentId", "INTEGER",
            true, 2, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNodeFragmentCrossRef.put("mappingType", TableInfo.Column("mappingType", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNodeFragmentCrossRef.put("priority", TableInfo.Column("priority", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNodeFragmentCrossRef.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNodeFragmentCrossRef: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysNodeFragmentCrossRef.add(TableInfo.ForeignKey("ontology_nodes", "CASCADE",
            "NO ACTION", listOf("nodeId"), listOf("id")))
        _foreignKeysNodeFragmentCrossRef.add(TableInfo.ForeignKey("norm_fragments", "CASCADE",
            "NO ACTION", listOf("fragmentId"), listOf("id")))
        val _indicesNodeFragmentCrossRef: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesNodeFragmentCrossRef.add(TableInfo.Index("index_node_fragment_cross_ref_fragmentId",
            false, listOf("fragmentId"), listOf("ASC")))
        val _infoNodeFragmentCrossRef: TableInfo = TableInfo("node_fragment_cross_ref",
            _columnsNodeFragmentCrossRef, _foreignKeysNodeFragmentCrossRef,
            _indicesNodeFragmentCrossRef)
        val _existingNodeFragmentCrossRef: TableInfo = read(connection, "node_fragment_cross_ref")
        if (!_infoNodeFragmentCrossRef.equals(_existingNodeFragmentCrossRef)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |node_fragment_cross_ref(dev.vskelk.cdf.core.database.entity.NodeFragmentCrossRef).
              | Expected:
              |""".trimMargin() + _infoNodeFragmentCrossRef + """
              |
              | Found:
              |""".trimMargin() + _existingNodeFragmentCrossRef)
        }
        val _columnsReactivos: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsReactivos.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("reactivoKey", TableInfo.Column("reactivoKey", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("primaryNodeId", TableInfo.Column("primaryNodeId", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("stem", TableInfo.Column("stem", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("formatType", TableInfo.Column("formatType", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("examArea", TableInfo.Column("examArea", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("cognitiveLevel", TableInfo.Column("cognitiveLevel", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("sourceMode", TableInfo.Column("sourceMode", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivos.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysReactivos: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysReactivos.add(TableInfo.ForeignKey("ontology_nodes", "RESTRICT", "NO ACTION",
            listOf("primaryNodeId"), listOf("id")))
        val _indicesReactivos: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesReactivos.add(TableInfo.Index("index_reactivos_reactivoKey", true,
            listOf("reactivoKey"), listOf("ASC")))
        _indicesReactivos.add(TableInfo.Index("index_reactivos_primaryNodeId", false,
            listOf("primaryNodeId"), listOf("ASC")))
        _indicesReactivos.add(TableInfo.Index("index_reactivos_formatType", false,
            listOf("formatType"), listOf("ASC")))
        _indicesReactivos.add(TableInfo.Index("index_reactivos_status", false, listOf("status"),
            listOf("ASC")))
        _indicesReactivos.add(TableInfo.Index("index_reactivos_examArea_status_cognitiveLevel",
            false, listOf("examArea", "status", "cognitiveLevel"), listOf("ASC", "ASC", "ASC")))
        val _infoReactivos: TableInfo = TableInfo("reactivos", _columnsReactivos,
            _foreignKeysReactivos, _indicesReactivos)
        val _existingReactivos: TableInfo = read(connection, "reactivos")
        if (!_infoReactivos.equals(_existingReactivos)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |reactivos(dev.vskelk.cdf.core.database.entity.ReactivoEntity).
              | Expected:
              |""".trimMargin() + _infoReactivos + """
              |
              | Found:
              |""".trimMargin() + _existingReactivos)
        }
        val _columnsReactivoOptions: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsReactivoOptions.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoOptions.put("reactivoId", TableInfo.Column("reactivoId", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoOptions.put("position", TableInfo.Column("position", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoOptions.put("label", TableInfo.Column("label", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoOptions.put("text", TableInfo.Column("text", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoOptions.put("isCorrect", TableInfo.Column("isCorrect", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoOptions.put("distractorType", TableInfo.Column("distractorType", "TEXT",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoOptions.put("rationale", TableInfo.Column("rationale", "TEXT", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysReactivoOptions: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysReactivoOptions.add(TableInfo.ForeignKey("reactivos", "CASCADE", "NO ACTION",
            listOf("reactivoId"), listOf("id")))
        val _indicesReactivoOptions: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesReactivoOptions.add(TableInfo.Index("index_reactivo_options_reactivoId", false,
            listOf("reactivoId"), listOf("ASC")))
        _indicesReactivoOptions.add(TableInfo.Index("index_reactivo_options_reactivoId_position",
            true, listOf("reactivoId", "position"), listOf("ASC", "ASC")))
        val _infoReactivoOptions: TableInfo = TableInfo("reactivo_options", _columnsReactivoOptions,
            _foreignKeysReactivoOptions, _indicesReactivoOptions)
        val _existingReactivoOptions: TableInfo = read(connection, "reactivo_options")
        if (!_infoReactivoOptions.equals(_existingReactivoOptions)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |reactivo_options(dev.vskelk.cdf.core.database.entity.ReactivoOptionEntity).
              | Expected:
              |""".trimMargin() + _infoReactivoOptions + """
              |
              | Found:
              |""".trimMargin() + _existingReactivoOptions)
        }
        val _columnsReactivoMetadata: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsReactivoMetadata.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("reactivoId", TableInfo.Column("reactivoId", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("difficulty", TableInfo.Column("difficulty", "REAL", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("discrimination", TableInfo.Column("discrimination", "REAL",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("estimatedTimeSec", TableInfo.Column("estimatedTimeSec",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("reviewState", TableInfo.Column("reviewState", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("reviewerNotes", TableInfo.Column("reviewerNotes", "TEXT",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("commonErrorPattern", TableInfo.Column("commonErrorPattern",
            "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("blueprintWeight", TableInfo.Column("blueprintWeight", "REAL",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("lastReviewedAt", TableInfo.Column("lastReviewedAt", "INTEGER",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoMetadata.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysReactivoMetadata: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysReactivoMetadata.add(TableInfo.ForeignKey("reactivos", "CASCADE", "NO ACTION",
            listOf("reactivoId"), listOf("id")))
        val _indicesReactivoMetadata: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesReactivoMetadata.add(TableInfo.Index("index_reactivo_metadata_reactivoId", true,
            listOf("reactivoId"), listOf("ASC")))
        _indicesReactivoMetadata.add(TableInfo.Index("index_reactivo_metadata_difficulty", false,
            listOf("difficulty"), listOf("ASC")))
        _indicesReactivoMetadata.add(TableInfo.Index("index_reactivo_metadata_reviewState", false,
            listOf("reviewState"), listOf("ASC")))
        val _infoReactivoMetadata: TableInfo = TableInfo("reactivo_metadata",
            _columnsReactivoMetadata, _foreignKeysReactivoMetadata, _indicesReactivoMetadata)
        val _existingReactivoMetadata: TableInfo = read(connection, "reactivo_metadata")
        if (!_infoReactivoMetadata.equals(_existingReactivoMetadata)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |reactivo_metadata(dev.vskelk.cdf.core.database.entity.ReactivoMetadataEntity).
              | Expected:
              |""".trimMargin() + _infoReactivoMetadata + """
              |
              | Found:
              |""".trimMargin() + _existingReactivoMetadata)
        }
        val _columnsReactivoValidity: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsReactivoValidity.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoValidity.put("reactivoId", TableInfo.Column("reactivoId", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoValidity.put("normVersionId", TableInfo.Column("normVersionId", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoValidity.put("validFrom", TableInfo.Column("validFrom", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoValidity.put("validTo", TableInfo.Column("validTo", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoValidity.put("isCurrent", TableInfo.Column("isCurrent", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoValidity.put("invalidationReason", TableInfo.Column("invalidationReason",
            "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoValidity.put("supersededByReactivoId",
            TableInfo.Column("supersededByReactivoId", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoValidity.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysReactivoValidity: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysReactivoValidity.add(TableInfo.ForeignKey("reactivos", "CASCADE", "NO ACTION",
            listOf("reactivoId"), listOf("id")))
        _foreignKeysReactivoValidity.add(TableInfo.ForeignKey("norm_versions", "RESTRICT",
            "NO ACTION", listOf("normVersionId"), listOf("id")))
        val _indicesReactivoValidity: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesReactivoValidity.add(TableInfo.Index("index_reactivo_validity_reactivoId", true,
            listOf("reactivoId"), listOf("ASC")))
        _indicesReactivoValidity.add(TableInfo.Index("index_reactivo_validity_normVersionId", false,
            listOf("normVersionId"), listOf("ASC")))
        _indicesReactivoValidity.add(TableInfo.Index("index_reactivo_validity_validFrom", false,
            listOf("validFrom"), listOf("ASC")))
        _indicesReactivoValidity.add(TableInfo.Index("index_reactivo_validity_validTo", false,
            listOf("validTo"), listOf("ASC")))
        _indicesReactivoValidity.add(TableInfo.Index("index_reactivo_validity_isCurrent", false,
            listOf("isCurrent"), listOf("ASC")))
        val _infoReactivoValidity: TableInfo = TableInfo("reactivo_validity",
            _columnsReactivoValidity, _foreignKeysReactivoValidity, _indicesReactivoValidity)
        val _existingReactivoValidity: TableInfo = read(connection, "reactivo_validity")
        if (!_infoReactivoValidity.equals(_existingReactivoValidity)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |reactivo_validity(dev.vskelk.cdf.core.database.entity.ReactivoValidityEntity).
              | Expected:
              |""".trimMargin() + _infoReactivoValidity + """
              |
              | Found:
              |""".trimMargin() + _existingReactivoValidity)
        }
        val _columnsReactivoFragmentCrossRef: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsReactivoFragmentCrossRef.put("reactivoId", TableInfo.Column("reactivoId", "INTEGER",
            true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoFragmentCrossRef.put("fragmentId", TableInfo.Column("fragmentId", "INTEGER",
            true, 2, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoFragmentCrossRef.put("referenceRole", TableInfo.Column("referenceRole",
            "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoFragmentCrossRef.put("citationOrder", TableInfo.Column("citationOrder",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysReactivoFragmentCrossRef: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysReactivoFragmentCrossRef.add(TableInfo.ForeignKey("reactivos", "CASCADE",
            "NO ACTION", listOf("reactivoId"), listOf("id")))
        _foreignKeysReactivoFragmentCrossRef.add(TableInfo.ForeignKey("norm_fragments", "RESTRICT",
            "NO ACTION", listOf("fragmentId"), listOf("id")))
        val _indicesReactivoFragmentCrossRef: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesReactivoFragmentCrossRef.add(TableInfo.Index("index_reactivo_fragment_cross_ref_fragmentId",
            false, listOf("fragmentId"), listOf("ASC")))
        val _infoReactivoFragmentCrossRef: TableInfo = TableInfo("reactivo_fragment_cross_ref",
            _columnsReactivoFragmentCrossRef, _foreignKeysReactivoFragmentCrossRef,
            _indicesReactivoFragmentCrossRef)
        val _existingReactivoFragmentCrossRef: TableInfo = read(connection,
            "reactivo_fragment_cross_ref")
        if (!_infoReactivoFragmentCrossRef.equals(_existingReactivoFragmentCrossRef)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |reactivo_fragment_cross_ref(dev.vskelk.cdf.core.database.entity.ReactivoFragmentCrossRef).
              | Expected:
              |""".trimMargin() + _infoReactivoFragmentCrossRef + """
              |
              | Found:
              |""".trimMargin() + _existingReactivoFragmentCrossRef)
        }
        val _columnsReactivoNodeCrossRef: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsReactivoNodeCrossRef.put("reactivoId", TableInfo.Column("reactivoId", "INTEGER",
            true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoNodeCrossRef.put("nodeId", TableInfo.Column("nodeId", "INTEGER", true, 2,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoNodeCrossRef.put("semanticRole", TableInfo.Column("semanticRole", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsReactivoNodeCrossRef.put("weight", TableInfo.Column("weight", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysReactivoNodeCrossRef: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysReactivoNodeCrossRef.add(TableInfo.ForeignKey("reactivos", "CASCADE",
            "NO ACTION", listOf("reactivoId"), listOf("id")))
        _foreignKeysReactivoNodeCrossRef.add(TableInfo.ForeignKey("ontology_nodes", "CASCADE",
            "NO ACTION", listOf("nodeId"), listOf("id")))
        val _indicesReactivoNodeCrossRef: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesReactivoNodeCrossRef.add(TableInfo.Index("index_reactivo_node_cross_ref_nodeId",
            false, listOf("nodeId"), listOf("ASC")))
        val _infoReactivoNodeCrossRef: TableInfo = TableInfo("reactivo_node_cross_ref",
            _columnsReactivoNodeCrossRef, _foreignKeysReactivoNodeCrossRef,
            _indicesReactivoNodeCrossRef)
        val _existingReactivoNodeCrossRef: TableInfo = read(connection, "reactivo_node_cross_ref")
        if (!_infoReactivoNodeCrossRef.equals(_existingReactivoNodeCrossRef)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |reactivo_node_cross_ref(dev.vskelk.cdf.core.database.entity.ReactivoNodeCrossRef).
              | Expected:
              |""".trimMargin() + _infoReactivoNodeCrossRef + """
              |
              | Found:
              |""".trimMargin() + _existingReactivoNodeCrossRef)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "messages", "sessions",
        "norm_sources", "norm_versions", "norm_fragments", "ontology_nodes", "ontology_edges",
        "node_fragment_cross_ref", "reactivos", "reactivo_options", "reactivo_metadata",
        "reactivo_validity", "reactivo_fragment_cross_ref", "reactivo_node_cross_ref")
  }

  public override fun clearAllTables() {
    super.performClear(true, "messages", "sessions", "norm_sources", "norm_versions",
        "norm_fragments", "ontology_nodes", "ontology_edges", "node_fragment_cross_ref",
        "reactivos", "reactivo_options", "reactivo_metadata", "reactivo_validity",
        "reactivo_fragment_cross_ref", "reactivo_node_cross_ref")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(MessageDao::class, MessageDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SessionDao::class, SessionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(NormativeDao::class, NormativeDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(OntologyDao::class, OntologyDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ReactivoDao::class, ReactivoDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun messageDao(): MessageDao = _messageDao.value

  public override fun sessionDao(): SessionDao = _sessionDao.value

  public override fun normativeDao(): NormativeDao = _normativeDao.value

  public override fun ontologyDao(): OntologyDao = _ontologyDao.value

  public override fun reactivoDao(): ReactivoDao = _reactivoDao.value
}
