package com.kafka.test.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *  Created by humdeef on 2017/1/8.
 */
public class Model {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toJson() throws Exception {
        return objectMapper.writeValueAsString(this);
    }

    public static Model toModel(String jsonStr) throws Exception {
        return objectMapper.readValue(jsonStr, Model.class);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
