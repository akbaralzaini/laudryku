package project.fmipa.laudryku.userInterface;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import project.fmipa.laudryku.R;
import project.fmipa.laudryku.model.Laundry;
import project.fmipa.laudryku.util.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    private TextView tvNamaLaundry;
    private TextView tvNamaPemilik;
    private TextView tvNoTlp;
    private TextView tvAlamat;
    private TextView tvEmail;
    private Button btnKelolaJenis, btnEditProfil;
    private SharedPrefManager sharedPrefManager;


    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profil, container, false);

        //deklarasi
        TextView tvLogout = rootView.findViewById(R.id.logout_button);
        sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getContext()));
        tvAlamat = rootView.findViewById(R.id.alamat_laundry);
        tvNamaPemilik = rootView.findViewById(R.id.nama_pemilik);
        tvNamaLaundry = rootView.findViewById(R.id.nama_laundry);
        tvNoTlp = rootView.findViewById(R.id.nomor_tlp);
        tvEmail = rootView.findViewById(R.id.email_laundry);
        btnKelolaJenis = rootView.findViewById(R.id.kelola_jenis_barang);
        btnEditProfil = rootView.findViewById(R.id.edit_profil);

        refresh();

        btnKelolaJenis.setOnClickListener(v -> {
            Intent kelola = new Intent(getContext(),JenisBarangActivity.class);
            startActivity(kelola);
        });

        tvLogout.setOnClickListener(v -> {
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            Intent logut = new Intent(getContext(),MainActivity.class);
            startActivity(logut);
            Objects.requireNonNull(getActivity()).finish();
        });

        btnEditProfil.setOnClickListener(v -> {
            Intent edit = new Intent(getContext(),UpdateProfilActivity.class);
            startActivity(edit);
        });
        return  rootView;
    }

    private void refresh(){
        Laundry laundry = sharedPrefManager.getLaundry();
        tvNamaLaundry.setText(laundry.getNama_laundry());
        tvNamaPemilik.setText(laundry.getNama_pemilik());
        tvAlamat.setText(laundry.getAlamat());
        tvNoTlp.setText(laundry.getTlp());
        tvEmail.setText(sharedPrefManager.getSpUsername());
    }

}
