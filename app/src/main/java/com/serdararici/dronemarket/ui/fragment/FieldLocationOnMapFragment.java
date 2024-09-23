package com.serdararici.dronemarket.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.serdararici.dronemarket.R;
import com.serdararici.dronemarket.databinding.FragmentFieldLocationOnMapBinding;
import com.serdararici.dronemarket.ui.viewmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FieldLocationOnMapFragment extends Fragment implements OnMapReadyCallback {
    FragmentFieldLocationOnMapBinding binding;
    private MainViewModel viewModelMain;
    private GoogleMap mMap;
    private boolean isMapReady = false;
    private boolean isSatelliteView = false;


    private double latitude;
    private double longitude;
    private String fieldName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelMain = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFieldLocationOnMapBinding.inflate(inflater,container,false);

        // MainActivity'den DrawerLayout'u al
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);

        // Drawer'ın kaydırma ile açılmasını devre dışı bırak
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

        viewModelMain.selectedField.observe(getViewLifecycleOwner(), field -> {
            latitude = field.getLatitude();
            longitude = field.getLongitude();
            fieldName = field.getFieldName();

            if(isMapReady){
                showLocationOnMap();
            }

        });

        // Map Fragment'i başlatma
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapField);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this); // Haritayı hazır olduğunda geri çağırır
        }

        binding.ibZoomIn.setOnClickListener(view -> {
            if (mMap != null) {
                // Geçerli yakınlık seviyesini al ve artır
                float currentZoomLevel = mMap.getCameraPosition().zoom;
                mMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoomLevel + 1)); // Yakınlaştır
            }
        });

        binding.ibZoomOut.setOnClickListener(view -> {
            if (mMap != null) {
                // Geçerli yakınlık seviyesini al ve azalt
                float currentZoomLevel = mMap.getCameraPosition().zoom;
                mMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoomLevel - 1)); // Uzaklaştır
            }
        });


        binding.ibMapType.setOnClickListener(view -> {
            if (mMap != null) {
                if (isSatelliteView) {
                    // Şu anda uydu görünümündeyse, normal görünüme geç
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    binding.ibMapType.setImageResource(R.drawable.baseline_map_24_black);
                    isSatelliteView = false;
                } else {
                    // Şu anda normal görünümdeyse, uydu görünümüne geç
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    binding.ibMapType.setImageResource(R.drawable.baseline_map_24_green);
                    isSatelliteView = true;
                }
            }
        });

        binding.btnBackFieldLocationOnMap.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });




        return binding.getRoot();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        isMapReady = true;

        // Eğer veriler geldiyse harita hazır olduğunda göster
        if (latitude != 0 && longitude != 0) {
            showLocationOnMap();
        }
    }

    // Konum gösterme işlemi
    private void showLocationOnMap() {
        LatLng location = new LatLng(latitude, longitude);
        mMap.clear(); // Eski işaretçileri temizle
        mMap.addMarker(new MarkerOptions().position(location).title(fieldName));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13), 2000, null); // Kamerayı konuma yaklaştır
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // MainActivity'den DrawerLayout'u al
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);

        // Drawer'ın kaydırma ile açılmasını tekrar aktif hale getir
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}