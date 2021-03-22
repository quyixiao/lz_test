package com.admin.crawler.entity;

import com.lz.mybatis.plugin.annotations.TableId;
import com.lz.mybatis.plugin.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
* <p>
* 菜单权限表
* </p>*规格表
* @author ququququ
* @since 2021-01-06
*/

@Data
@TableName("lz_gitlab_user")
public class GitlabUser implements java.io.Serializable {
    //主键id
    @TableId(value = "id")
    private Long id;
    //是否删除
    private Integer isDelete;
    //生成时间
    private Date gmtCreate;
    //修改时间
    private Date gmtModified;
    //用户名
    private String username;
    //密码
    private String password;
    //是否可用
    private Integer status;
    /**
     * 主键id 
     * @return
     */
    public Long getId() {
        return id;
    }
    /**
     * 主键id 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 是否删除 
     * @return
     */
    public Integer getIsDelete() {
        return isDelete;
    }
    /**
     * 是否删除 
     * @param isDelete
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 生成时间 
     * @return
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }
    /**
     * 生成时间 
     * @param gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改时间 
     * @return
     */
    public Date getGmtModified() {
        return gmtModified;
    }
    /**
     * 修改时间 
     * @param gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 用户名 
     * @return
     */
    public String getUsername() {
        return username;
    }
    /**
     * 用户名 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 密码 
     * @return
     */
    public String getPassword() {
        return password;
    }
    /**
     * 密码 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 是否可用 
     * @return
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * 是否可用 
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GitlabUser{" +
                ",id=" + id +
                ",isDelete=" + isDelete +
                ",gmtCreate=" + gmtCreate +
                ",gmtModified=" + gmtModified +
                ",username=" + username +
                ",password=" + password +
                ",status=" + status +
                "}";
    }
}