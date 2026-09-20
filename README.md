# BiciSpot Compose · Redes y Estaciones de Bicicletas Compartidas

Aplicación móvil para Android desarrollada en **Kotlin** con **Jetpack Compose** y arquitectura **MVVM (Model-View-ViewModel)**. Su propósito es la visualización, exploración y consulta en tiempo real de redes de bicicletas compartidas y el estado de disponibilidad de sus estaciones. La información se consume de manera remota mediante una API REST desarrollada sobre MockAPI.

## 🚲 Características Principales

- **Exploración de Redes de Bicicletas:** Consulta el catálogo de redes activas a nivel global con información de ciudad y país de operación.

- **Búsqueda y Filtrado Reactivo:** Motor de búsqueda en memoria que filtra dinámicamente por nombre de red, ciudad o país al escribir en el campo de texto.

- **Detalle de Red y Estaciones:** Vista detallada que lista las estaciones asociadas con métricas en tiempo real de bicicletas disponibles y anclajes libres.

- **Semáforos Visuales de Disponibilidad:** Indicadores semánticos (`IndicadorCupo`) que cambian de color (verde para cupos disponibles, rojo para agotado) para una lectura rápida del estado del servicio.

- **Gestión de Estados Declarativa (UiState):** Interfaces selladas (`sealed interface`) que modelan de forma determinista y predecible los estados `Loading`, `Error` (con opción de reintento) y `Success`.

- **Navegación Centralizada:** Flujo estructurado mediante rutas tipadas (`Screen`), paso de argumentos seguros (`networkId`) y un grafo de navegación desacoplado (`AppNavGraph`).

- **Diseño y Tema Personalizado:** Implementación completa de Material 3 con paleta de colores personalizada de temática ecológica (`VerdeBiciPrimario`, `AcentoNaranja`), soporte de modo claro/oscuro y formas redondeadas (`Shapes`).


## 🏛️ Arquitectura del Sistema

El proyecto sigue las directrices de arquitectura moderna de Android con separación de responsabilidades y flujo unidireccional de datos (UDF):

Plaintext

```
com.example.bicispotcompose/
│
├── data/
│   ├── model/
│   │   └── BikeModels.kt            # Modelos Station y BikeNetwork con @SerializedName
│   ├── remote/
│   │   ├── ApiService.kt            # Interfaz de endpoints Retrofit (GET)
│   │   └── RetrofitInstance.kt      # Singleton del cliente HTTP con Base URL
│   └── repository/
│       └── BikeRepository.kt        # Abstracción y consumo de datos para los ViewModels
│
├── navigation/
│   ├── AppNavGraph.kt               # Configuración del NavHost y declaración de pantallas
│   └── Screen.kt                    # Definición sellada de rutas y parámetros
│
├── ui/
│   ├── screens/
│   │   ├── NetworkListScreen.kt     # Pantalla de listado principal y barra de búsqueda
│   │   └── NetworkDetailScreen.kt   # Pantalla de estaciones con tarjetas e indicadores
│   ├── state/
│   │   ├── NetworkListUiState.kt    # Estados UiState para el listado de redes
│   │   └── NetworkDetailUiState.kt  # Estados UiState para el detalle de la red
│   ├── theme/
│   │   ├── Color.kt                 # Paleta cromática corporativa y colores semánticos
│   │   ├── Shape.kt                 # Especificaciones de curvatura para componentes M3
│   │   ├── Theme.kt                 # Configuración de MaterialTheme (Dark/Light/Dynamic)
│   │   └── Type.kt                  # Jerarquía tipográfica
│   └── viewmodel/
│       ├── NetworkListViewModel.kt  # Lógica de negocio y filtrado del catálogo de redes
│       └── NetworkDetailViewModel.kt# Lógica de carga y enlace de estaciones por red
│
└── MainActivity.kt                  # Punto de entrada de la app y contenedor de navegación
```
## 🛠️ Stack Tecnológico y Dependencias

- **Lenguaje:** [Kotlin](https://kotlinlang.org/?utm_source=gemini)

- **Framework UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose?utm_source=gemini) con [Material 3](https://m3.material.io/?utm_source=gemini)

- **Navegación:** `androidx.navigation:navigation-compose`

- **Red y Serialización:** `com.squareup.retrofit2:retrofit` y `converter-gson`

- **Ciclo de Vida y Asincronía:** Android Lifecycle ViewModel Compose, Coroutines y `StateFlow`

- **Iconografía:** `androidx.compose.material:material-icons-extended`


## ⚙️ Especificaciones Técnicas

|**Parámetro**|**Configuración**|
|---|---|
|**Compile SDK**|37|
|**Target SDK**|37|
|**Min SDK**|24 (Android 7.0 Nougat o superior)|
|**Gradle Version**|9.7.1|
|**Java / JVM Target**|Java 11 (`VERSION_11`)|
|**Package Name / Namespace**|`com.example.bicispotcompose`|

## 🌐 Servicios API REST (MockAPI)

El cliente Retrofit (`RetrofitInstance`) consume la siguiente API:

**Base URL:** `[https://6aac7b00a2413bf0ec10d089.mockapi.io/api/v1/](https://6aac7b00a2413bf0ec10d089.mockapi.io/api/v1/)`

### Endpoints

- `GET /networks` – Retorna la lista de todas las redes de bicicletas disponibles.

- `GET /networks/{id}` – Obtiene la información detallada de una red por su identificador.

- `GET /stations` – Devuelve el catálogo general de estaciones de bicicletas con sus capacidades de anclaje y geolocalización.


## 🚀 Instalación y Despliegue

### 1. Clonar el repositorio

Bash

```
git clone https://github.com/Isabel-C-Acevedo-G/Bicisport/tree/main
cd BiciSpotCompose
```

### 2. Abrir en Android Studio

1. Inicia **Android Studio** (versión compatible con Gradle 9.7+).

2. Selecciona **Open** y navega hasta la carpeta del proyecto.

3. Espera a que finalice la sincronización de Gradle (`Sync Project with Gradle Files`).


### 3. Ejecución

- Para ejecutar en emulador o dispositivo físico: presiona **Run ('app')** o el atajo `Shift + F10`.

- El artefacto compilado debug se genera en la ruta: `app/build/intermediates/apk/debug/app-debug.apk`.