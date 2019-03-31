package com.project.babylon.landing.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.babylon.R;
import com.project.babylon.comments.model.CommentModel;
import com.project.babylon.comments.view.AllCommentsActivity;
import com.project.babylon.comments.view.CommentAdapter;
import com.project.babylon.common.AppUtils;
import com.project.babylon.common.BaseActivity;
import com.project.babylon.common.ConnectionDetector;
import com.project.babylon.landing.presenter.LandingImp;
import com.project.babylon.landing.presenter.LandingPresenter;
import com.project.babylon.post.model.PostModel;
import com.project.babylon.post.view.AllPostActivity;
import com.project.babylon.post.view.PostAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LandingActivity extends BaseActivity implements LandingView {
    private final String TAG = LandingActivity.class.getName();

    @BindView(R.id.rv_post)
    RecyclerView rvPost;
    ConnectionDetector connectionDetector;
    @BindView(R.id.ll_no_internet)
    LinearLayout llNoInternet;
    @BindView(R.id.ll_main_landing)
    LinearLayout llMainLanding;
    LandingPresenter landingPresenter;
    @BindView(R.id.tv_comments)
    TextView tvComments;
    @BindView(R.id.sv_landing)
    ScrollView svLanding;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;

    @Override
    protected int getLayout() {
        return R.layout.activity_landing;
    }

    @Override
    protected void init() {
        connectionDetector = new ConnectionDetector(this);

        //post action
        rvPost.setHasFixedSize(true);
        RecyclerView.LayoutManager postLayoutManager = new LinearLayoutManager(this);
        rvPost.setLayoutManager(postLayoutManager);

        //comments action
        rvComments.setHasFixedSize(true);
        rvComments.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager commentLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) commentLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        rvComments.setLayoutManager(commentLayoutManager);
        checkpoint();
    }

    @Override
    public void showPostData(List<PostModel> postModelList) {
        PostAdapter postAdapter = new PostAdapter(postModelList);
        rvPost.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();
        AppUtils.snackbar(svLanding, getString(R.string.data_loaded), this);
    }


    @Override
    public void showCommentsData(List<CommentModel> commentModelList) {
        tvComments.setText(commentModelList.size() + " " + getString(R.string.comments));
        CommentAdapter commentAdapter = new CommentAdapter(commentModelList);
        rvComments.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
        AppUtils.snackbar(svLanding, getString(R.string.data_loaded), this);
    }

    @Override
    public void onSuccess() {
        AppUtils.snackbar(svLanding, getString(R.string.data_loaded), this);
    }

    @Override
    public void onError() {
        AppUtils.snackbar(svLanding, getString(R.string.sth_wrong), this);
    }

    @Override
    public void checkpoint() {
        if (connectionDetector.isConnected()) {
            llNoInternet.setVisibility(View.GONE);
            llMainLanding.setVisibility(View.VISIBLE);

            //loading data
            landingPresenter = new LandingImp(this);
            landingPresenter.loadCommentData();
            landingPresenter.loadPostData();


        } else {
            llMainLanding.setVisibility(View.GONE);
            llNoInternet.setVisibility(View.VISIBLE);
            AppUtils.snackbar(svLanding, getString(R.string.no_internet_connection), this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkpoint();
    }


    @OnClick({R.id.ll_comment_view_all, R.id.ll_post_view_all, R.id.btn_retry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_retry:
                checkpoint();
                break;
            case R.id.ll_comment_view_all:
                Intent commentIntent = new Intent(this, AllCommentsActivity.class);
                startActivity(commentIntent);
                break;
            case R.id.ll_post_view_all:
                Intent intent = new Intent(this, AllPostActivity.class);
                startActivity(intent);
                break;
        }
    }
}
