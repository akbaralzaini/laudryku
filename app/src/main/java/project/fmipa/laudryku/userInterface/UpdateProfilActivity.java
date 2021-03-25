package project.fmipa.laudryku.userInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import project.fmipa.laudryku.R;
import project.fmipa.laudryku.model.Laundry;
import project.fmipa.laudryku.model.UpdateLaundry;
import project.fmipa.laudryku.rest.ApiClient;
import project.fmipa.laudryku.rest.LaundryApiInterface;
import project.fmipa.laudryku.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfilActivity extends Activity {

    private SharedPrefManager sharedPrefManager;
    private Button btnSimpan;
    private EditText tvAlamat,tvNamaPemilik,tvNoTlp,tvNamaLaundry;
    LaundryApiInterface laundryApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);
        laundryApiInterface = ApiClient.getClient().create(LaundryApiInterface.class);

        sharedPrefManager = new SharedPrefManager(this);
        tvAlamat = findViewById(R.id.alamat_laundry);
        tvNamaPemilik = findViewById(R.id.nama_pemilik);
        tvNamaLaundry = findViewById(R.id.nama_laundry);
        tvNoTlp = findViewById(R.id.nomor_tlp);
        btnSimpan = findViewById(R.id.simpan_profil);

        Laundry laundry = sharedPrefManager.getLaundry();
        tvNamaLaundry.setText(laundry.getNama_laundry());
        tvNamaPemilik.setText(laundry.getNama_pemilik());
        tvAlamat.setText(laundry.getAlamat());
        tvNoTlp.setText(laundry.getTlp());

        btnSimpan.setOnClickListener(v -> {
            UpdateLaundry updateLaundry = new UpdateLaundry(tvNamaLaundry.getText().toString(),tvNamaPemilik.getText().toString(),tvAlamat.getText().toString(),tvNoTlp.getText().toString());
            Log.e("kksks","sss"+tvNoTlp.getText().toString());
            Call<Laundry> laundryCall = laundryApiInterface.updateLaundry(updateLaundry,laundry.getId_laundry());
            laundryCall.enqueue(new Callback<Laundry>() {
                @Override
                public void onResponse(Call<Laundry> call, Response<Laundry> response) {
                    Laundry laundry1 = response.body();
                    sharedPrefManager.storeFavorites(laundry1);

                    Intent home = new Intent(UpdateProfilActivity.this,DashboardActivity.class);
                    startActivity(home);
                }

                @Override
                public void onFailure(Call<Laundry> call, Throwable t) {

                }
            });
        });

    }
}
