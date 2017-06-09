package com.developer.shan.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.shan.R;
import com.developer.shan.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicListFragment extends BaseFragment {

    private static final String TAG = TopicListFragment.class.getName();

    @BindView(R.id.topicRecyclerView)
    RecyclerView mTopicRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    private String mTab;
    private TopicAdapter mAdapter = new TopicAdapter();

    public static TopicListFragment instance(String tab) {
        TopicListFragment fragment = new TopicListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tab", tab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mTab = getArguments().getString("tab");
        }
    }


    @Override
    public int setContentView() {
        return R.layout.fragment_topic_list;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView(View root) {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }


    private class TopicAdapter extends RecyclerView.Adapter {
        private int TYPE_ITEM = 0x001;
        private int TYPE_FOOTER = 0x002;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                final TopicHolder holder = new TopicHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_topic, parent, false));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // TopicDetailActivity.start((BaseActivity) getActivity(), mTopicList.get(holder.getAdapterPosition()).getId());
                    }
                });
                return holder;
            } else {
                return new FooterHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_footer, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof TopicHolder) {
                TopicHolder topicHolder = (TopicHolder) holder;
                Topic topic = mTopicList.get(position);
                ImageLoader.loadUrl(topicHolder.avatar, topic.getAuthor().getAvatar_url());
                if (topic.isTop()) {
                    topicHolder.tag.setText("置顶");
                } else if (topic.isGood()) {
                    topicHolder.tag.setText("精华");
                } else {
                    topicHolder.tagNormal.setText(parseTabName(topic.getTab()));
                }
                topicHolder.tag.setVisibility((topic.isGood() || topic.isTop()) ? View.VISIBLE : View.GONE);
                topicHolder.tagNormal.setVisibility((topic.isGood() || topic.isTop()) ? View.GONE : View.VISIBLE);
                topicHolder.title.setText(topic.getTitle());
                topicHolder.lastReply.setText(String.format(getString(R.string.last_reply), DateUtils.format(topic.getLast_reply_at())));
                topicHolder.replyCount.setText(String.valueOf(topic.getReply_count()));
                topicHolder.visitCount.setText(String.valueOf(topic.getVisit_count()));
            } else if (holder instanceof FooterHolder) {
                ((FooterHolder) holder).footerInfo.setText(mPresenter.hasNextPage() ? "加载中..." : "已加载全部内容!");
            }

        }

        @Override
        public int getItemCount() {
            return mTopicList.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < mTopicList.size()) {
                return TYPE_ITEM;
            } else {
                return TYPE_FOOTER;
            }
        }
    }

    public class TopicHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        RoundedImageView avatar;
        @BindView(R.id.tv_tag)
        TextView tag;
        @BindView(R.id.tv_tag_normal)
        TextView tagNormal;
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_last_reply)
        TextView lastReply;
        @BindView(R.id.tv_reply_count)
        TextView replyCount;
        @BindView(R.id.tv_visit_count)
        TextView visitCount;

        private TopicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.footerInfo)
        TextView footerInfo;

        private FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String parseTabName(String tab) {
        if (TextUtils.isEmpty(tab)) {
            return "未知";
        }
        switch (tab) {
            case "share":
                return "分享";
            case "ask":
                return "问答";
            case "job":
                return "招聘";
            case "good":
                return "精华";
            default:
                return "";
        }
    }

}
