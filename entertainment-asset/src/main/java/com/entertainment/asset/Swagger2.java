package com.entertainment.asset;

import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.jwt.JwtService;
import com.entertainment.asset.service.sys.SysUserService;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: Swagger2
 *  @package: com.entertainment.asset
 *  @Date: Created in 2018/7/19 上午11:58
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${spring.profiles}")
    private String profiles;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public Docket createRestApi(){

        SysUser sysUser = sysUserService.findSysUserByEmail("test@qq.com");
        String token = "";
        if(sysUser!=null){
            token = jwtService.createJwt(sysUser);
        }
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
        .defaultValue(token)
        .required(false);
        pars.add(tokenPar.build());
        tokenPar.name("locale").description("语言").modelRef(new ModelRef("string")).parameterType("query").defaultValue("ch").required(false);
        pars.add(tokenPar.build());
//        //隐藏生产swagger地址
        Predicate<String> pathSelectors = PathSelectors.any();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.entertainment.asset.controller"))
                .paths(pathSelectors).build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("描述")
                .termsOfServiceUrl("http://zsx.com.cn")
                .version("1.0")
                .build();
    }
}
