package com.admin.crawler.entity;

import com.lz.mybatis.plugin.annotations.TableId;
import com.lz.mybatis.plugin.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
* <p>
* 菜单权限表
* </p>*接口修改记录表
* @author ququququ
* @since 2021-03-09
*/

@Data
@TableName("lz_test_interface_modified")
public class TestInterfaceModified implements java.io.Serializable {
    //主键id
    @TableId(value = "id")
    private Long id;
    //是否删除
    private Integer isDelete;
    //生成时间
    private Date gmtCreate;
    //修改时间
    private Date gmtModified;
    //修改用户 id
    private Long userId;
    //修改用户名称
    private String realName;
    //修改后的 code
    private String code;
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
     * 修改用户 id 
     * @return
     */
    public Long getUserId() {
        return userId;
    }
    /**
     * 修改用户 id 
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 修改用户名称 
     * @return
     */
    public String getRealName() {
        return realName;
    }
    /**
     * 修改用户名称 
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 修改后的 code 
     * @return
     */
    public String getCode() {
        return code;
    }
    /**
     * 修改后的 code 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TestInterfaceModified{" +
                ",id=" + id +
                ",isDelete=" + isDelete +
                ",gmtCreate=" + gmtCreate +
                ",gmtModified=" + gmtModified +
                ",userId=" + userId +
                ",realName=" + realName +
                ",code=" + code +
                "}";
    }
}