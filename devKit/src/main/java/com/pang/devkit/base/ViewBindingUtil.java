package com.pang.devkit.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ViewBindingUtil {
    public static <VB extends ViewBinding, T extends Object> Class<VB> getClassVB(Class<T> clazz) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type type : types) {
            if (type.toString().endsWith("Binding")) {
                return (Class<VB>) type;
            }
        }
        return (Class<VB>) types[0];
    }

    public static <VB extends ViewBinding, T extends Object> VB viewBindingJavaClass(LayoutInflater inflater, ViewGroup container, View view, Class<T> clazz) {
        try {
            return viewBinding(inflater, container, view, getClassVB(clazz));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                throw new Exception(clazz.getSimpleName() + "viewBinding error");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static <VB extends ViewBinding> VB viewBinding(LayoutInflater inflater, ViewGroup container, View view, Class<VB> clazz) {
        try {
            if (view != null) {
                Method method = clazz.getMethod("bind", View.class);
                return (VB) method.invoke(null, view);
            } else {
                Method method = clazz.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                return (VB) method.invoke(null, inflater, container, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                throw new Exception(clazz.getSimpleName() + "viewBinding error");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }

    }
}
