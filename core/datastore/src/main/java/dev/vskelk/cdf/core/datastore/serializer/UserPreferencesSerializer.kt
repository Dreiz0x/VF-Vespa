package dev.vskelk.cdf.core.datastore.serializer

import androidx.datastore.core.Serializer
import dev.vskelk.cdf.core.datastore.UserPreferences
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences.newBuilder()
        .setOfflineMode(false)
        .setDecisionEngineEnabled(true)
        .build()

    override suspend fun readFrom(input: InputStream): UserPreferences =
        runCatching { UserPreferences.parseFrom(input) }.getOrDefault(defaultValue)

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) =
        t.writeTo(output)
}
