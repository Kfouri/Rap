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
     Pantalla inicial. 
  * MainActivity
     Muestra tres listados de películas y series (Popular, Top Ranking y Próximos Lanzamientos), también permite filtrar información por cada listado.
  * MovieDataActivity
     Muestra el detalle de la película seleccionada.
  * TvDataActivity
     Muestra el detalle de la serie seleccionada.
  * GenericAdapter
     Permite adaptar los datos de las películas y series al componente visual (RecycledView)
  * SeasonAdapter
     Permite adaptar los datos de las temporadas dentro del detalle de las series al componente visual (RecycledView)
* Modelos
   Las clases de la capa de modelo nos permiten albergar información recibida desde el backend (ROOM/Retrofit) y se envían a la capa de Controlador para que luego sea procesada y enviada a la capa de Vistas. 
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
  Las clases de la capa Controlador nos permiten intermediar entre las capas de Modelo y la capa de Vistas. Esta capa tiene el poder de manipular la información recibida desde o hacia el backend/vistas.
  * MainActivityViewModel
  * APIInterface
* Persistencia
   * Dao
     Las clase Dao tienen la responsabilidad de la persistencia (ABMC) de los datos, en este caso con ROOM.
     * PopularDao
     * TopRatedDao
     * UpcomingDao
   * Database
     La clase Database nos permite definir la configuración de persistencia con ROOM
     * Database
   * Model
     Estas clases de modelo nos permiten albergar información para el manejo de la persistencia de dato.
     * PopularModel
     * TopRatedModel
     * UpcomingModel
* Helpers
  Las clases de tipo Helpers nos permiten usar metodos y constantes (datos) a lo largo de toda la aplicación.
  * Utils
  * Constants


##### En qué consiste el principio de responsabilidad única? Cuál es su propósito?
   El Principio de responsabilidad única es el primero de los cinco que componen SOLID que basicamente dice que un objeto debe realizar una única cosa, debe tener solo un objetivo, debe ser atomico. Esto nos protege de futuros cambios.

##### Qué características tiene, según su opinión, un “buen” código o código limpio?
   El codigo limpio debe ser algo vital en el desarrollo de una aplicación, ya que un codigo limpio es mas facil de leer e interpretar ya sea por quien lo hizo o de futuros desarrolladores. Si el codigo es entendible o sea.. si la nomenclatura de los metodos o variables son suficientemente claros no haria falta agregar comentarios para explicar que hace el codigo.


