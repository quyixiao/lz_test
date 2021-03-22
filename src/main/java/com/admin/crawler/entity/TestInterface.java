package com.admin.crawler.entity;

import com.lz.mybatis.plugin.annotations.TableId;
import com.lz.mybatis.plugin.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
* <p>
* 菜单权限表
* </p>*接口
* @author ququququ
* @since 2021-03-09
*/

@Data
@TableName("lz_test_interface")
public class TestInterface implements java.io.Serializable {
    //主键id
    @TableId(value = "id")
    private Long id;
    //是否删除
    private Integer isDelete;
    //生成时间
    private Date gmtCreate;
    //修改时间
    private Date gmtModified;
    //空间 id
    private Long spaceId;
    //菜单 id
    private Long menuId;
    //接口名称
    private String name;
    //代码
    private String code;
    //位置
    private Integer position;
    //用户 id
    private Long userId;
    //用户名称
    private String realName;
    //文件名称，用户其他接口 import
    private String fileName;
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
     * 空间 id 
     * @return
     */
    public Long getSpaceId() {
        return spaceId;
    }
    /**
     * 空间 id 
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
     * 接口名称 
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * 接口名称 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 代码 
     * @return
     */
    public String getCode() {
        return code;
    }
    /**
     * 代码 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
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
     * 用户 id 
     * @return
     */
    public Long getUserId() {
        return userId;
    }
    /**
     * 用户 id 
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 用户名称 
     * @return
     */
    public String getRealName() {
        return realName;
    }
    /**
     * 用户名称 
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 文件名称，用户其他接口 import 
     * @return
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * 文件名称，用户其他接口 import 
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "TestInterface{" +
                ",id=" + id +
                ",isDelete=" + isDelete +
                ",gmtCreate=" + gmtCreate +
                ",gmtModified=" + gmtModified +
                ",spaceId=" + spaceId +
                ",menuId=" + menuId +
                ",name=" + name +
                ",code=" + code +
                ",position=" + position +
                ",userId=" + userId +
                ",realName=" + realName +
                ",fileName=" + fileName +
                "}";
    }
}