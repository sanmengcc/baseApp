package com.base.core.jackson.jackson;

import com.base.core.annotation.DictAnnotation;
import com.base.core.service.TransferFieldService;
import com.base.util.SpringBeanUtil;
import com.base.util.ValidateHelper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private DictAnnotation dictAnnotation;
    private BeanProperty beanProperty;

    private Map<String, String> mapping = new HashMap<>();

    public DictSerializer(DictAnnotation dictAnnotation, BeanProperty beanProperty) {
        this.dictAnnotation = dictAnnotation;
        this.beanProperty = beanProperty;
        String name = dictAnnotation.name();
        TransferFieldService transferFieldService = SpringBeanUtil.getBean(TransferFieldService.class);
        this.mapping = transferFieldService.getMapping(name);
    }

    public DictSerializer() {
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(value);
        String dictName = mapping.get(value);
        if (ValidateHelper.isNotEmptyString(dictName)) {
            String newField = beanProperty.getName() + "Label";
            jsonGenerator.writeStringField(newField, dictName);
        }
    }
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            DictAnnotation dictField = beanProperty.getAnnotation(DictAnnotation.class);
            if (dictField != null) {
                return new DictSerializer(dictField, beanProperty);
            } else {
                return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
            }
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}