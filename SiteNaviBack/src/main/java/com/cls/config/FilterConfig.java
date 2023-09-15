package com.cls.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cls.filter.MyFilter;

/**
 * アプリケーションの設定を行うクラス
 * 「@Configuration」をクラスに付与し、この中に「@Bean」を付与したメソッドを記述すると、
 * アプリケーション起動時に、「@Bean」を付与したメソッドのコンポーネントが作成される
 */

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<MyFilter>  filter2() {
    	System.out.println("filter2执000行！！！！！！");
        FilterRegistrationBean<MyFilter>  registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/*");
//        registrationBean.setName("MyFilter");
        registrationBean.setOrder(1); //指定执行顺序为2
        return registrationBean;
    }

//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
//			throws IOException, ServletException {
//		// TODO 自動生成されたメソッド・スタブ
//		filterChain.doFilter(request, response);//doFilter将请求转发给过滤器链下一个filter , 如果没有filter那就是你请求的资源
//		System.out.println("filter111！！！！！");
//	}
}