package project.fmipa.laudryku.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import project.fmipa.laudryku.R;
import project.fmipa.laudryku.model.Order;
import project.fmipa.laudryku.userInterface.DetailOrderActivity;

public class NotifikasiListAdapter extends RecyclerView.Adapter<NotifikasiListAdapter.MyViewHolder>{
    List<Order> notifikasiList;

    public NotifikasiListAdapter(List<Order> notifikasiList){
        this.notifikasiList = notifikasiList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifikasi_list, parent, false);
        return new MyViewHolder(mView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotifikasiListAdapter.MyViewHolder holder, int position) {

        String Notifikasi = "Laundry milik "+notifikasiList.get(position).getNama_pemesan()+" pada tanggal "+notifikasiList.get(position).getTanggal_masuk()+" dengan jumlah sebesar Rp. "+notifikasiList.get(position).getTotal_bayar()+" Selesai Hari ini";
        holder.tvTextNotifikasi.setText(Notifikasi);

        holder.itemView.setOnClickListener(view -> {
            Intent detailOrder =  new Intent(view.getContext(), DetailOrderActivity.class);
            detailOrder.putExtra("id_order",notifikasiList.get(position).getId_order());
            detailOrder.putExtra("tanggal_order",notifikasiList.get(position).getTanggal_masuk());
            detailOrder.putExtra("nama_pemesan",notifikasiList.get(position).getNama_pemesan());
            detailOrder.putExtra("no_tlp",notifikasiList.get(position).getNo_telpon());
            detailOrder.putExtra("status",notifikasiList.get(position).getStatus());

            Locale locale = new Locale("id", "ID");
            NumberFormat sharga = NumberFormat.getCurrencyInstance(locale);
            detailOrder.putExtra("total_bayar",sharga.format(notifikasiList.get(position).getTotal_bayar()));
            view.getContext().startActivity(detailOrder);
        });
    }

    @Override
    public int getItemCount() {
        return notifikasiList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTextNotifikasi;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTextNotifikasi = itemView.findViewById(R.id.textNotifikasi);
        }
    }
}
