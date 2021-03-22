package com.admin.crawler.service;

import com.admin.crawler.entity.GitlabUser;

/**
 * <p>
 * 规格表 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-01-06
 */
public interface GitlabUserService {


    GitlabUser selectGitlabUserById(Long id);


    Long insertGitlabUser(GitlabUser gitlabUser);


    int updateGitlabUserById(GitlabUser gitlabUser);


    int updateCoverGitlabUserById(GitlabUser gitlabUser);


    int deleteGitlabUserById(Long id);


    GitlabUser selectGitlabUserByUsername(String username);
}