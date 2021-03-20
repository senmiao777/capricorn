package com.frank.util;

import com.frank.annotation.RedisLock;
import com.frank.model.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

@Slf4j
public class AnnotationScanUtil {


    private static Map<String, PathParam> URL_PARAM_MAP = new HashMap<>();
    private static boolean scaned = false;
    private static String BASE_PACKAGE;

    private volatile static boolean cacheLoaded = false;

    public static boolean cacheLoad() {

        if (cacheLoaded) {
            return cacheLoaded;
        }

        synchronized (AnnotationScanUtil.class) {
            if (cacheLoaded) {
                return cacheLoaded;
            }
            init();
            cacheLoaded = true;
        }
        return cacheLoaded;
    }

    public static <T extends Annotation> void init() {

        long l = System.currentTimeMillis();
        Set<String> classNames = scanClass("com.frank", null);
        for (String className : classNames) {
            scanMethod(className);
        }
        log.info("123耗时={}", (System.currentTimeMillis() - l));

    }

    public static PathParam getPathParam(String url) {
        if (URL_PARAM_MAP.size() == 0) {
            return null;
        }
        log.info("URL_PARAM_MAP={}", URL_PARAM_MAP);
        if (URL_PARAM_MAP.containsKey(url)) {
            return URL_PARAM_MAP.get(url);
        }

        PathMatcher pathMatcher = new AntPathMatcher();

        for (Map.Entry<String, PathParam> entry : URL_PARAM_MAP.entrySet()) {
            String k = entry.getKey();
            if (pathMatcher.match(k, url)) {
                return URL_PARAM_MAP.get(k);
            }
        }

        return null;
    }

    private static Set<String> scanClass(String basePackage, Set<String> nameSet) {
        if (scaned && !basePackage.startsWith(BASE_PACKAGE)) {
            return nameSet;
        }

        if (nameSet == null) {
            nameSet = new HashSet<>();
        }
        if (basePackage == null || basePackage.length() == 0) {
            basePackage = "com.frank";
        }
        if (!scaned && basePackage.endsWith("controller")) {
            scaned = true;
            BASE_PACKAGE = basePackage;
        }

        String classpath = AnnotationScanUtil.class.getResource("/").getPath();
        String basePack = basePackage.replace(".", File.separator);

        String filePath = classpath + basePack;
        List<String> names = null;

        if (isJarFile(filePath)) {
            try {
                names = readFromJarFile(filePath, basePackage);
            } catch (Exception e) {
                log.error("scanClass readFromJarFile异常e=" + e);
            }
        } else {
            names = readFromDirectory(filePath);
        }

        for (String name : names) {
            if (isClassFile(name)) {
                if (scaned && name.endsWith("Controller.class")) {
                    nameSet.add(toFullyQualifiedName(name, basePackage));
                }
            } else {
                scanClass(basePackage + "." + name, nameSet);
            }
        }

        return nameSet;
    }

    private static void scanMethod(String className) {

        try {
            Class<? extends Object> clazz = Class.forName(className);
            RequestMapping classPath = clazz.getAnnotation(RequestMapping.class);
            String[] clazzPath = {""};
            if (classPath != null) {
                clazzPath = classPath.value();
            }
            for (Method method : clazz.getMethods()) {
                RedisLock t = method.getAnnotation(RedisLock.class);
                if (t == null) {
                    continue;
                }
                RequestMapping path = method.getAnnotation(RequestMapping.class);
                if (path == null) {
                    continue;
                }

                for (String clazzPathOne : clazzPath) {
                    String[] pathMethod = path.value();
                    if (clazzPathOne.endsWith("/")) {
                        clazzPathOne = clazzPathOne.substring(0, clazzPathOne.length() - 1);
                    }
                    for (String pathMethodOne : pathMethod) {
                        if (!pathMethodOne.startsWith("/")) {
                            pathMethodOne = "/" + pathMethodOne;
                        }

                        String pathReally = clazzPathOne + pathMethodOne;
                        if (!pathReally.startsWith("/")) {
                            pathReally = "/" + pathReally;
                        }
                        URL_PARAM_MAP.put(pathReally, new PathParam(t.key(), t.maxWaitTime(), t.maxExpireTime()));
                    }
                }
            }
        } catch (Exception e) {
            log.error("scanMethod异常e=" + e);
        }
    }

    private static String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');

        int pos = shortName.indexOf('.');
        if (-1 != pos) {
            shortName = shortName.substring(0, pos);
        }
        sb.append(shortName);
        return sb.toString();
    }

    private static List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {

        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();
        List<String> nameList = new ArrayList<>();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                nameList.add(name);
            }

            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    private static List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    private static boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private static boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

}