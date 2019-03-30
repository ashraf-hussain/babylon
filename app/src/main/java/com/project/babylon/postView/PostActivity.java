package com.project.babylon.postView;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.project.babylon.R;
import com.project.babylon.common.AppUtils;
import com.project.babylon.common.BaseActivity;
import com.project.babylon.common.ConnectionDetector;
import com.project.babylon.model.PostModel;
import com.project.babylon.postPresenter.PostImp;
import com.project.babylon.postPresenter.PostPresenter;

import java.util.List;

import butterknife.BindView;

public class PostActivity extends BaseActivity implements PostView {
    private final String TAG = PostActivity.class.getName();

    @BindView(R.id.rv_post)
    RecyclerView rvPost;
    @BindView(R.id.pullToRefresh_main_post)
    SwipeRefreshLayout pullToRefresh;
    ConnectionDetector connectionDetector;
    @BindView(R.id.ll_no_internet)
    LinearLayout llNoInternet;
    PostPresenter postPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_post;
    }

    @Override
    protected void init() {
        connectionDetector = new ConnectionDetector(this);

        if (connectionDetector.isConnected()) {
            rvPost.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvPost.setLayoutManager(layoutManager);

            postPresenter = new PostImp(this);
            postPresenter.loadPostData();
            llNoInternet.setVisibility(View.GONE);
        } else {
            llNoInternet.setVisibility(View.VISIBLE);
            AppUtils.snackbar(pullToRefresh, getString(R.string.no_internet_connection), this);
        }


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!connectionDetector.isConnected()) {
                    llNoInternet.setVisibility(View.VISIBLE);
                    AppUtils.snackbar(pullToRefresh, getString(R.string.no_internet_connection),
                            PostActivity.this);
                    pullToRefresh.setRefreshing(false);
                } else {
                    postPresenter.loadPostData();
                    llNoInternet.setVisibility(View.GONE);
                    pullToRefresh.setRefreshing(false);
                    AppUtils.snackbar(pullToRefresh, getString(R.string.data_loaded),
                            PostActivity.this);
                }

            }
        });

    }

    @Override
    public void showPostData(List<PostModel> postModelList) {
        PostAdapter postAdapter = new PostAdapter(postModelList);
        rvPost.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();
        AppUtils.snackbar(pullToRefresh, getString(R.string.data_loaded), this);
    }

    @Override
    public void onSuccess() {
        AppUtils.snackbar(pullToRefresh, getString(R.string.data_loaded), this);
    }

    @Override
    public void onError() {
        AppUtils.snackbar(pullToRefresh, getString(R.string.sth_wrong), this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        postPresenter.loadPostData();
    }
}
