package org.xc.utils.commons;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Test test = new Test("1", "2");

        System.out.println(ReflectionUtils.getMethodByName(test, "setField1"));
        //method1.invoke(test,"hahaha");
        try {
            ReflectionUtils.invoke(test, "setAllaa", "hahaha", "gogogo");
            System.out.println(ReflectionUtils.invoke(test, "getField1"));
            System.out.println(test);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Object invoke(Object object, String methodName, Object... parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class[] parameterTypes = null;
        if (parameters != null) {
            parameterTypes = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }
        }
        Method method = getMethod(object, methodName, parameterTypes);
        return method.invoke(object, parameters);
    }

    public static List<String> getAllFieldName(Object object) {
        Class clazz = object.getClass();
        return getAllFieldName(clazz);
    }

    public static List<String> getAllFieldName(Class clazz) {
        List<String> fieldName = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldName.add(field.getName());
        }
        return fieldName;
    }

    public static List<Method> getMethodByName(Object object, String methodName) {
        List<Method> method = new ArrayList<>();
        Class clazz = object.getClass();
        Method[] clazzDeclaredMethods = clazz.getDeclaredMethods();
        for (Method classMethod : clazzDeclaredMethods
        ) {
            if (classMethod.getName().equals(methodName)) {
                method.add(classMethod);
            }
        }
        return method;
    }

    public static Method getMethod(Object object, String methodName, Class... paramTypes) throws NoSuchMethodException {
        Class clazz = object.getClass();
        return clazz.getDeclaredMethod(methodName, paramTypes);
    }

    static class Test {
        private String field2;
        private String field1;

        public Test(String field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public String getField1() {
            return this.field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public void setAll(String field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "field2='" + field2 + '\'' +
                    ", field1='" + field1 + '\'' +
                    '}';
        }
    }


}
