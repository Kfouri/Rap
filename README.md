# RappiTest

En esta aplicación se demuestra el uso y consumo del webservice de themoviedb para Android.

El desarrollo esta compuesto por una pantalla principal en el cual esta dividida en tres categorías:

  * Popularity
  * Top Rated
  * Upcoming
  
En todas las categorías se obtienen peliculas y/o series de tv, también se pueden filtrar tanto online como offline.

En caso de no estar conectado a internet, las imagenes de las portadas las obtiene de la cache.

Ingresando a las peliculas/series se puede ver el detalle de cada una como por ejemplo el nombre, una breve descripción, fecha de lanzamiento, preview del video de lanzamiento (si es proveeido), etc.

## Capas de la aplicación

* Vistas
  * SplashActivity
  * MainActivity
  * MovieDataActivity
  * TvDataActivity
  * GenericAdapter
  * SeasonAdapter
* Modelos
  * Movie
  * Tv
  * Video
  * VideoDetail
  * VideoPlay
  * Season
  * MovieDataResponse
  * MovieResponse
  * TvDataResponse
  * TvResponse
* Controlador
  * MainActivityViewModel
  * APIInterface
* Persistencia
   * Dao
     * PopularDao
     * TopRatedDao
     * UpcomingDao
   * Database
     * Database
   * Model
     * PopularModel
     * TopRatedModel
     * UpcomingModel
* Helpers
  * Utils
  * Constants

## Responsabilidades

##### En qué consiste el principio de responsabilidad única? Cuál es su propósito?

##### Qué características tiene, según su opinión, un “buen” código o código limpio?


