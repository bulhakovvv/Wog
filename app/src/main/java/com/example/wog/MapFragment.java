package com.example.wog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private EditText etOrigin, etDestination, etConsumption;
    private Spinner spinnerFuel;
    private TextView tvResult;
    private Button btnCalculate, btnBuy;
    private LatLng currentLocation;
    private double lastLiters = 0.0;
    private double lastBonus = 0.0;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etOrigin = view.findViewById(R.id.etOrigin);
        etDestination = view.findViewById(R.id.etDestination);
        etConsumption = view.findViewById(R.id.etConsumption);
        spinnerFuel = view.findViewById(R.id.spinnerFuel);
        tvResult = view.findViewById(R.id.textResult);
        btnCalculate = view.findViewById(R.id.btnCalculate);
        btnBuy = view.findViewById(R.id.btnBuy);

        List<String> fuelOptions = new ArrayList<>();
        for (String name : DataManager.fuelPrices.keySet()) {
            double price = DataManager.fuelPrices.get(name);
            fuelOptions.add(name + " - " + String.format(Locale.getDefault(), "%.2f грн/л", price));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                fuelOptions
        );
        spinnerFuel.setAdapter(adapter);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            if (mMap != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12f));
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        btnCalculate.setOnClickListener(v -> calculateRoute());
        btnBuy.setOnClickListener(v -> performPurchase());
    }

    private void calculateRoute() {
        // ... (залиште існуючу логіку розрахунку без змін)
    }

    private void performPurchase() {
        if (!tvResult.isShown()) {
            Toast.makeText(getContext(), "Спочатку розрахуйте маршрут", Toast.LENGTH_SHORT).show();
            return;
        }
        String message = DataManager.simulatePayment(getContext());
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        DataManager.fuelBalance += lastLiters;
        DataManager.bonusBalance += lastBonus;
        BottomNavigationView nav = requireActivity().findViewById(R.id.bottom_navigation);
        if (nav != null) {
            nav.setSelectedItemId(R.id.navigation_home);
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }
}
