package cn.seu.edu.arch.model;

import javax.persistence.*;

public class Collections {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户收藏 json
     */
    private String collections;

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取用户收藏 json
     *
     * @return collections - 用户收藏 json
     */
    public String getCollections() {
        return collections;
    }

    /**
     * 设置用户收藏 json
     *
     * @param collections 用户收藏 json
     */
    public void setCollections(String collections) {
        this.collections = collections;
    }
}