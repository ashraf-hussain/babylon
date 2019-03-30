package com.project.babylon.postView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.project.babylon.R;
import com.project.babylon.common.AppConstant;
import com.project.babylon.model.PostModel;
import com.project.babylon.postDetail.PostDetailActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private static final String TAG = PostAdapter.class.getName();
    private Context context;
    private List<PostModel> postModelList;
    private int lastPosition = -1;


    PostAdapter(List<PostModel> musicModelList) {
        this.postModelList = musicModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_post, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        setAnimation(holder.itemView,position);
        final PostModel postModel = postModelList.get(position);
        holder.tvTitle.setText(" : "+postModel.getTitle());
        holder.cvPostAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PostDetailActivity.class);
                i.putExtra(AppConstant.POST_DETAIL, postModel);
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return postModelList == null ? 0 : postModelList.size();

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.cv_post_adapter)
        CardView cvPostAdapter;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
