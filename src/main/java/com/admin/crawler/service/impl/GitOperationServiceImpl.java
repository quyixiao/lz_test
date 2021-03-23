package com.admin.crawler.service.impl;

import com.admin.crawler.service.GitOperationService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.R_HEADS;

@Slf4j
@Service
public class GitOperationServiceImpl implements GitOperationService {

    private static final UsernamePasswordCredentialsProvider credentialsProvider
            = new UsernamePasswordCredentialsProvider("quyixiao", "quyixiao321");

    @Autowired
    private Git git;

    public static final String projectName = "lz_test_script";

    public GitOperationServiceImpl(Git git) {
        this.git = git;
    }

    @Override
    public void pull(String... branchs) throws Exception {
        String branch = getBranch();
        if (branchs != null && branchs.length > 0) {
            branch = branchs[0];
        }
        if (remoteBranchExist(git, branch)) {                   //如果远程分支存在该分支，再 pull
            try {
                PullCommand pull = git.pull().setRemoteBranchName(branch);
                pull.setCredentialsProvider(credentialsProvider).call();
            } catch (GitAPIException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean checkout(String branch) throws Exception {
        if (!localBranchExist(git, branch)) {        //分支不存在创建
            git.branchCreate().setName(branch).call();
            git.checkout().setName(branch).call();                 //创建新分支，并切换到该分支
        } else {
            Ref ref = null;
            try {
                ref = git.checkout().setName(branch).call();
            } catch (CheckoutConflictException e) {
                String currBranch = getBranch();
                log.info("当前分支：" + currBranch);
                log.error("文件冲突异常,branch, " + branch ,e);

                throw  new Exception();
            }
            log.info("----------" + ref.getName());
        }
        return true;
    }

    @Override
    public boolean branch(String branch) throws Exception {           //如果分支存在，则不创建该创建，如果分支不存在，则创建该分支
        //分支不存在创建
        if (!allBranchExist(git, branch)) {
            git.branchCreate().setName(branch).call();
            return true;
        }
        return false;
    }

    @Override
    public void write(String directory, String fileName, String code) throws Exception {
        String filePath = System.getProperty("user.home") + "/test_script/" + projectName;
        File path = new File(filePath + "/" + directory);
        if (!path.exists()) {
            path.mkdirs();
        }
        FileOutputStream fos = null;
        Writer out = null;
        try {
            fos = new FileOutputStream(filePath + "/" + directory + "/" + fileName);
            out = new OutputStreamWriter(fos, "UTF-8");
            out.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void add(String... paths) throws Exception {
        String path = ".";
        if (paths != null && paths.length > 0) {
            path = paths[0];
        }
        git.add().addFilepattern(path).call();
    }


    @Override
    public RevCommit commit(String message) throws Exception {
        return git.commit().setAll(true).setMessage(message).call();
    }


    @Override
    public Iterable<PushResult> push(String... branchs) throws Exception {
        String branch = getBranch();
        if (branchs != null && branchs.length > 0) {
            branch = branchs[0];
        }
        Iterable<PushResult> list = git.push().setRemote("origin").add(branch)
                .setCredentialsProvider(credentialsProvider).call();
        if (list != null) {
            for (PushResult pr : list) {
                log.info(pr.getMessages());
            }
        }
        return list;
    }

    @Override
    public String merge(String branch) throws Exception {
        Repository repository = git.getRepository();
        MergeStrategy strategy = MergeStrategy.RECURSIVE;
        MergeResult result = git.merge().setStrategy(strategy).include(repository.exactRef(R_HEADS + branch)).call();
        log.info("mergeStatus :" + result.getMergeStatus().toString());
        return result.getMergeStatus().toString();
    }

    @Override
    public String read(String directory, String gitFileName) throws Exception {
        Repository repository = git.getRepository();
        return read(repository, directory + "/" + gitFileName);
    }

    @Override
    public void reset(String branch) throws Exception {
        ResetCommand resetCmd = git.reset().setMode(ResetCommand.ResetType.HARD);
        resetCmd.setRef("origin/" + branch).call();

    }

    @Override
    public void fetch() throws Exception {
        git.fetch().setRemote("origin").setCredentialsProvider(credentialsProvider).call();
    }

    @Override
    public String getBranch() throws Exception {
        return git.getRepository().getBranch();
    }


    public static String read(Repository db, String name)
            throws IOException {
        File file = new File(db.getWorkTree(), name);
        return read(file);
    }


    public static String read(File file) throws IOException {
        final byte[] body = IO.readFully(file);
        return new String(body, 0, body.length, UTF_8);
    }


    public static boolean localBranchExist(Git git, String branch) throws GitAPIException {
        // 获取所有的本地分支和本地分支
        List<Ref> refs = git.branchList().call();
        return branchExits(refs, branch);
    }

    public static boolean remoteBranchExist(Git git, String branch) throws GitAPIException {
        // 获取所有的远程分支和本地分支
        List<Ref> refs = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
        return branchExits(refs, branch);
    }

    public static boolean allBranchExist(Git git, String branch) throws GitAPIException {
        // 获取所有的远程分支和本地分支
        List<Ref> refs = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        return branchExits(refs, branch);
    }


    public static boolean branchExits(List<Ref> refs, String branch) {
        for (Ref ref : refs) {
            String branchName = ref.getName();
            int index = branchName.lastIndexOf("/");
            if (index > 0) {
                branchName = branchName.substring(index + 1);
                if (branchName.equals(branch)) {
                    return true;
                }
            }
        }
        return false;
    }

}
