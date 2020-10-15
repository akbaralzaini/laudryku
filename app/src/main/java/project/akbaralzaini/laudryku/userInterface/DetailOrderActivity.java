package project.akbaralzaini.laudryku.userInterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import project.akbaralzaini.laudryku.R;
import project.akbaralzaini.laudryku.model.DetailOrder;
import project.akbaralzaini.laudryku.rest.ApiClient;
import project.akbaralzaini.laudryku.rest.DetailOrderApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderActivity extends Activity {
    DetailOrderApiInterface detailOrderApiInterface;
    TextView tvIdOrder,tvTanggal,tvTotalBayar;
    private int id_order;
    private LinearLayout listdet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        detailOrderApiInterface = ApiClient.getClient().create(DetailOrderApiInterface.class);
        tvIdOrder = findViewById(R.id.id_order);
        tvTanggal = findViewById(R.id.tanggal_order);
        tvTotalBayar = findViewById(R.id.total_bayar_detail);
        listdet = findViewById(R.id.deskripsi);

        Intent i = getIntent();
        id_order = i.getIntExtra("id_order",0);
        String or = i.getStringExtra("nama_pemesan")+" #"+id_order;
        tvIdOrder.setText(or);
        tvTanggal.setText(i.getStringExtra("tanggal_order"));
        tvTotalBayar.setText(i.getStringExtra("total_bayar"));

        refresh();
    }

    private void refresh() {

        Call<List<DetailOrder>> getdetail = detailOrderApiInterface.getDetail(id_order);
        getdetail.enqueue(new Callback<List<DetailOrder>>() {
            @Override
            public void onResponse(Call<List<DetailOrder>> call, Response<List<DetailOrder>> response) {
                List<DetailOrder> list = response.body();
                for (int i=0;i<list.size();i++){
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    assert layoutInflater != null;
                    final View addView = layoutInflater.inflate(R.layout.row_detail_order,null);
                    TextView textOut = (TextView) addView.findViewById(R.id.jenis_barang);
                    TextView tvHarga = (TextView) addView.findViewById(R.id.harga_barang);

                    String barang = "" + list.get(i).getNama_jenis() + " " + list.get(i).getLama_waktu() + " hari x " + list.get(i).getBerat() + " Kg";
                    Locale locale = new Locale("id", "ID");
                    NumberFormat sharga = NumberFormat.getCurrencyInstance(locale);
                    float harga = list.get(i).getTotal_harga();
                    textOut.setText(barang);
                    tvHarga.setText(sharga.format(harga));

                    listdet.addView(addView);
                }
            }

            @Override
            public void onFailure(Call<List<DetailOrder>> call, Throwable t) {

            }
        });
    }
}
