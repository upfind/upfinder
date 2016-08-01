package cn.upfinder.upfinder.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.upfinder.upfinder.Adapter.UsersAdapter;
import cn.upfinder.upfinder.Contract.SearchUserContract;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.R;


public class SearchUserFragment extends Fragment implements SearchUserContract.View {
    private final String TAG = SearchUserFragment.class.getSimpleName();

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivSearchCommit)
    ImageView ivSearchCommit;
    @BindView(R.id.rvUsers)
    RecyclerView rvUsers;
    @BindView(R.id.pbProgress)
    ContentLoadingProgressBar pbProgress;

    private LinearLayoutManager layoutManager;
    private UsersAdapter adapter;

    private SearchUserContract.Presenter presenter;

    public SearchUserFragment() {
    }

    public static SearchUserFragment newInstance() {
        SearchUserFragment fragment = new SearchUserFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        ButterKnife.bind(this, view);
        presenter.start();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(SearchUserContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @OnClick({R.id.ivBack, R.id.ivSearchCommit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                getActivity().finish();
                break;
            case R.id.ivSearchCommit:
                presenter.searchUser(etSearch.getText().toString());
                break;
        }
    }

    @Override
    public void initView() {
        Log.d(TAG, "initView: ");
        layoutManager = new LinearLayoutManager(getContext());
        rvUsers.setLayoutManager(layoutManager);
        adapter = new UsersAdapter();
        rvUsers.setAdapter(adapter);
    }

    @Override
    public void showSearchResult(List<User> userList) {
        adapter.setUserList(userList);
    }

    @Override
    public void showSearchProgress() {

    }

    @Override
    public void showErrMsg(String errMsg) {

    }
}
