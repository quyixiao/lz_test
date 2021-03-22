package com.admin.crawler.service.impl;

import com.admin.crawler.entity.GitlabUser;
import com.admin.crawler.mapper.GitlabUserMapper;
import com.admin.crawler.service.GitlabUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 规格表 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-01-06
 */

@Service
public class GitlabUserServiceImpl implements GitlabUserService {


    @Autowired
    private GitlabUserMapper gitlabUserMapper;


    @Override
    public GitlabUser selectGitlabUserById(Long id) {
        return gitlabUserMapper.selectGitlabUserById(id);
    }


    @Override
    public Long insertGitlabUser(GitlabUser gitlabUser) {
        return gitlabUserMapper.insertGitlabUser(gitlabUser);
    }


    @Override
    public int updateGitlabUserById(GitlabUser gitlabUser) {
        return gitlabUserMapper.updateGitlabUserById(gitlabUser);
    }


    @Override
    public int updateCoverGitlabUserById(GitlabUser gitlabUser) {
        return gitlabUserMapper.updateCoverGitlabUserById(gitlabUser);
    }


    @Override
    public int deleteGitlabUserById(Long id) {
        return gitlabUserMapper.deleteGitlabUserById(id);
    }

    @Override
    public GitlabUser selectGitlabUserByUsername(String username) {

        return gitlabUserMapper.selectGitlabUserByUsername(username);
    }


}
