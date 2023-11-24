package com.tiexiu.dq.annotation;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class RedisListenerConfigurationSelector implements DeferredImportSelector {
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                RedisDelayBootstrapConfiguration.class.getName()
        };
    }
}
