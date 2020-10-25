package project.akbaralzaini.laudryku.userInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.internal.Util;
import project.akbaralzaini.laudryku.R;
import project.akbaralzaini.laudryku.model.DetailOrder;
import project.akbaralzaini.laudryku.model.Laundry;
import project.akbaralzaini.laudryku.model.Order;
import project.akbaralzaini.laudryku.model.UpdateOrder;
import project.akbaralzaini.laudryku.rest.ApiClient;
import project.akbaralzaini.laudryku.rest.DetailOrderApiInterface;
import project.akbaralzaini.laudryku.rest.OrderApiInterface;
import project.akbaralzaini.laudryku.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderActivity extends Activity {
    DetailOrderApiInterface detailOrderApiInterface;
    TextView tvIdOrder,tvTanggal,tvTotalBayar;
    private int id_order;
    private LinearLayout listdet;
    private Button btnUpdate;
    private OrderApiInterface orderApiInterface;
    private String nama,nomorTlp,harga,nama_laundry;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        detailOrderApiInterface = ApiClient.getClient().create(DetailOrderApiInterface.class);
        tvIdOrder = findViewById(R.id.id_order);
        tvTanggal = findViewById(R.id.tanggal_order);
        tvTotalBayar = findViewById(R.id.total_bayar_detail);
        listdet = findViewById(R.id.deskripsi);
        btnUpdate = findViewById(R.id.update_pesanan);
        orderApiInterface = ApiClient.getClient().create(OrderApiInterface.class);
        sharedPrefManager = new SharedPrefManager(DetailOrderActivity.this);

        Intent i = getIntent();
        id_order = i.getIntExtra("id_order",0);
        nama = i.getStringExtra("nama_pemesan");
        harga = i.getStringExtra("total_bayar");
        nomorTlp = i.getStringExtra("no_tlp");

        String or = i.getStringExtra("nama_pemesan")+" #"+id_order;
        tvIdOrder.setText(or);
        tvTanggal.setText(i.getStringExtra("tanggal_order"));
        tvTotalBayar.setText(i.getStringExtra("total_bayar"));

        refresh();
        if(i.getStringExtra("status").equals("selesai")){
            btnUpdate.setText("Diambil");
            btnUpdate.setOnClickListener(v -> {
                UpdateOrder u = new UpdateOrder();
                u.setStatus("diambil");
                Call<Order> orderCall = orderApiInterface.updateStatus(id_order,u);
                orderCall.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailOrderActivity.this);
                        builder.setTitle("Informasi");
                        builder.setMessage("berhasil di update");
                        builder.setNeutralButton("Ok", (dialogInterface, i) -> {
//                        String formattedNumber = PhoneNumberUtils.formatNumber("6281274131888","ID");
                            try{
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_VIEW);
                                Laundry l = sharedPrefManager.getLaundry();
                                String url = "https://api.whatsapp.com/send?phone=" + nomorTlp + "&text=" + "Hi "+nama+"\nLaundry kamu dengan ID 6774 Sudah Diambil!\nTotal pembayaran "+harga+"\nTerimakasih telah menggunakan Aplikasi Laundryku di "+l.getNama_laundry();
                                sendIntent.setData(Uri.parse(url));
                                startActivity(sendIntent);
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(DetailOrderActivity.this,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailOrderActivity.this);
                        builder.setTitle("Informasi");
                        builder.setMessage("Gagal di update, periksa konksi anda");
                        builder.setNeutralButton("Ok", (dialogInterface, i) -> {
                        });
                        builder.show();

                    }
                });
            });
        }
        else if(i.getStringExtra("status").equals("diambil")){
            btnUpdate.setVisibility(View.GONE);
        }
        else{
            btnUpdate.setOnClickListener(v -> {
                UpdateOrder u = new UpdateOrder();
                u.setStatus("selesai");
                Call<Order> orderCall = orderApiInterface.updateStatus(id_order,u);
                orderCall.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailOrderActivity.this);
                        builder.setTitle("Informasi");
                        builder.setMessage("berhasil di update");
                        builder.setNeutralButton("Ok", (dialogInterface, i) -> {
//                        String formattedNumber = PhoneNumberUtils.formatNumber("6281274131888","ID");
                            try{
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_VIEW);
                                Laundry l = sharedPrefManager.getLaundry();
                                String url = "https://api.whatsapp.com/send?phone=" + nomorTlp + "&text=" + "Hi "+nama+"\nLaundry kamu dengan ID 6774 Sudah selesai!\nTotal pembayaran "+harga+"\nAyo segera ambil laundry kamu di "+l.getNama_laundry()+". \nTerimakasih!";
                                sendIntent.setData(Uri.parse(url));
                                startActivity(sendIntent);
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(DetailOrderActivity.this,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailOrderActivity.this);
                        builder.setTitle("Informasi");
                        builder.setMessage("Gagal di update, periksa konksi anda");
                        builder.setNeutralButton("Ok", (dialogInterface, i) -> {
                        });
                        builder.show();

                    }
                });
            });
        }


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
