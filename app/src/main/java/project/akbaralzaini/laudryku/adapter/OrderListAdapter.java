package project.akbaralzaini.laudryku.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import project.akbaralzaini.laudryku.R;
import project.akbaralzaini.laudryku.model.Order;
import project.akbaralzaini.laudryku.userInterface.DetailOrderActivity;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder>{
    List<Order> orderList;

    public OrderListAdapter(List<Order> orderList){
        this.orderList = orderList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order, parent, false);
        OrderListAdapter.MyViewHolder mViewHolder = new OrderListAdapter.MyViewHolder(mView);
        return mViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.MyViewHolder holder, int position) {

        holder.tvNamaPemesan.setText(orderList.get(position).getNama_pemesan());
        holder.tvTanggalOrder.setText(orderList.get(position).getTanggal_masuk());
        Locale locale = new Locale("id", "ID");
        NumberFormat sharga = NumberFormat.getCurrencyInstance(locale);

        holder.tvTotalOrder.setText(sharga.format(orderList.get(position).getTotal_bayar()));
        switch (orderList.get(position).getStatus()){
            case "dilaundry":
                holder.ivStatusOrder.setImageResource(R.drawable.waiting);
                break;
            case "selesai":
                holder.ivStatusOrder.setImageResource(R.drawable.delay);
                break;
            default:
                holder.ivStatusOrder.setImageResource(R.drawable.done);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailOrder =  new Intent(view.getContext(), DetailOrderActivity.class);
                detailOrder.putExtra("id_order",orderList.get(position).getId_order());
                detailOrder.putExtra("tanggal_order",orderList.get(position).getTanggal_masuk());
                detailOrder.putExtra("nama_pemesan",orderList.get(position).getNama_pemesan());

                Locale locale = new Locale("id", "ID");
                NumberFormat sharga = NumberFormat.getCurrencyInstance(locale);
                detailOrder.putExtra("total_bayar",sharga.format(orderList.get(position).getTotal_bayar()));
                view.getContext().startActivity(detailOrder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaPemesan,tvTanggalOrder,tvTotalOrder;
        ImageView ivIconOrder,ivStatusOrder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaPemesan = itemView.findViewById(R.id.nama_pemesan);
            tvTanggalOrder = itemView.findViewById(R.id.tanggal_order);
            tvTotalOrder = itemView.findViewById(R.id.total_order);
            ivIconOrder = itemView.findViewById(R.id.foto_order);
            ivStatusOrder = itemView.findViewById(R.id.status_order);
        }
    }
}
