package com.admin.crawler.mapper;

import com.admin.crawler.base.BaseMapper;
import com.admin.crawler.entity.GitlabUser;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 规格表 服务类
* </p>
*
* @author ququququ
* @since 2021-01-06
*/
@Mapper
public interface GitlabUserMapper extends BaseMapper<GitlabUser> {


	GitlabUser selectGitlabUserById(@Param("id") Long id);


	Long insertGitlabUser(GitlabUser gitlabUser);


	int updateGitlabUserById(GitlabUser gitlabUser);


	int updateCoverGitlabUserById(GitlabUser gitlabUser);


	int deleteGitlabUserById(@Param("id") Long id);


    GitlabUser selectGitlabUserByUsername(@Param("username") String username);
}