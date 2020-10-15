package project.akbaralzaini.laudryku.userInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import project.akbaralzaini.laudryku.R;
import project.akbaralzaini.laudryku.model.AddLaundry;
import project.akbaralzaini.laudryku.model.DetailLoundry;
import project.akbaralzaini.laudryku.model.JenisBarang;
import project.akbaralzaini.laudryku.model.Laundry;
import project.akbaralzaini.laudryku.model.Order;
import project.akbaralzaini.laudryku.rest.ApiClient;
import project.akbaralzaini.laudryku.rest.DetailOrderApiInterface;
import project.akbaralzaini.laudryku.rest.JenisBarangApiInterface;
import project.akbaralzaini.laudryku.rest.OrderApiInterface;
import project.akbaralzaini.laudryku.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOrderActivity extends Activity {
    private Button btnAddDetail,btnTambahOrder;
    private LinearLayout llDetail;
    private EditText edtNamaPemesan,edtTlp,edtBerat,edtAlamat;
    private SharedPrefManager sharedPrefManager;
    private TextView tvTotal;
    private Spinner sDaftarJenis;
    JenisBarangApiInterface jenisBarangApiInterface;
    OrderApiInterface orderApiInterface;
    DetailOrderApiInterface detailOrderApiInterface;
    List<JenisBarang> list = new ArrayList<JenisBarang>();
    List<DetailLoundry> listDetail = new ArrayList<>();
    private float total_bayar=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        llDetail = findViewById(R.id.detail);
        sDaftarJenis = findViewById(R.id.jenis);
        edtBerat = findViewById(R.id.berat_order);
        tvTotal = findViewById(R.id.total_bayar);
        edtNamaPemesan = findViewById(R.id.nama_order);
        edtAlamat = findViewById(R.id.alamat_order);
        edtTlp = findViewById(R.id.nomor_telpon);
        btnTambahOrder = findViewById(R.id.button_tambah);
        sharedPrefManager = new SharedPrefManager(AddOrderActivity.this);
        orderApiInterface = ApiClient.getClient().create(OrderApiInterface.class);
        detailOrderApiInterface = ApiClient.getClient().create(DetailOrderApiInterface.class);


        //spinner dan detail
        tvTotal.setText("Rp. 0");
        populateSpinner();

        btnAddDetail = findViewById(R.id.button_add_barang);
        btnAddDetail.setOnClickListener(v -> {
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            final View addView = layoutInflater.inflate(R.layout.row_detal,null);
            TextView textOut = (TextView) addView.findViewById(R.id.jenis_barang);
            TextView tvHarga = (TextView) addView.findViewById(R.id.harga_barang);

            //set text
            try {
                int pos = sDaftarJenis.getSelectedItemPosition();
                float number = Float.valueOf(edtBerat.getText().toString());
                float harga = list.get(pos).getHarga_jenis() * number;
                String barang = "" + list.get(pos).getNama_jenis() + " " + list.get(pos).getLama_waktu() + " hari x " + edtBerat.getText().toString() + " Kg";

                Locale locale = new Locale("id", "ID");
                NumberFormat sharga = NumberFormat.getCurrencyInstance(locale);
                textOut.setText(barang);
                tvHarga.setText(sharga.format(harga));
                updateTotalBayar(harga,true);
                String j = list.get(pos).getId_jenis();
                DetailLoundry d = new DetailLoundry(number,harga,Integer.parseInt(j));
                listDetail.add(d);
//                ImageView btnRemove = (ImageView) addView.findViewById(R.id.delete);
//                btnRemove.setOnClickListener(v1 -> {
//                    updateTotalBayar(harga,false);
//                    ((LinearLayout) addView.getParent()).removeView(addView);
//
//                });


                llDetail.addView(addView);
            }
            catch (Exception e){
                AlertDialog.Builder builder = new AlertDialog.Builder(AddOrderActivity.this);
                builder.setTitle("Informasi");
                builder.setMessage("Masukan Berat");
                builder.setNeutralButton("Ok", (dialogInterface, i) -> { });
                builder.show();
            }
        });

        btnTambahOrder.setOnClickListener(v -> {
            Log.d("kikk","klikkk");
            Laundry l = sharedPrefManager.getLaundry();
            AddLaundry order = new AddLaundry(l.getId_laundry(),edtNamaPemesan.getText().toString(),edtTlp.getText().toString(),edtAlamat.getText().toString(),total_bayar,"dilaundry");

            Call<Order> createOrde = orderApiInterface.createOrder(order);
            createOrde.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    Order hasil=response.body();

                    for (int i=0;i<listDetail.size();i++){
                        listDetail.get(i).setId_order(hasil.getId_order());
                        DetailLoundry d = listDetail.get(i);
                        Call<DetailLoundry> Create = detailOrderApiInterface.createDetail(d);
                        Create.enqueue(new Callback<DetailLoundry>() {
                            @Override
                            public void onResponse(Call <DetailLoundry> call, Response<DetailLoundry> response) {
                                DetailLoundry dc = response.body();
                            }

                            @Override
                            public void onFailure(Call<DetailLoundry> call, Throwable t) {
                            }
                        });
                    }


                    AlertDialog.Builder builder = new AlertDialog.Builder(AddOrderActivity.this);
                    builder.setTitle("Informasi");
                    builder.setMessage("Berhasil Dipesan");
                    builder.setNeutralButton("Ok", (dialogInterface, i) -> {
                        Intent detail = new Intent(AddOrderActivity.this,DetailOrderActivity.class);
                        detail.putExtra("id_order",hasil.getId_order());
                        detail.putExtra("tanggal_order","-");
                        startActivity(detail);
                        finish();
                    });
                    builder.show();

                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddOrderActivity.this);
                    builder.setTitle("Informasi");
                    builder.setMessage(t.getMessage());
                    builder.show();
                }
            });
        });
    }

    private void updateTotalBayar(float harga,boolean p) {
        if (p){
            total_bayar = (int) (total_bayar+harga);
        }
        else{
            total_bayar = (int) (total_bayar-harga);
        }

        tvTotal.setText("RP. "+total_bayar);
    }

    private void populateSpinner() {
        jenisBarangApiInterface = ApiClient.getClient().create(JenisBarangApiInterface.class);
        Call<List<JenisBarang>> jenisBarangCall = jenisBarangApiInterface.getJenisBarang("2");
        jenisBarangCall.enqueue(new Callback<List<JenisBarang>>() {
            @Override
            public void onResponse(Call<List<JenisBarang>> call, Response<List<JenisBarang>> response) {
                try{
                    List<JenisBarang>  jenisBarangList = response.body();
                    List<String> listSpinner = new ArrayList<>();

                    for (int i=0;i<jenisBarangList.size();i++){
                        String text = ""+jenisBarangList.get(i).getNama_jenis()+" waktu "+jenisBarangList.get(i).getLama_waktu()+" Hari";
                        listSpinner.add(text);
                        list.add(jenisBarangList.get(i));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddOrderActivity.this, android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sDaftarJenis.setAdapter(adapter);

                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddOrderActivity.this);
                    builder.setTitle("Informasi");
                    builder.setMessage("Data barang tidak ditemukan");
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<List<JenisBarang>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddOrderActivity.this);
                builder.setTitle("Informasi");
                builder.setMessage("Koneksi internet terputus");
                builder.show();
            }
        });
    }
}
