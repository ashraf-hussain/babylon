package com.project.babylon.comments.view;


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

public class AllCommentsActivity extends BaseActivity implements LandingView {

    private final String TAG = AllCommentsActivity.class.getName();

    @BindView(R.id.pullToRefresh_main_post)
    SwipeRefreshLayout pullToRefresh;
    ConnectionDetector connectionDetector;
    @BindView(R.id.ll_no_internet)
    LinearLayout llNoInternet;
    LandingPresenter landingPresenter;
    @BindView(R.id.tv_comments)
    TextView tvComments;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;
    @BindView(R.id.tb_title)
    TextView tbTitle;

    @Override
    protected int getLayout() {
        return R.layout.activity_all_comments;
    }

    @Override
    protected void init() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tbTitle.setText(getString(R.string.comments));
        connectionDetector = new ConnectionDetector(this);

        //comments action
        rvComments.setHasFixedSize(true);
        RecyclerView.LayoutManager commentLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(commentLayoutManager);
        checkpoint();
        //pull to refresh action
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!connectionDetector.isConnected()) {
                    llNoInternet.setVisibility(View.VISIBLE);
                    AppUtils.snackbar(pullToRefresh, getString(R.string.no_internet_connection),
                            AllCommentsActivity.this);
                    pullToRefresh.setRefreshing(false);
                } else {
                    landingPresenter.loadCommentData();
                    llNoInternet.setVisibility(View.GONE);
                    pullToRefresh.setRefreshing(false);
                    AppUtils.snackbar(pullToRefresh, getString(R.string.data_loaded),
                            AllCommentsActivity.this);
                }
            }
        });
    }

    @Override
    public void showPostData(List<PostModel> postModelList) {

    }

    @Override
    public void showCommentsData(List<CommentModel> commentModelList) {
        tvComments.setText(commentModelList.size() + " " + getString(R.string.comments));
        AllCommentsAdapter commentAdapter = new AllCommentsAdapter(commentModelList);
        rvComments.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
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
            llNoInternet.setVisibility(View.GONE);

            //loading data
            landingPresenter = new LandingImp(this);
            landingPresenter.loadCommentData();

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
