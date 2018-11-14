//package com.vpis.schedule;
//
//import com.google.common.base.Predicate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *  @Author: Yuhan.Tang
// *  @ClassName: Swagger2
// *  @package: com.entertainment.asset
// *  @Date: Created in 2018/7/19 上午11:58
// *  @email yuhan.tang@magicwindow.cn
// *  @Description:
// */
//@Configuration
//@EnableSwagger2
//public class Swagger2 {
//
//    @Value("${spring.profiles}")
//    private String profiles;
//
//    @Bean
//    public Docket createRestApi(){
//
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        pars.add(tokenPar.build());
//        tokenPar.name("locale").description("语言").modelRef(new ModelRef("string")).parameterType("query").defaultValue("ch").required(false);
//        pars.add(tokenPar.build());
//        Predicate<String> pathSelectors = PathSelectors.any();
//        if (profiles.contains("prod")) {
//            pathSelectors = PathSelectors.none();
//        }
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo()).select()
//                .apis(RequestHandlerSelectors.basePackage("com.vpis.schedule.controller"))
//                .paths(pathSelectors).build()
//                .globalOperationParameters(pars);
//    }
//
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("Spring Boot中使用Swagger2构建RESTful APIs")
//                .description("描述")
//                .termsOfServiceUrl("http://zsx.com.cn")
//                .version("1.0")
//                .build();
//    }
//}
