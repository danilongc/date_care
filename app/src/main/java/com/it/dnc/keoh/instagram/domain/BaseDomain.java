package com.it.dnc.keoh.instagram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.it.dnc.keoh.util.JsonUtil;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDomain implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}