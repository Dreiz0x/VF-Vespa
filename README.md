# Vespa

**Sistema de preparación integral para el Servicio Profesional Electoral Nacional (SPEN)**

Package: `dev.vskelk.cdf` · Autor: VSkel-K · Versión: 2.0.0

---

## Qué es Vespa

Vespa no es un banco de preguntas ni una interfaz de chat. Es un sistema que convierte normativa electoral, conocimiento operativo y relaciones ontológicas en un motor trazable de preparación, diagnóstico y simulación para el concurso SPEN.

El núcleo está en `:core:database`, donde el schema modela la cadena:

```
NormSource → NormVersion → NormFragment → OntologyNode → Reactivo → ReactivoMetadata → ReactivoValidity
```

Cualquier reactivo puede justificarse, validarse, invalidarse por reforma y relacionarse con nodos semánticos reutilizables.

---

## Módulos

```
:app                    Punto de entrada, navegación, DI raíz
:benchmark              Baseline profile
:core:common            Tipos compartidos, NetworkMonitor, AppResult, AppError
:core:network           HTTP, interceptores, retry, circuit breaker, Anthropic API
:core:database          Room v2 — schema conversacional + ontológico (14 entidades, 5 DAOs)
:core:datastore         Proto DataStore cifrado con Android Keystore
:data:repository        Implementaciones, mappers, WorkManager
:domain                 Modelos puros, contratos, DecisionEngine, 18 UseCases
:feature:main           Chat + navegación principal
:feature:chaos          Monitor operativo (red, circuit breaker, cola)
:feature:simulador      Sesiones de reactivos filtradas por área
:feature:diagnostico    Análisis de brechas por área y nivel cognitivo
:feature:entrevista     Preparación por competencias del cargo
```

---

## Build

**Requisitos:** JDK 21 · Android SDK 35 · Gradle wrapper incluido

```bash
# Debug
./gradlew assembleDebug

# Validación completa
./gradlew spotlessCheck detekt testDebugUnitTest assembleDebug

# Release
./gradlew :app:assembleRelease

# Antes de PR
./gradlew spotlessApply
./gradlew spotlessCheck detekt testDebugUnitTest assembleDebug
```

---

## Logo

Copiar el logo al proyecto antes de compilar:

```bash
cp logo.png app/src/main/res/drawable/ic_vespa_logo.png
```

---

## Seguridad

- API key cifrada en DataStore con Android Keystore — nunca en BuildConfig
- Certificate pinning configurable en `res/values/strings.xml`
- Logging HTTP solo en debug
- ProGuard/R8 configurado para release

---

## Decision Engine

Vespa incluye un motor determinista que decide la ruta de cada envío:

| Condición | Ruta |
|---|---|
| Sin API key | `BLOCK_MISSING_KEY` |
| Offline + caché | `SERVE_CACHE` |
| Offline sin caché | `QUEUE_AND_DEFER` |
| Circuit breaker abierto + caché | `SERVE_CACHE` |
| Online y saludable | `SEND_REMOTE` |

---

## Examen SPEN 2026

| Módulo | Preguntas |
|---|---|
| Lenguaje y comunicación | 60 |
| Competencia matemática | 30 |
| Sistema político y electoral | 40 |
| Conocimientos técnicos del cargo | 40 |
| **Total** | **170** |

Duración: 5 horas · Opciones: 3 por reactivo

---

## Filosofía del repositorio

Cada pieza debe responder tres preguntas:

1. ¿Qué responsabilidad tiene?
2. ¿Cómo se reemplaza sin romper el resto?
3. ¿Cómo se verifica?

Si una pieza no responde bien esas tres preguntas, no está terminada.

---

**Vespa: conocimiento versionado, ontología trazable, ejecución Android lista para endurecerse.**
