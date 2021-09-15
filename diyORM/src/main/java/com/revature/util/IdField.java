
package com.revature.util;

import java.lang.reflect.Field;

import com.revature.annotations.Id;

public class IdField {

    private Field field;

    public IdField(Field field) {
        if (field.getAnnotation(Id.class) == null) {
            throw new IllegalStateException("Cannot create IdField object! Provided field, " + getName() + "is not annotated with @Id");
        }
        this.field = field;
    }

    public String getName() {
        return field.getName();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public String getColumnName() {
        return field.getAnnotation(Id.class).columnName();
    }

}
