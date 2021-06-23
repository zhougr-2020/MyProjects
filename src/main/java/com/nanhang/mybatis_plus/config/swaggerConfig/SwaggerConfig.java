package com.nanhang.mybatis_plus.config.swaggerConfig;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
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

@Configuration
@EnableSwagger2
@EnableKnife4j //启动 Knife4j  增强swagger前端UI体检
//@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private boolean flag = true;

    @Bean
    public Docket createRestApi() {

        //增加请求头
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        if (flag) { //是否开启接口文档可以手动输入用户token进行调试,开启后可在请求头位置输入用户的token
            ticketPar.name("SSOToken").description("用户唯一标识")//name表示名称，description表示描述
                    .modelRef(new ModelRef("string")).parameterType("header")
                    .required(false).build();//required表示是否必填
            pars.add(ticketPar.build());//添加完此处一定要在下边加上否则不生效
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true) //是否启用
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //扫描包下的类，生成接口文档(第二种)
                //.apis(RequestHandlerSelectors.basePackage("com.nanhang.mybatis_plus.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);//添加请求头
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("新版H5接口文档")
                .description("新版H5接口文档")
                .termsOfServiceUrl("https://www.csair.com/cn/")
                .version("1.0.0")
                .build();
    }

}