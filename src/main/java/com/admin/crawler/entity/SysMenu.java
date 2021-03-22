package com.admin.crawler.entity;

import com.lz.mybatis.plugin.annotations.TableId;
import com.lz.mybatis.plugin.annotations.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
* <p>
* 菜单权限表
* </p>*菜单管理
* @author ququququ
* @since 2021-03-05
*/

@Data
@TableName("sys_menu")
public class SysMenu implements java.io.Serializable {
    //
    @TableId(value = "id")
    private Long id;
    //
    private Integer isDelete;
    //创建时间
    private Date gmtCreate;
    //修改时间
    private Date gmtModified;
    //父菜单ID，一级菜单为0
    private Long parentId;
    //空间id
    private Long spaceId;
    //菜单名称
    private String name;
    //菜单URL
    private String url;
    //类型   0：目录   1：菜单   2：按钮
    private Integer type;
    //菜单图标
    private String icon;
    //排序
    private Integer sort;
    //0:自有接口菜单，1其他菜单
    private Integer specType;

    private List<?> list;

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
     * 父菜单ID，一级菜单为0 
     * @return
     */
    public Long getParentId() {
        return parentId;
    }
    /**
     * 父菜单ID，一级菜单为0 
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 空间id 
     * @return
     */
    public Long getSpaceId() {
        return spaceId;
    }
    /**
     * 空间id 
     * @param spaceId
     */
    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    /**
     * 菜单名称 
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * 菜单名称 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 菜单URL 
     * @return
     */
    public String getUrl() {
        return url;
    }
    /**
     * 菜单URL 
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 类型   0：目录   1：菜单   2：按钮 
     * @return
     */
    public Integer getType() {
        return type;
    }
    /**
     * 类型   0：目录   1：菜单   2：按钮 
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 菜单图标 
     * @return
     */
    public String getIcon() {
        return icon;
    }
    /**
     * 菜单图标 
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
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
     * 0:自有接口菜单，1其他菜单 
     * @return
     */
    public Integer getSpecType() {
        return specType;
    }
    /**
     * 0:自有接口菜单，1其他菜单 
     * @param specType
     */
    public void setSpecType(Integer specType) {
        this.specType = specType;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                ",id=" + id +
                ",isDelete=" + isDelete +
                ",gmtCreate=" + gmtCreate +
                ",gmtModified=" + gmtModified +
                ",parentId=" + parentId +
                ",spaceId=" + spaceId +
                ",name=" + name +
                ",url=" + url +
                ",type=" + type +
                ",icon=" + icon +
                ",sort=" + sort +
                ",specType=" + specType +
                ", list=" + list +
                "}";
    }
}