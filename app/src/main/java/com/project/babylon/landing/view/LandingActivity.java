package com.project.babylon.landing.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.babylon.R;
import com.project.babylon.comments.model.CommentModel;
import com.project.babylon.comments.view.CommentAdapter;
import com.project.babylon.comments.view.AllCommentActivity;
import com.project.babylon.common.AppConstant;
import com.project.babylon.common.AppUtils;
import com.project.babylon.common.BaseActivity;
import com.project.babylon.common.ConnectionDetector;
import com.project.babylon.landing.presenter.LandingImp;
import com.project.babylon.landing.presenter.LandingPresenter;
import com.project.babylon.post.model.PostModel;
import com.project.babylon.post.view.AllPostActivity;
import com.project.babylon.post.view.PostAdapter;
import com.project.babylon.post.view.PostView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LandingActivity extends BaseActivity implements LandingView, PostView {
    private final String TAG = LandingActivity.class.getName();

    @BindView(R.id.rv_post)
    RecyclerView rvPost;
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

    @Override
    protected int getLayout() {
        return R.layout.activity_landing;
    }

    @Override
    protected void init() {
        connectionDetector = new ConnectionDetector(this);

        if (connectionDetector.isConnected()) {
            llNoInternet.setVisibility(View.GONE);

            //post action
            rvPost.setHasFixedSize(true);
            RecyclerView.LayoutManager postLayoutManager = new LinearLayoutManager(this);
            rvPost.setLayoutManager(postLayoutManager);


            //comments action
            rvComments.setHasFixedSize(true);
            RecyclerView.LayoutManager commentLayoutManager = new LinearLayoutManager(this);
            ((LinearLayoutManager) commentLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
            rvComments.setLayoutManager(commentLayoutManager);

            //loading data
            landingPresenter = new LandingImp(this);
            landingPresenter.loadCommentData();
            landingPresenter.loadPostData();


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
                            LandingActivity.this);
                    pullToRefresh.setRefreshing(false);
                } else {
                    landingPresenter.loadCommentData();
                    landingPresenter.loadPostData();
                    llNoInternet.setVisibility(View.GONE);
                    pullToRefresh.setRefreshing(false);
                    AppUtils.snackbar(pullToRefresh, getString(R.string.data_loaded),
                            LandingActivity.this);
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
    public void showCommentsData(List<CommentModel> commentModelList) {
        tvComments.setText(commentModelList.size() + " " + getString(R.string.comments));
        CommentAdapter commentAdapter = new CommentAdapter(commentModelList);
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
    protected void onResume() {
        super.onResume();
        landingPresenter.loadCommentData();
        landingPresenter.loadPostData();
    }


    @OnClick({R.id.ll_comment_view_all, R.id.ll_post_view_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_comment_view_all:
                Intent commentIntent = new Intent(this, AllCommentActivity.class);
                startActivity(commentIntent);
                break;
            case R.id.ll_post_view_all:
                Intent intent = new Intent(this, AllPostActivity.class);
                startActivity(intent);
                break;
        }
    }
}
