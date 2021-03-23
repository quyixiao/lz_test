package com.admin.crawler.config;


import com.admin.crawler.exception.LZException;
import com.admin.crawler.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
@Slf4j
public class GitConfig {


    @Bean
    public Git getGit() {
        Git git = null;
        String url = "http://xxxxxxxxxxxxxxx/lz_test_script.git";
        String username = "xxxxxxxxxx";
        String password = "xxxxxxxxx";
        String projectName = getProjectName(url);
        String path = System.getProperty("user.home") + "/test_script/" + projectName;
        File file = new File(path);
        //log.info(" path = " + path);
        if (file.exists()) {
            try {
                git = new Git(new FileRepository(path + "/.git"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                    UsernamePasswordCredentialsProvider(username, password);
            CloneCommand cloneCommand = Git.cloneRepository();
            try {
                git = cloneCommand.setURI(url)
                        .setBranch("master")
                        .setDirectory(new File(path))
                        .setCredentialsProvider(usernamePasswordCredentialsProvider)
                        .call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return git;
    }


    private String getProjectName(String gitUrl) {
        if (StringUtil.isBlank(gitUrl)) {
            throw new LZException("项目地址路径错误");
        }
        int i = gitUrl.lastIndexOf("/");
        int j = gitUrl.indexOf(".git");
        if (i < 1 || j < 1) {
            throw new LZException("项目地址路径错误");
        }
        return gitUrl.substring(i + 1, j);
    }

}
