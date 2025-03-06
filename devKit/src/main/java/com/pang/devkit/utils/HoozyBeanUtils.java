package com.pang.devkit.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 由hoozy于2023/6/6 16:22进行创建
 * 转自 https://blog.csdn.net/ichenfang163/article/details/7839167
 * 描述：利用反射机制拷贝对象属性值
 */
public class HoozyBeanUtils {
    /**
     * 拷贝对象属性值
     *
     * @param o1 - orig 被拷贝对象
     * @param o2 - dest 拷贝至对象
     * @author sd(chenfang)
     * @dateTime 2012-8-7下午2:47:53
     * @returnType void
     */
    public static void copyPriperties(Object o1, Object o2) {
        String fileName, str, getName, setName;
        List fields = new ArrayList();
        Method getMethod = null;
        Method setMethod = null;
        try {
            Class c1 = o1.getClass();
            Class c2 = o2.getClass();

            Field[] fs1 = c1.getDeclaredFields();
            Field[] fs2 = c2.getDeclaredFields();
            // 两个类属性比较剔除不相同的属性，只留下相同的属性
            for (int i = 0; i < fs2.length; i++) {
                for (int j = 0; j < fs1.length; j++) {
                    if (fs1[j].getName().equals(fs2[i].getName())) {
                        fields.add(fs1[j]);
                        break;
                    }
                }
            }
            if (null != fields && fields.size() > 0) {
                for (int i = 0; i < fields.size(); i++) {
                    // 获取属性名称
                    Field f = (Field) fields.get(i);
                    fileName = f.getName();
                    // 属性名第一个字母大写
                    str = fileName.substring(0, 1).toUpperCase();
                    // 拼凑getXXX和setXXX方法名
                    getName = "get" + str + fileName.substring(1);
                    setName = "set" + str + fileName.substring(1);
                    // 获取get、set方法
                    getMethod = c1.getMethod(getName, new Class[]{});
                    setMethod = c2.getMethod(setName, new Class[]{f.getType()});
                    // 获取属性值
                    Object o = getMethod.invoke(o1, new Object[]{});
                    // 将属性值放入另一个对象中对应的属性
                    setMethod.invoke(o2, new Object[]{o});
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
