package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.annotation
 * @CreateTime: 2022-05-24  19:58
 * @Description: 使用元注解检查登录状态
 */

@Deprecated
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}
