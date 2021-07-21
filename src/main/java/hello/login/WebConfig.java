package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); //등록할 필터
        filterRegistrationBean.setOrder(1); //순서(낮을 수록 먼저 동작)
        filterRegistrationBean.addUrlPatterns("/*"); //필터를 적용시킬 서블릿 URL 패턴

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); //등록할 필터
        filterRegistrationBean.setOrder(2); //순서(낮을 수록 먼저 동작)
        filterRegistrationBean.addUrlPatterns("/*"); //필터를 적용시킬 서블릿 URL 패턴

        return filterRegistrationBean;
    }
}
