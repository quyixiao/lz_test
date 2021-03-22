package com.test;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.eclipse.jgit.lib.Constants.R_HEADS;


public class Test2 {

    public static MergeStrategy[] strategiesUnderTest = new MergeStrategy[]{
            MergeStrategy.RECURSIVE, MergeStrategy.RESOLVE};

    public static void main(String[] args) throws Exception {


        String username = "ququququ";
        String password = "password123456";
        String url = "http://xxx.xxx.xxx/lz_test_script.git";
        Git git = null;
        String projectName = "lz_test_script";
        String path = System.getProperty("user.home") + "/test_script/" + projectName + "/lz_test";
        File file = new File(path);
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

        Repository repository = git.getRepository();
        git.checkout().setName("side").call();
        writeTrashFile(repository, "a/content", "111\n222\n333\n4444\n5555\n6666666\n77777\n8888");
        RevCommit sideCommit = git.commit().setAll(true).setMessage("modified 1 on master again").call();
        MergeStrategy strategy = MergeStrategy.RECURSIVE;
        MergeResult result = git.merge().setStrategy(strategy).include(repository.exactRef(R_HEADS + MASTER)).call();
        System.out.println("mergeStatus :" + result.getMergeStatus());

        if("Conflicting".equals(result.getMergeStatus().toString())){ //如果文件冲突
            //文件冲突
            String a = read(repository,"a/content");
            System.out.println(a);
        } else{
            result.getNewHead();
            git.checkout().setName("master").call();
            MergeResult resultxx = git.merge().setStrategy(strategy).include(sideCommit).call();
            MergeResult.MergeStatus  mergeStatus= resultxx.getMergeStatus();
            boolean statusSuccessful = mergeStatus.isSuccessful();
            System.out.println(" statusSuccessful : " + statusSuccessful);
            Iterable<PushResult> list = git.push().setRemote("origin").add("master").setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
            if(list != null) {
                for(PushResult pr : list) {
                    System.out.println(pr.getMessages());
                }
            }
            try {
                String a = read(repository,"a/content");
                System.out.println(a);
            } catch (JGitInternalException e) {
                e.printStackTrace();
            }
        }
    }

    public static File writeTrashFile(final Repository db,
                                      final String name, final String data) throws IOException {
        File path = new File(db.getWorkTree(), name);
        write(path, data);
        return path;
    }

    public static void write(File f, String body)
            throws IOException {
        FileUtils.mkdirs(f.getParentFile(), true);
        try (Writer w = new OutputStreamWriter(new FileOutputStream(f),
                UTF_8)) {
            w.write(body);
        }
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


}
