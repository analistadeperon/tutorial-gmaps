package co.tiagoaguiar.tutorial.gmaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {

  private val places = arrayListOf(
    Place("Google", LatLng(-23.5868031,-46.6843406), "Av. Brg. Faria Lima, 3477 - 18º Andar - Itaim Bibi, São Paulo - SP", 4.8f),
    Place("Parque", LatLng(-23.5899619,-46.66747), "Av. República do Líbano, 1111 - Ibirapuera, São Paulo - SP", 4.9f),
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
    mapFragment.getMapAsync { googleMap ->
      googleMap.setInfoWindowAdapter(MarkerInfoAdapter(this))
      addMarkers(googleMap)

      googleMap.setOnMapLoadedCallback {
        val bounds = LatLngBounds.builder()

        places.forEach {
          bounds.include(it.latLng)
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
      }
    }
  }

  private fun addMarkers(googleMap: GoogleMap) {
    places.forEach { place ->
      val marker = googleMap.addMarker(
        MarkerOptions()
          .title(place.name)
          .snippet(place.address)
          .position(place.latLng)
          .icon(
            BitmapHelper.vectorToBitmap(this, R.drawable.ic_android_black_24dp, ContextCompat.getColor(this, R.color.teal_200))
          )
      )

      marker.tag = place
    }
  }

}


data class Place(
  val name: String,
  val latLng: LatLng,
  val address: String,
  val rating: Float
)