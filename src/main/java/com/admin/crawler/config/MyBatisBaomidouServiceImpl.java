
package com.admin.crawler.config;

import com.lz.mybatis.plugin.service.MyBatisBaomidouService;
import com.lz.mybatis.plugin.utils.t.PluginTuple;
import org.apache.ibatis.session.Configuration;

import java.util.List;

public class MyBatisBaomidouServiceImpl implements MyBatisBaomidouService {

    @Override
    public void init(List<PluginTuple> pluginTuples, Configuration configuration, Class type) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
