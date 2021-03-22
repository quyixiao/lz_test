package com.admin.crawler.config;

import com.lz.mybatis.plugin.config.CustomerMapperBuilder;
import com.lz.mybatis.plugin.service.MyBatisBaomidouService;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.mapper.MapperFactoryBean;

public class CustomerMapperFactoryBean extends MapperFactoryBean {



    protected void initDao() throws Exception {
        SqlSession sqlSession = getSqlSession();
        Configuration configuration = sqlSession.getConfiguration();
        // myBatisBaomidouService 主要是解析版本兼容问题，交给引入包的项目来解决不同版本兼容性问题
        MyBatisBaomidouService myBatisBaomidouService = new MyBatisBaomidouServiceImpl();
        CustomerMapperBuilder customerMapperBuilder = new CustomerMapperBuilder(configuration,
                this.getObjectType(), myBatisBaomidouService);
        // 调用 parse 方法，解析 *Mapper.java中的方法，动态生成sql并保存到org.apache.ibatis.session.Configuration中
        customerMapperBuilder.parse();
    }

}
