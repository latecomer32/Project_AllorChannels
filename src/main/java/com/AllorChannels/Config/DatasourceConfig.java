package com.AllorChannels.Config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {
    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
          .driverClassName("com.mysql.cj.jdbc.Driver")
          .url("jdbc:mysql://eanl4i1omny740jw.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/kgt2iokhmbxc1789?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul")
          .username("xibx75hj5pwcpzum")
          .password("ybzkbdptntvl10r8")
          .build();	
    }
}
