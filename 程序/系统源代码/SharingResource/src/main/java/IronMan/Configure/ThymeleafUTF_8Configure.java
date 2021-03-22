//package IronMan.Configure;
//
//import org.springframework.beans.BeansException;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.thymeleaf.spring4.SpringTemplateEngine;
//import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;
//
///**
// * WebMvc的配置类(自定义Thymeleaf模板)
// *
// * @author Liao Huan
// */
//@Configuration
//public class ThymeleafUTF_8Configure extends WebMvcConfigurerAdapter implements ApplicationContextAware {
//
//	private ApplicationContext applicationContext;
//
//	/**
//	 * 设置上下文
//	 *
//	 * @param applicationContext
//	 * @throws BeansException
//	 */
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		this.applicationContext = applicationContext;
//	}
//
//	/**
//	 * Thymeleaf模板资源解析器(自定义的需要做前缀绑定)
//	 */
//	@Bean
//	@ConfigurationProperties(prefix = "spring.thymeleaf")
//	public SpringResourceTemplateResolver templateResolver() {
//		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//		templateResolver.setCharacterEncoding("UTF-8");
//		templateResolver.setApplicationContext(this.applicationContext);
//		return templateResolver;
//	}
//
//	/**
//	 * Thymeleaf标准方言解释器
//	 */
//	@Bean
//	public SpringTemplateEngine templateEngine() {
//		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//		templateEngine.setTemplateResolver(templateResolver());
//		return templateEngine;
//	}
//
//	/**
//	 * 视图解析器
//	 */
//	@Bean
//	public ThymeleafViewResolver thymeleafViewResolver() {
//		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
//		thymeleafViewResolver.setTemplateEngine(templateEngine());
//		thymeleafViewResolver.setCharacterEncoding("UTF-8");
//		return thymeleafViewResolver;
//	}
//}
