package com.admin.crawler.service;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;

public interface GitOperationService {

    void pull(String... branchs) throws Exception;

    boolean checkout(String branch) throws Exception;

    boolean branch(String branch) throws Exception;

    void write(String path, String fileName, String code) throws Exception;

    void add(String... paths) throws Exception;

    RevCommit commit(String message) throws Exception;

    Iterable<PushResult>  push(String... branch) throws Exception;

    String merge(String branch) throws Exception;

    String read(String directory, String gitFileName) throws Exception;


    void reset(String branch) throws Exception;

    void fetch() throws Exception;

    String getBranch() throws Exception;
}
