package com.example.wog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {
    private View btnFuel, btnCoffee, btnCafe, btnWogPay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Отримання посилань на елементи UI
        btnFuel = view.findViewById(R.id.btnFuel);
        btnCoffee = view.findViewById(R.id.btnCoffee);
        btnCafe = view.findViewById(R.id.btnCafe);
        btnWogPay = view.findViewById(R.id.btnWogPay);

        btnFuel.setOnClickListener(v -> {
            // Перехід до вкладки "Карта"
            BottomNavigationView nav = getActivity().findViewById(R.id.bottom_navigation);
            if (nav != null) {
                nav.setSelectedItemId(R.id.navigation_map);
            }
        });
        btnCoffee.setOnClickListener(v -> {
            // Емуляція сервісу WOG  Кава
            Toast.makeText(getContext(), "WOG PAY Кава – функція не реалізована", Toast.LENGTH_SHORT).show();
        });
        btnCafe.setOnClickListener(v -> {

            Toast.makeText(getContext(), "WOG PAY Кафе – функція не реалізована", Toast.LENGTH_SHORT).show();
        });
        btnWogPay.setOnClickListener(v -> {
            // Емуляція оплати пального (WOG PAY)
            String result = DataManager.simulatePayment(getContext());
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        });
    }
}
