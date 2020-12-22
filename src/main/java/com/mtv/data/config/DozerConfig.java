package com.mtv.data.config;

import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.List;

@Configuration
public class DozerConfig {

    @Bean
    public DozerBeanMapperFactoryBean dozerMapper(
            ResourcePatternResolver resourcePatternResolver) throws IOException {
        DozerBeanMapperFactoryBean factoryBean = new DozerBeanMapperFactoryBean();
//        factoryBean.setMappingFiles(
//            resourcePatternResolver.getResources("classpath*:/*mapping.xml"));

        factoryBean.setMappingBuilders(List.of(builderItems));

        return factoryBean;
    }

    BeanMappingBuilder builderItems = new BeanMappingBuilder() {
        @Override
        protected void configure() {
            mapping(com.mtv.data.collection.select.service.model.Item.class, com.mtv.data.collection.select.repository.entity.Item.class)
                    .fields("coverPhotoId", "coverPhoto.id");
        }
    };

}