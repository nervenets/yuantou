package com.nervenets.general.adapter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CustomJsonEnumDeserializer extends JsonDeserializer<Enum> {
    @Override
    public Enum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String enumValue = jsonParser.getText();

        JSONObject object = new JSONObject();
        if (jsonParser.isExpectedStartObjectToken()) {
            JsonToken nextToken = jsonParser.nextToken();
            while (JsonToken.END_OBJECT != nextToken) {
                if (JsonToken.FIELD_NAME == nextToken) {
                    String key = jsonParser.getText();
                    jsonParser.nextToken();
                    String value = jsonParser.getText();
                    object.put(key, value);
                }
                nextToken = jsonParser.nextToken();
            }
            if (object.containsKey("value")) {
                enumValue = object.getString("value");
            }
        }

        try {
            if (null == enumValue) return null;
            String fieldName = jsonParser.currentName();

            Class<Enum> enumClass;
            if (jsonParser.getCurrentValue() instanceof Iterable) {
                final JsonStreamContext parent = deserializationContext.getParser().getParsingContext().getParent();
                fieldName = parent.getCurrentName();
                final Type[] arguments = ((ParameterizedType) parent.getCurrentValue().getClass().getDeclaredField(fieldName).getGenericType()).getActualTypeArguments();
                if (arguments.length == 0) return null;
                enumClass = (Class<Enum>) arguments[0];
            } else {
                Class<?> currentClass = jsonParser.getCurrentValue().getClass();
                enumClass = (Class<Enum>) currentClass.getDeclaredField(fieldName).getType();
            }
            if (null == enumClass) return null;
            Enum[] enums = enumClass.getEnumConstants();
            for (Enum anEnum : enums) {
                if (enumValue.equals(anEnum.name())) {
                    return anEnum;
                }
            }
        } catch (Exception e) {
            //
        }
        return null;
    }
}
