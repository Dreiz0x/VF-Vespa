package dev.vskelk.cdf.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cuarentena_fragmentos")
data class CuarentenaFragmentoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val contenido: String,
    val fuente: String,
    val fuenteTipo: String,       // DOCUMENTO_USUARIO | INVESTIGACION_IA
    val certeza: String,          // ALTA | MEDIA | BAJA
    val areaExamen: String?,
    val conflictoConId: Long?,
    val conflictoDescripcion: String?,
    val estado: String = "PENDIENTE", // PENDIENTE | APROBADO | RECHAZADO | CONFLICTO
    val creadoEn: Long = System.currentTimeMillis(),
)
