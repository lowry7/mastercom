package cn.mastercom.backstage.residentuserquery;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourcesConfig
{

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource getDataSources()
	{
		return new DruidDataSource();
	}

	/**
	 * 配置druid后台管理的servlet
	 *
	 * @return
	 */
	@Bean
	public ServletRegistrationBean druidServlet()
	{
		ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		Map<String, String> initParams = new HashMap<>();
		initParams.put("loginUsername", "admin");
		initParams.put("loginPassword", "admin");
		initParams.put("allow", "");// 默认就是允许所有访问
		initParams.put("deny", "192.168.15.21");// 禁止访问的ip
		bean.setInitParameters(initParams);
		return bean;
	}

	/**
	 * 配置druid后台管理的filter
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean druidFilter()
	{
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new WebStatFilter());
		Map<String, String> initParams = new HashMap<>();
		// 设置不拦截的路径 *.cs *.js /druid/*
		initParams.put("exclusions", "*.js,*.css,/druid/*");
		bean.setInitParameters(initParams);
		// 设置filter拦截 那些请求
		bean.setUrlPatterns(Arrays.asList("/*"));
		return bean;
	}

}
