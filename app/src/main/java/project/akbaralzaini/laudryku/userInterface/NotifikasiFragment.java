package project.akbaralzaini.laudryku.userInterface;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import project.akbaralzaini.laudryku.R;
import project.akbaralzaini.laudryku.adapter.NotifikasiListAdapter;
import project.akbaralzaini.laudryku.model.Laundry;
import project.akbaralzaini.laudryku.model.Order;
import project.akbaralzaini.laudryku.rest.ApiClient;
import project.akbaralzaini.laudryku.rest.NotifikasiInterface;
import project.akbaralzaini.laudryku.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifikasiFragment extends Fragment {

    private NotifikasiInterface notifikasiInterface;
    private RecyclerView rvNotifikasi;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private SharedPrefManager sharedPrefManager;

    public NotifikasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notifikasi, container, false);
        rvNotifikasi = rootView.findViewById(R.id.list_notifikasi);
        sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getContext()));

        mLayoutManager = new LinearLayoutManager(getContext());
        rvNotifikasi.setLayoutManager(mLayoutManager);
        notifikasiInterface = ApiClient.getClient().create(NotifikasiInterface.class);

        refresh();

        return rootView;
    }

    private void refresh() {
        Laundry l = sharedPrefManager.getLaundry();

        Calendar calendar = Calendar.getInstance();
        Locale locale = new Locale("id", "ID");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",locale);
        String date = formatter.format(calendar.getTime());

        Call<List<Order>> callNotifikasi = notifikasiInterface.getNotifikasi(l.getId_laundry(),date);
        callNotifikasi.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                try{
                    List<Order> notifikasi = response.body();
                    mAdapter = new NotifikasiListAdapter(notifikasi);
                    rvNotifikasi.setAdapter(mAdapter);
                }
                catch (Throwable throwable){
                    Log.i("Error","errorr cukk");
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

}
