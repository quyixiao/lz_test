package com.admin.crawler.entity;

import com.lz.mybatis.plugin.annotations.TableId;
import com.lz.mybatis.plugin.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
* <p>
* 菜单权限表
* </p>*测试组
* @author ququququ
* @since 2021-03-09
*/

@Data
@TableName("lz_test_group")
public class TestGroup implements java.io.Serializable {
    //主键id
    @TableId(value = "id")
    private Long id;
    //是否删除
    private Integer isDelete;
    //生成时间
    private Date gmtCreate;
    //修改时间
    private Date gmtModified;
    //空间id
    private Long spaceId;
    //菜单 id
    private Long menuId;
    //组名称
    private String name;
    //位置
    private Integer position;
    //创建用户 id
    private Long userId;
    //是否可用，0 启用，1 禁用
    private Integer isDisable;
    //开发者真实姓名
    private String realName;
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
     * 菜单 id 
     * @return
     */
    public Long getMenuId() {
        return menuId;
    }
    /**
     * 菜单 id 
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 组名称 
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * 组名称 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 位置 
     * @return
     */
    public Integer getPosition() {
        return position;
    }
    /**
     * 位置 
     * @param position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * 创建用户 id 
     * @return
     */
    public Long getUserId() {
        return userId;
    }
    /**
     * 创建用户 id 
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 是否可用，0 启用，1 禁用 
     * @return
     */
    public Integer getIsDisable() {
        return isDisable;
    }
    /**
     * 是否可用，0 启用，1 禁用 
     * @param isDisable
     */
    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    /**
     * 开发者真实姓名 
     * @return
     */
    public String getRealName() {
        return realName;
    }
    /**
     * 开发者真实姓名 
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "TestGroup{" +
                ",id=" + id +
                ",isDelete=" + isDelete +
                ",gmtCreate=" + gmtCreate +
                ",gmtModified=" + gmtModified +
                ",spaceId=" + spaceId +
                ",menuId=" + menuId +
                ",name=" + name +
                ",position=" + position +
                ",userId=" + userId +
                ",isDisable=" + isDisable +
                ",realName=" + realName +
                "}";
    }
}