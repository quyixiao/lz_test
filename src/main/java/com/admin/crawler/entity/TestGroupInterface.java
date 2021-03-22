package com.admin.crawler.entity;

import com.lz.mybatis.plugin.annotations.TableId;
import com.lz.mybatis.plugin.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
* <p>
* 菜单权限表
* </p>*组接口
* @author ququququ
* @since 2021-03-09
*/

@Data
@TableName("lz_test_group_interface")
public class TestGroupInterface implements java.io.Serializable {
    //主键id
    @TableId(value = "id")
    private Long id;
    //是否删除
    private Integer isDelete;
    //生成时间
    private Date gmtCreate;
    //修改时间
    private Date gmtModified;
    //
    private String name;
    //
    private Long groupId;
    //
    private Long menuId;
    //
    private Long spaceId;
    //
    private Long interfaceId;
    //位置
    private Integer position;
    //0 启用，1 禁用
    private Integer isDisable;
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
     *  
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     *  
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  
     * @return
     */
    public Long getGroupId() {
        return groupId;
    }
    /**
     *  
     * @param groupId
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     *  
     * @return
     */
    public Long getMenuId() {
        return menuId;
    }
    /**
     *  
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     *  
     * @return
     */
    public Long getSpaceId() {
        return spaceId;
    }
    /**
     *  
     * @param spaceId
     */
    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    /**
     *  
     * @return
     */
    public Long getInterfaceId() {
        return interfaceId;
    }
    /**
     *  
     * @param interfaceId
     */
    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
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
     * 0 启用，1 禁用 
     * @return
     */
    public Integer getIsDisable() {
        return isDisable;
    }
    /**
     * 0 启用，1 禁用 
     * @param isDisable
     */
    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    @Override
    public String toString() {
        return "TestGroupInterface{" +
                ",id=" + id +
                ",isDelete=" + isDelete +
                ",gmtCreate=" + gmtCreate +
                ",gmtModified=" + gmtModified +
                ",name=" + name +
                ",groupId=" + groupId +
                ",menuId=" + menuId +
                ",spaceId=" + spaceId +
                ",interfaceId=" + interfaceId +
                ",position=" + position +
                ",isDisable=" + isDisable +
                "}";
    }
}