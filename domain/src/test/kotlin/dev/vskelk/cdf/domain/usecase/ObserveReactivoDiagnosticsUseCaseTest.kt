package dev.vskelk.cdf.domain.usecase

import com.google.common.truth.Truth.assertThat
import dev.vskelk.cdf.domain.fakes.FakeReactivoRepository
import dev.vskelk.cdf.domain.fakes.sampleReactivoAggregate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ObserveReactivoDiagnosticsUseCaseTest {

    @Test
    fun groupsDiagnosticsByExamArea() = runTest {
        val reactivos = listOf(
            sampleReactivoAggregate(1L).reactivo,
            sampleReactivoAggregate(2L).reactivo.copy(
                id = 2L,
                examArea = "SISTEMA",
                cognitiveLevel = "APPLY",
            ),
            sampleReactivoAggregate(3L).reactivo.copy(
                id = 3L,
                examArea = "TECNICO",
                cognitiveLevel = "ANALYZE",
            ),
        )
        val aggregates = reactivos.mapIndexed { index, reactivo ->
            sampleReactivoAggregate(index.toLong() + 1).copy(reactivo = reactivo)
        }
        val repository = FakeReactivoRepository(reactivos = reactivos, aggregates = aggregates)
        val result = ObserveReactivoDiagnosticsUseCase(repository).invoke().first()
        assertThat(result).hasSize(2)
        assertThat(result.first { it.examArea == "TECNICO" }.count).isEqualTo(2)
        assertThat(result.first { it.examArea == "SISTEMA" }.count).isEqualTo(1)
    }
}
