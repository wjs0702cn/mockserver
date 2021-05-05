package org.mockserver.model;

/**
 * trigger when http-response to handle addition job controlled by `triggerClass`
 */
public class HttpResponseTrigger{
    private String triggerClass;
    private String param;

    public HttpResponseTrigger() {
    }

    public String getTriggerClass() {
        return triggerClass;
    }

    public void setTriggerClass(String triggerClass) {
        this.triggerClass = triggerClass;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
