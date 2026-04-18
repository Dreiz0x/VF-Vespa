package dev.vskelk.cdf.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.vskelk.cdf.core.database.entity.CuarentenaFragmentoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CuarentenaDao {
    @Query("SELECT * FROM cuarentena_fragmentos WHERE estado = 'PENDIENTE' OR estado = 'CONFLICTO' ORDER BY creadoEn DESC")
    fun observePendientes(): Flow<List<CuarentenaFragmentoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fragmentos: List<CuarentenaFragmentoEntity>)

    @Query("UPDATE cuarentena_fragmentos SET estado = :estado WHERE id = :id")
    suspend fun updateEstado(id: Long, estado: String)

    @Query("DELETE FROM cuarentena_fragmentos WHERE estado = 'APROBADO'")
    suspend fun deletAprobados()

    @Query("SELECT * FROM cuarentena_fragmentos WHERE id = :id")
    suspend fun getById(id: Long): CuarentenaFragmentoEntity?
}
