package com.admin.crawler.entity;

import com.lz.mybatis.plugin.annotations.TableId;
import com.lz.mybatis.plugin.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
* <p>
* 菜单权限表
* </p>*空间管理
* @author ququququ
* @since 2021-03-09
*/

@Data
@TableName("sys_space")
public class SysSpace implements java.io.Serializable {
    //
    @TableId(value = "id")
    private Long id;
    //
    private Integer isDelete;
    //创建时间
    private Date gmtCreate;
    //修改时间
    private Date gmtModified;
    //空间名称
    private String name;
    //排序
    private Integer sort;
    //gitLab 地址
    private String gitlabUrl;
    //got:ab的项目ID
    private String gitProjectId;
    //项目名称
    private String gitProjectName;
    //线上域名，调试接口用
    private String onlineUrl;
    //预发地址，调试接口用
    private String previewUrl;
    //测试地址，调试接口用
    private String testUrl;
    /**
     *  
     * @return
     */
    public Long getId() {
        return id;
    }
    /**
     *  
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *  
     * @return
     */
    public Integer getIsDelete() {
        return isDelete;
    }
    /**
     *  
     * @param isDelete
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 创建时间 
     * @return
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }
    /**
     * 创建时间 
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
     * 空间名称 
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * 空间名称 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 排序 
     * @return
     */
    public Integer getSort() {
        return sort;
    }
    /**
     * 排序 
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * gitLab 地址 
     * @return
     */
    public String getGitlabUrl() {
        return gitlabUrl;
    }
    /**
     * gitLab 地址 
     * @param gitlabUrl
     */
    public void setGitlabUrl(String gitlabUrl) {
        this.gitlabUrl = gitlabUrl;
    }

    /**
     * got:ab的项目ID 
     * @return
     */
    public String getGitProjectId() {
        return gitProjectId;
    }
    /**
     * got:ab的项目ID 
     * @param gitProjectId
     */
    public void setGitProjectId(String gitProjectId) {
        this.gitProjectId = gitProjectId;
    }

    /**
     * 项目名称 
     * @return
     */
    public String getGitProjectName() {
        return gitProjectName;
    }
    /**
     * 项目名称 
     * @param gitProjectName
     */
    public void setGitProjectName(String gitProjectName) {
        this.gitProjectName = gitProjectName;
    }

    /**
     * 线上域名，调试接口用 
     * @return
     */
    public String getOnlineUrl() {
        return onlineUrl;
    }
    /**
     * 线上域名，调试接口用 
     * @param onlineUrl
     */
    public void setOnlineUrl(String onlineUrl) {
        this.onlineUrl = onlineUrl;
    }

    /**
     * 预发地址，调试接口用 
     * @return
     */
    public String getPreviewUrl() {
        return previewUrl;
    }
    /**
     * 预发地址，调试接口用 
     * @param previewUrl
     */
    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    /**
     * 测试地址，调试接口用 
     * @return
     */
    public String getTestUrl() {
        return testUrl;
    }
    /**
     * 测试地址，调试接口用 
     * @param testUrl
     */
    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }

    @Override
    public String toString() {
        return "SysSpace{" +
                ",id=" + id +
                ",isDelete=" + isDelete +
                ",gmtCreate=" + gmtCreate +
                ",gmtModified=" + gmtModified +
                ",name=" + name +
                ",sort=" + sort +
                ",gitlabUrl=" + gitlabUrl +
                ",gitProjectId=" + gitProjectId +
                ",gitProjectName=" + gitProjectName +
                ",onlineUrl=" + onlineUrl +
                ",previewUrl=" + previewUrl +
                ",testUrl=" + testUrl +
                "}";
    }
}