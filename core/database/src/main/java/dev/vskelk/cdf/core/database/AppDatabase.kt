package dev.vskelk.cdf.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.vskelk.cdf.core.database.dao.MessageDao
import dev.vskelk.cdf.core.database.dao.NormativeDao
import dev.vskelk.cdf.core.database.dao.OntologyDao
import dev.vskelk.cdf.core.database.dao.ReactivoDao
import dev.vskelk.cdf.core.database.dao.SessionDao
import dev.vskelk.cdf.core.database.dao.CuarentenaDao
import dev.vskelk.cdf.core.database.entity.MessageEntity
import dev.vskelk.cdf.core.database.entity.NodeFragmentCrossRef
import dev.vskelk.cdf.core.database.entity.NormFragmentEntity
import dev.vskelk.cdf.core.database.entity.NormSourceEntity
import dev.vskelk.cdf.core.database.entity.NormVersionEntity
import dev.vskelk.cdf.core.database.entity.OntologyEdgeEntity
import dev.vskelk.cdf.core.database.entity.OntologyNodeEntity
import dev.vskelk.cdf.core.database.entity.ReactivoEntity
import dev.vskelk.cdf.core.database.entity.ReactivoFragmentCrossRef
import dev.vskelk.cdf.core.database.entity.ReactivoMetadataEntity
import dev.vskelk.cdf.core.database.entity.ReactivoNodeCrossRef
import dev.vskelk.cdf.core.database.entity.ReactivoOptionEntity
import dev.vskelk.cdf.core.database.entity.ReactivoValidityEntity
import dev.vskelk.cdf.core.database.entity.SessionEntity
import dev.vskelk.cdf.core.database.entity.CuarentenaFragmentoEntity

@Database(
    entities = [
        MessageEntity::class,
        SessionEntity::class,
        NormSourceEntity::class,
        NormVersionEntity::class,
        NormFragmentEntity::class,
        OntologyNodeEntity::class,
        OntologyEdgeEntity::class,
        NodeFragmentCrossRef::class,
        ReactivoEntity::class,
        ReactivoOptionEntity::class,
        ReactivoMetadataEntity::class,
        ReactivoValidityEntity::class,
        ReactivoFragmentCrossRef::class,
        ReactivoNodeCrossRef::class,
        CuarentenaFragmentoEntity::class,
    ],
    version = 3,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun sessionDao(): SessionDao
    abstract fun normativeDao(): NormativeDao
    abstract fun ontologyDao(): OntologyDao
    abstract fun reactivoDao(): ReactivoDao
    abstract fun cuarentenaDao(): CuarentenaDao
}
