package com.admin.crawler.entity;

import com.lz.mybatis.plugin.annotations.TableId;
import com.lz.mybatis.plugin.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
* <p>
* 菜单权限表
* </p>*测试报告
* @author ququququ
* @since 2021-03-16
*/

@Data
@TableName("lz_test_report")
public class TestReport implements java.io.Serializable {
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
    //组 id
    private Long groupId;
    //菜单 id
    private Long menuId;
    //接口 id
    private Long interfaceId;
    //执行结果
    private String exeResult;
    //唯一标识
    private String uniqueFlag;
    //用户 id
    private Long userId;
    //真实姓名
    private String realName;
    //状态，0 未取，1 己取  2 终止
    private Integer status;
    //组接口 id
    private Long groupInterfaceId;
    //0 执行成功，1 执行失败
    private Integer exeResultFlag;
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
     * 组 id 
     * @return
     */
    public Long getGroupId() {
        return groupId;
    }
    /**
     * 组 id 
     * @param groupId
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
     * 接口 id 
     * @return
     */
    public Long getInterfaceId() {
        return interfaceId;
    }
    /**
     * 接口 id 
     * @param interfaceId
     */
    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    /**
     * 执行结果 
     * @return
     */
    public String getExeResult() {
        return exeResult;
    }
    /**
     * 执行结果 
     * @param exeResult
     */
    public void setExeResult(String exeResult) {
        this.exeResult = exeResult;
    }

    /**
     * 唯一标识 
     * @return
     */
    public String getUniqueFlag() {
        return uniqueFlag;
    }
    /**
     * 唯一标识 
     * @param uniqueFlag
     */
    public void setUniqueFlag(String uniqueFlag) {
        this.uniqueFlag = uniqueFlag;
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
     * 真实姓名 
     * @return
     */
    public String getRealName() {
        return realName;
    }
    /**
     * 真实姓名 
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 状态，0 未取，1 己取  2 终止 
     * @return
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * 状态，0 未取，1 己取  2 终止 
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 组接口 id 
     * @return
     */
    public Long getGroupInterfaceId() {
        return groupInterfaceId;
    }
    /**
     * 组接口 id 
     * @param groupInterfaceId
     */
    public void setGroupInterfaceId(Long groupInterfaceId) {
        this.groupInterfaceId = groupInterfaceId;
    }

    /**
     * 0 执行成功，1 执行失败 
     * @return
     */
    public Integer getExeResultFlag() {
        return exeResultFlag;
    }
    /**
     * 0 执行成功，1 执行失败 
     * @param exeResultFlag
     */
    public void setExeResultFlag(Integer exeResultFlag) {
        this.exeResultFlag = exeResultFlag;
    }

    @Override
    public String toString() {
        return "TestReport{" +
                ",id=" + id +
                ",isDelete=" + isDelete +
                ",gmtCreate=" + gmtCreate +
                ",gmtModified=" + gmtModified +
                ",spaceId=" + spaceId +
                ",groupId=" + groupId +
                ",menuId=" + menuId +
                ",interfaceId=" + interfaceId +
                ",exeResult=" + exeResult +
                ",uniqueFlag=" + uniqueFlag +
                ",userId=" + userId +
                ",realName=" + realName +
                ",status=" + status +
                ",groupInterfaceId=" + groupInterfaceId +
                ",exeResultFlag=" + exeResultFlag +
                "}";
    }
}