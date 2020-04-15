package com.fl.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：WebMvcConfig
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-08 18:53
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${file.virtualPath}")
    private String virtualPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    //配置拦截的资源以及放行的资源
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*registry.addInterceptor(new MyIntercept())
                // 拦截路径
                .addPathPatterns("/**")
                // 排除路径
                .excludePathPatterns("/", "/userLogin", "/logout", "/randCode")
                // 排除资源请求
                .excludePathPatterns("/static/**");*/
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedHeaders("*").allowedOrigins("*").allowedMethods("*");
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Headers",
                        "Access-Control-Allow-Methods",
                        "Access-Control-Allow-Origin",
                        "Access-Control-Max-Age",
                        "X-Frame-Options")
                .allowCredentials(true).maxAge(14400);//单位s  14400s=60*60*4=4小时
    }

    /*@Bean
    public CookieSerializer httpSessionIdResolver() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName("token");
        cookieSerializer.setUseHttpOnlyCookie(false);
        cookieSerializer.setSameSite(null);
        return cookieSerializer;
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置虚拟目录
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:F:/fileUpload/");
        registry.addResourceHandler(virtualPath + "**").addResourceLocations("file:" + uploadFolder);
    }
}
