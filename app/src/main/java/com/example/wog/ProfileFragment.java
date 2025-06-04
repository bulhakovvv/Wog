package com.example.wog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private TextView tvName, tvCard, tvBonus, tvFuel, tvCoffee;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = view.findViewById(R.id.tvName);
        tvCard = view.findViewById(R.id.tvCard);
        tvBonus = view.findViewById(R.id.tvBonusBalance);
        tvFuel = view.findViewById(R.id.tvFuelBalance);
        tvCoffee = view.findViewById(R.id.tvCoffeeCount);
        // Встановлення даних профілю
        tvName.setText("Ім'я: " + DataManager.userName);
        tvCard.setText("Карта PRIDE: " + DataManager.cardNumber);
        tvBonus.setText(String.format("Бонусний баланс: %.2f грн", DataManager.bonusBalance));
        tvFuel.setText(String.format("Залишок пального: %.0f л", DataManager.fuelBalance));
        tvCoffee.setText("Кавові напої: " + DataManager.coffeeCount);
    }
}
