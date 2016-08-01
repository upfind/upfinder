package cn.upfinder.upfinder.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.upfinder.upfinder.Contract.NearbyContract;
import cn.upfinder.upfinder.R;

public class NearbyFragment extends Fragment implements NearbyContract.View {
    private final String TAG = NearbyFragment.class.getSimpleName();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private NearbyContract.Precenter precenter;


    public NearbyFragment() {
        // Required empty public constructor
    }


    public static NearbyFragment newInstance(String param1, String param2) {
        NearbyFragment fragment = new NearbyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        precenter.start();
    }

    @Override
    public void setPresenter(NearbyContract.Precenter presenter) {
        this.precenter = presenter;
    }
}
