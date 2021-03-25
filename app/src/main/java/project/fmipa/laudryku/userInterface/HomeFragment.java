package project.fmipa.laudryku.userInterface;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import project.fmipa.laudryku.R;
import project.fmipa.laudryku.adapter.OrderListAdapter;
import project.fmipa.laudryku.model.DataOrderAll;
import project.fmipa.laudryku.model.Laundry;
import project.fmipa.laudryku.model.Order;
import project.fmipa.laudryku.rest.ApiClient;
import project.fmipa.laudryku.rest.OrderApiInterface;
import project.fmipa.laudryku.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView rvOrder;
    private RecyclerView.Adapter mAdapter;
    private OrderApiInterface orderApiInterface;
    TextView nama_laundry,total_orderan,omset_order,total_orderan_today,omset_order_today;
    SharedPrefManager sharedPrefManager;
    private DataOrderAll dataorder;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        rvOrder = rootView.findViewById(R.id.list_recent);
        sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getContext()));


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvOrder.setLayoutManager(mLayoutManager);
        orderApiInterface = ApiClient.getClient().create(OrderApiInterface.class);

        refresh(rootView);

        total_orderan = rootView.findViewById(R.id.total_orderan);
        omset_order = rootView.findViewById(R.id.omset_order);
        total_orderan_today = rootView.findViewById(R.id.total_orderan_today);
        omset_order_today = rootView.findViewById(R.id.omset_order_today);

        //deklarasi isi
        nama_laundry = rootView.findViewById(R.id.nama_laundry);
        Laundry l = sharedPrefManager.getLaundry();
        nama_laundry.setText(l.getNama_laundry());


        return rootView;
    }


    private void refresh(View rootView) {
        Laundry l = sharedPrefManager.getLaundry();
        Call<DataOrderAll> dataOrderAllCall = orderApiInterface.getDataOrderAll(l.getId_laundry());
        dataOrderAllCall.enqueue(new Callback<DataOrderAll>() {
            @Override
            public void onResponse(Call<DataOrderAll> call, Response<DataOrderAll> response) {
                dataorder = response.body();
               // Log.e("Dddd",String.valueOf(response.body().getTotal_bayar()));

                total_orderan.setText(String.valueOf(dataorder.getTotal_order()));
                total_orderan_today.setText(String.valueOf(dataorder.getTotal_order_today()));
                omset_order.setText(String.valueOf(dataorder.getTotal_bayar()));
                omset_order_today.setText(String.valueOf(dataorder.getTotal_bayar_today()));
            }

            @Override
            public void onFailure(Call<DataOrderAll> call, Throwable t) {
                Log.e("agaga","sgsgagga");
            }
        });


        Call<List<Order>> listCall = orderApiInterface.getOrderby(l.getId_laundry());
        listCall.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> dataListOrder = response.body();
                mAdapter = new OrderListAdapter(dataListOrder);
                rvOrder.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }


}
