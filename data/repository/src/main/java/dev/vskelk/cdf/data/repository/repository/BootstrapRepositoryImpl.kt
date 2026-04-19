package dev.vskelk.cdf.data.repository.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.vskelk.cdf.core.database.dao.NormativeDao
import dev.vskelk.cdf.core.database.dao.OntologyDao
import dev.vskelk.cdf.core.database.dao.ReactivoDao
import dev.vskelk.cdf.core.datastore.datasource.PreferencesDataSource
import dev.vskelk.cdf.domain.model.BootstrapState
import dev.vskelk.cdf.domain.model.SeedManifest
import dev.vskelk.cdf.domain.repository.BootstrapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.int
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootstrapRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferencesDataSource: PreferencesDataSource,
    private val normativeDao: NormativeDao,
    private val ontologyDao: OntologyDao,
    private val reactivoDao: ReactivoDao,
) : BootstrapRepository {

    private val _state = MutableStateFlow<BootstrapState>(BootstrapState.Checking)

    override fun observe(): Flow<BootstrapState> = _state

    override suspend fun initialize() {
        _state.value = BootstrapState.Checking

        try {
            val manifestRaw = context.assets.open("seed/seed_manifest.json")
                .bufferedReader().readText()
            val manifest = parseManifest(manifestRaw)

            val appliedVersion = preferencesDataSource.observeSeedVersionApplied().first()

            val normCount = normativeDao.observeAllSources().first().size
            val reactCount = reactivoDao.observeAllReactivos().first().size

            val needsSeed = appliedVersion != manifest.version ||
                normCount < manifest.minNormativa ||
                reactCount < manifest.minReactivos

            if (needsSeed) {
                _state.value = BootstrapState.Seeding("Verificando base de datos...")
                seedNormativa(manifest)

                _state.value = BootstrapState.Seeding("Cargando ontología...")
                seedOntologia(manifest)

                _state.value = BootstrapState.Seeding("Preparando reactivos...")
                seedReactivos(manifest)

                preferencesDataSource.setSeedVersionApplied(manifest.version)
            }

            _state.value = BootstrapState.Ready
        } catch (e: Exception) {
            _state.value = BootstrapState.Error(e.message ?: "Error desconocido")
        }
    }

    private fun parseManifest(raw: String): SeedManifest {
        val obj = Json.parseToJsonElement(raw).jsonObject
        return SeedManifest(
            version = obj["version"]!!.jsonPrimitive.content,
            minReactivos = obj["minReactivos"]!!.jsonPrimitive.int,
            minNormativa = obj["minNormativa"]!!.jsonPrimitive.int,
            minOntologia = obj["minOntologia"]!!.jsonPrimitive.int,
        )
    }

    private suspend fun seedNormativa(manifest: SeedManifest) {
        // Leer e insertar normativa.json — implementación en siguiente iteración
        // cuando Rey valide el contenido del JSON
    }

    private suspend fun seedOntologia(manifest: SeedManifest) {
        // Leer e insertar ontologia.json — implementación en siguiente iteración
    }

    private suspend fun seedReactivos(manifest: SeedManifest) {
        // Leer e insertar reactivos.json — implementación en siguiente iteración
    }
}
