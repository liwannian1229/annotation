package com.lwn;

/**
 * @author liwannian
 * @since 2019-04-17 12:04
 */
public class Config {

    /**
     * 包名：controller
     */
    public static final String PACKAGE_NAME_CONTROLLER = "com.lwn.model.controller";

    /**
     * 包名：service
     */
    public static final String PACKAGE_NAME_SERVICE = "com.lwn.model.service";

    /**
     * 包名：service.impl
     */
    public static final String PACKAGE_NAME_SERVICE_IMPL = "com.lwn.model.service.impl";

    /**
     * 包名：model
     */
    public static final String PACKAGE_NAME_MODEL = "com.lwn.model.entity";

    /**
     * 包名：dao
     */
    public static final String PACKAGE_NAME_DAO = "com.lwn.model.dao";

    /**
     * 包名：xml
     */
    public static final String PACKAGE_NAME_XML = "com.lwn.model.mapper";

    /**
     * 文件名后缀：Model
     */
    public static final String FILE_NAME_MODEL = "%sEntity";

    /**
     * 文件名后缀：Dao
     */
    public static final String FILE_NAME_DAO = "%sDao";

    /**
     * 文件名后缀：Mapper
     */
    public static final String FILE_NAME_XML = "%sMapper";

    /**
     * MP开头，Service结尾
     */
    public static final String FILE_NAME_SERVICE = "%sService";

    /**
     * 文件名后缀：ServiceImpl
     */
    public static final String FILE_NAME_SERVICE_IMPL = "%sServiceImpl";

    /**
     * 文件名后缀：Controller
     */
    public static final String FILE_NAME_CONTROLLER = "%sController";

    /**
     * 逻辑删除字段
     */
    public static final String FIELD_LOGIC_DELETE_NAME = "delete_state";

    /**
     * 作者
     */
    public static final String AUTHOR = "liwannian";

    /**
     * 生成文件的输出目录
     */
    public static String projectPath = System.getProperty("user.dir");

    /**
     * 输出目录
     */
    public static final String outputDir = projectPath + "/repo/src/main/java";

    /**
     * 模板引擎。velocity / freemarker / beetl
     */
    public static final String TEMPLATE_ENGINE = "velocity";

    /**
     * 是否支持Swagger，默认不支持
     */
    public static final Boolean SWAGGER_SUPPORT = false;

}
