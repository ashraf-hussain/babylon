package com.project.babylon.post.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.babylon.R;
import com.project.babylon.comments.model.CommentModel;
import com.project.babylon.common.AppUtils;
import com.project.babylon.common.BaseActivity;
import com.project.babylon.common.ConnectionDetector;
import com.project.babylon.landing.presenter.LandingImp;
import com.project.babylon.landing.presenter.LandingPresenter;
import com.project.babylon.landing.view.LandingView;
import com.project.babylon.post.model.PostModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AllPostActivity extends BaseActivity implements LandingView {

    private final String TAG = AllPostActivity.class.getName();

    @BindView(R.id.rv_post)
    RecyclerView rvPost;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.pullToRefresh_main_post)
    SwipeRefreshLayout pullToRefresh;
    ConnectionDetector connectionDetector;
    @BindView(R.id.ll_no_internet)
    LinearLayout llNoInternet;
    LandingPresenter landingPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_post_all;
    }

    @Override
    protected void init() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tbTitle.setText(getString(R.string.post));
        connectionDetector = new ConnectionDetector(this);

        rvPost.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPost.setLayoutManager(layoutManager);

        checkpoint();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!connectionDetector.isConnected()) {
                    llNoInternet.setVisibility(View.VISIBLE);
                    AppUtils.snackbar(pullToRefresh, getString(R.string.no_internet_connection),
                            AllPostActivity.this);
                    pullToRefresh.setRefreshing(false);
                } else {
                    landingPresenter.loadPostData();
                    llNoInternet.setVisibility(View.GONE);
                    pullToRefresh.setRefreshing(false);
                    AppUtils.snackbar(pullToRefresh, getString(R.string.data_loaded),
                            AllPostActivity.this);
                }
            }
        });

    }

    @Override
    public void showCommentsData(List<CommentModel> commentModelList) {

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
    public void checkpoint() {
        if (connectionDetector.isConnected()) {
            landingPresenter = new LandingImp(this);
            landingPresenter.loadPostData();
            llNoInternet.setVisibility(View.GONE);
        } else {
            llNoInternet.setVisibility(View.VISIBLE);
            AppUtils.snackbar(pullToRefresh, getString(R.string.no_internet_connection), this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkpoint();
    }

    @OnClick({R.id.toolbar, R.id.btn_retry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                onBackPressed();
                break;
            case R.id.btn_retry:
                checkpoint();
                break;
        }
    }
}
