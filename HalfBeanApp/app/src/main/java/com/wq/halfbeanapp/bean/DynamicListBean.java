package com.wq.halfbeanapp.bean;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by vivianWQ on 2018/2/6
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class DynamicListBean {
    private Timestamp createTime;
    private Timestamp systemTime;
    private int commentCount;
    private int praiseCount;
    private int type;
    private UserBroad userBroad;
    private  TopicRepost topicRepost;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Timestamp systemTime) {
        this.systemTime = systemTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserBroad getUserBroad() {
        return userBroad;
    }

    public void setUserBroad(UserBroad userBroad) {
        this.userBroad = userBroad;
    }

    public TopicRepost getTopicRepost() {
        return topicRepost;
    }

    public void setTopicRepost(TopicRepost topicRepost) {
        this.topicRepost = topicRepost;
    }

    public LiveRePost getLiveRePost() {
        return liveRePost;
    }

    public void setLiveRePost(LiveRePost liveRePost) {
        this.liveRePost = liveRePost;
    }

    private LiveRePost liveRePost;
    //1 小组话题转发
    //2 live话题转发
    //3自己的广播
    public  static  class UserBroad{
        private String textContent;
        private List<String> imageUrls;

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }
    }
    public static class TopicRepost{
        private String textContent;
        private String imageUrl;
        private int topicId;

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getTopicId() {
            return topicId;
        }

        public void setTopicId(int topicId) {
            this.topicId = topicId;
        }
    }
    public static class LiveRePost{
        private String textContent;
        private String imageUrl;
        private int liveId;

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getLiveId() {
            return liveId;
        }

        public void setLiveId(int liveId) {
            this.liveId = liveId;
        }
    }
    public static class UserInfo {
        private int userId;
        private String userName;
        private String userIcon;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }
    }
}
