package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * 인터셉터를 등록하기 위해서는 WebMvcConfigurer 인터페이스를 구현하면 됨.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //@Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); //등록할 필터
        filterRegistrationBean.setOrder(1); //순서(낮을 수록 먼저 동작)
        filterRegistrationBean.addUrlPatterns("/*"); //필터를 적용시킬 서블릿 URL 패턴

        return filterRegistrationBean;
    }

    //@Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); //등록할 필터
        filterRegistrationBean.setOrder(2); //순서(낮을 수록 먼저 동작)
        filterRegistrationBean.addUrlPatterns("/*"); //필터를 적용시킬 서블릿 URL 패턴

        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()) //등록할 인터셉터
                .order(1) //순서
                .addPathPatterns("/**") //인터셉터를 적용시킬 URL 패턴(서블릿 URL 패턴과는 다름)
                .excludePathPatterns("/css/**", "/*.ico", "/error"); //인터셉터를 적용시키지 않을 URL 목록

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error");
    }
}
