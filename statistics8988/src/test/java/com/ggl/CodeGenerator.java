package com.ggl;



import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/*
*
*@Date:2022年4月30日
*
*@Author:Lori Shu
*
*/

public class CodeGenerator {
    public static void main(String[] args) {
      FastAutoGenerator.create("jdbc:mysql://localhost:3306/ggl_zone?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "guang20010427")
    .globalConfig(builder -> {
        builder.author("baomidou") // 设置作者
            .enableSwagger() // 开启 swagger 模式
        // 覆盖已生成文件
            .outputDir("E:\\GGL-ZONE\\statistics8988\\src\\main\\java"); // 指定输出目录
    })
    .packageConfig(builder -> {
        builder.parent("com.ggl.cloud") // 设置父包名
            .moduleName("") // 设置父包模块名
            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\GGL-ZONE\\statistics8988\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
    })
    .strategyConfig(builder -> {
        builder.addInclude("statistics") // 设置需要生成的表名
            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
    })
    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
    .execute();
    }

}
