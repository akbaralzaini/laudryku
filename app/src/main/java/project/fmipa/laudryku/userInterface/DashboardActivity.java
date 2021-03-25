package project.fmipa.laudryku.userInterface;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import project.fmipa.laudryku.CurvedBottomNavigationView;
import project.fmipa.laudryku.R;

public class DashboardActivity extends FragmentActivity {
    private HomeFragment homeFragment;
    private ListOrderFragment listOrderFragment;
    private ProfilFragment profilFragment;
    private NotifikasiFragment notifikasiFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FloatingActionButton floatingActionButton = findViewById(R.id.add_order);
        homeFragment = new HomeFragment();
        setFragment(homeFragment);

        CurvedBottomNavigationView mView = findViewById(R.id.bottom);
        mView.inflateMenu(R.menu.bottom_app_bar);
        mView.setSelectedItemId(R.id.navigation_home);
        //getting bottom navigation view and attaching the listener
        mView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    setFragment(homeFragment);
                    return true;
                case R.id.navigation_order:
                    listOrderFragment = new ListOrderFragment();
                    setFragment(listOrderFragment);
                    return true;
                case R.id.navigation_notifications:
                    notifikasiFragment = new NotifikasiFragment();
                    setFragment(notifikasiFragment);
                    return true;
                case R.id.navigation_profil:
                    profilFragment = new ProfilFragment();
                    setFragment(profilFragment);
                    return true;
                default:
                    return false;
            }
        });

        //listener floting button
        floatingActionButton.setOnClickListener(v -> {
            Intent addOrder = new Intent(DashboardActivity.this,AddOrderActivity.class);
            startActivity(addOrder);
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }


}
