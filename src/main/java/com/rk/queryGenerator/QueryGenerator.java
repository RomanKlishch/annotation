package com.rk.queryGenerator;

import com.rk.annotation.Column;
import com.rk.annotation.Id;
import com.rk.annotation.Table;

import java.lang.reflect.Field;
import java.util.StringJoiner;

public class QueryGenerator {

    public String getAll(Class<?> clazz) {
        StringBuilder queryBuilder = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        StringJoiner joiner = new StringJoiner(", ");

        for (Field field : fields) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation != null) {
                joiner.add(getFieldName(field, annotation));
            }
        }

        queryBuilder.append("SELECT ")
                .append(joiner)
                .append(" FROM ")
                .append(getTableName(clazz));

        return String.valueOf(queryBuilder);
    }

    public String insert(Object object) throws IllegalAccessException {
        if (object == null) {
            throw new IllegalArgumentException("Trying to pass null to a method as an argument");
        }
        StringBuilder queryBuilder = new StringBuilder();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        StringJoiner joinerParameter = new StringJoiner(", ", " (", ")");
        StringJoiner joinerValue = new StringJoiner(", ", "(", ")");

        for (Field field : fields) {
            field.setAccessible(true);
            Object valueFromField = field.get(object);
            field.setAccessible(false);
            if (valueFromField != null) {
                Column annotation = field.getAnnotation(Column.class);
                if (annotation != null && !field.isAnnotationPresent(Id.class)) {
                    joinerParameter.add(getFieldName(field, annotation));
                    joinerValue.add(wrapValue(field, valueFromField));
                }
            }
        }

        queryBuilder.append("INSERT INTO ")
                .append(getTableName(object.getClass()))
                .append(joinerParameter)
                .append(" VALUES ")
                .append(joinerValue);

        return queryBuilder.toString();
    }

    public String update(Object object) throws IllegalAccessException {
        if (object == null) {
            throw new IllegalArgumentException("Trying to pass null to a method as an argument");
        }
        StringBuilder queryBuilder = new StringBuilder();
        StringJoiner joiner = new StringJoiner(", ");
        StringBuilder provisoBuilder = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object valueFromField = field.get(object);
            field.setAccessible(false);

            if (valueFromField != null) {
                Column annotation = field.getAnnotation(Column.class);
                if (annotation != null && !field.isAnnotationPresent(Id.class)) {
                    String fieldName = getFieldName(field, annotation);
                    String fieldValue = wrapValue(field, valueFromField);
                    joiner.add(fieldName.concat(" = ").concat(fieldValue));
                } else if (annotation != null && field.isAnnotationPresent(Id.class)) {
                    provisoBuilder.append(getFieldName(field, annotation))
                            .append(" = ")
                            .append(wrapValue(field, valueFromField));
                }
            }
        }

        queryBuilder.append("UPDATE ")
                .append(getTableName(object.getClass()))
                .append(" SET ")
                .append(joiner)
                .append(" WHERE ")
                .append(provisoBuilder);
        return String.valueOf(queryBuilder);
    }

    public String delete(Object object) throws IllegalAccessException {
        if (object == null) {
            throw new IllegalArgumentException("Trying to pass null to a method as an argument");
        }
        StringBuilder queryBuilder = new StringBuilder();
        StringBuilder provisoBuilder = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object valueFromField = field.get(object);
            field.setAccessible(false);
            if (valueFromField != null && field.isAnnotationPresent(Id.class)) {
                provisoBuilder.append(field.getName()).append(" = ").append(valueFromField);
            }
        }

        queryBuilder.append("DELETE FROM ")
                .append(getTableName(object.getClass()))
                .append(" WHERE ").append(provisoBuilder);

        return String.valueOf(queryBuilder);
    }

    private String wrapValue(Field field, Object valueFromField) throws IllegalAccessException {
        Class<?> type = field.getType();
        if (type.isPrimitive() && !type.equals(char.class)) {
            return String.valueOf(valueFromField);
        } else {
            return "'" + valueFromField + "'";
        }
    }

    private String getFieldName(Field field, Column annotation) {
        String fieldName;
        if ("".equals(annotation.value())) {
            fieldName = field.getName();
        } else {
            fieldName = annotation.value();
        }
        return fieldName;
    }

    //TODO: нормально ли не создавать переменную а сразу ставить return
    private String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation != null) {
            if ("".equals(annotation.value())) {
                return clazz.getSimpleName();
            } else {
                return annotation.value();
            }
        }
        return null;
    }
}
