package org.mockserver.serialization.model;

import org.mockserver.model.HttpResponseTrigger;
import org.mockserver.model.ObjectWithReflectiveEqualsHashCodeToString;

/**
 * @author jiansong wang
 */
public class HttpResponseTriggerDTO extends ObjectWithReflectiveEqualsHashCodeToString implements DTO<HttpResponseTrigger> {
    private String triggerClass;
    private String param;


    public HttpResponseTriggerDTO() {
    }

    public HttpResponseTriggerDTO(HttpResponseTrigger httpResponseTrigger) {
        if (httpResponseTrigger != null) {
            this.triggerClass = httpResponseTrigger.getTriggerClass();
            this.param = httpResponseTrigger.getParam();
        }
    }

    public HttpResponseTrigger buildObject() {
        HttpResponseTrigger httpResponseTrigger = new HttpResponseTrigger();
        httpResponseTrigger.setParam(this.param);
        httpResponseTrigger.setTriggerClass(this.triggerClass);
        return httpResponseTrigger;
    }

    public String getTriggerClass() {
        return triggerClass;
    }

    public HttpResponseTriggerDTO setTriggerClass(String triggerClass) {
        this.triggerClass = triggerClass;
        return this;
    }

    public String getParam() {
        return param;
    }

    public HttpResponseTriggerDTO setParam(String param) {
        this.param = param;
        return this;
    }
}
