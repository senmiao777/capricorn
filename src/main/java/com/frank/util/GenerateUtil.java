package com.frank.util;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/18 0018 下午 5:12
 */
@Slf4j
public class GenerateUtil {

    private static final String NOTE = "/**\n * //TODO\n*/";
    private static final String ID = "@Id\n@GeneratedValue(strategy = GenerationType.IDENTITY)\nprivate Long id";
    private static final String COLUMN = "@Column(name = \"";

    /**
     * eg:
     * List<String> attrList = Lists.newArrayList(
     * "id bigint(20) NOT NULL AUTO_INCREMENT",
     * "area varchar(40)",
     * "occupation varchar(40)",
     * "requirement varchar(256) ");
     *
     * @param list
     * @return
     */
    public static String sqlList2Entity(List<String> list) {
        StringBuilder sql = new StringBuilder(ID);
        for (String a : list) {
            List<String> strings = Splitter.on(" ").splitToList(a);
            sql.append(NOTE).append(COLUMN).append(strings.get(0)).append("\")")
                    .append(getAttributeType(strings.get(1))).append(strings.get(0)).append(";\n\n");
        }
        log.info("sql = {}", sql.toString());
        return sql.toString();
    }

    /**
     * 获取属性类型
     *
     * @param type
     * @return
     */
    private static String getAttributeType(String type) {
        if (type.contains("varchar")) {
            return "private String ";
        } else if (type.contains("bigint")) {
            return "private Long ";
        } else if (type.contains("int")) {
            return "private Integer ";
        } else if (type.contains("bigdecimal")) {
            return "private BigDecimal ";
        } else {
            return "private String ";
        }
    }


}
