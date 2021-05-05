package org.mockserver.mock.action.http;

import org.apache.commons.lang3.StringUtils;
import org.mockserver.log.model.LogEntry;
import org.mockserver.logging.MockServerLogger;
import org.mockserver.mock.HttpState;
import org.mockserver.mock.action.TriggerCallback;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpResponseTrigger;
import org.slf4j.event.Level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author jamesdbloom
 */
public class HttpResponseActionHandler {

    public HttpResponse handle(HttpResponse httpResponse) {
        return httpResponse.clone();
    }

    public void trigger(HttpState httpState, HttpResponseTrigger httpResponseTrigger, MockServerLogger mockServerLogger) {
        if (httpResponseTrigger != null && StringUtils.isNotBlank(httpResponseTrigger.getTriggerClass())) {
            TriggerCallback triggerCallback = instantiateTrigger(httpResponseTrigger, mockServerLogger);
            if (triggerCallback == null) {
                return;
            }
            mockServerLogger.logEvent(new LogEntry()
                .setType(LogEntry.LogMessageType.INFO)
                .setLogLevel(Level.INFO)
                .setMessageFormat("[Trigger] {} will call to exec with param {}")
                .setArguments(triggerCallback.getClass().getName(), httpResponseTrigger.getParam())
            );
            triggerCallback.setHttpState(httpState);
            triggerCallback.setMockServerLogger(mockServerLogger);
            triggerCallback.trigger(httpResponseTrigger.getParam());
        }

    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    private TriggerCallback instantiateTrigger(HttpResponseTrigger httpResponseTrigger, MockServerLogger mockServerLogger) {
        try {
            Class expectationResponseCallbackClass = Class.forName(httpResponseTrigger.getTriggerClass());
            if (TriggerCallback.class.isAssignableFrom(expectationResponseCallbackClass)) {
                Constructor<? extends TriggerCallback> constructor = expectationResponseCallbackClass.getConstructor();
                return constructor.newInstance();
            } else {
                mockServerLogger.logEvent(
                    new LogEntry()
                        .setLogLevel(Level.ERROR)
                        .setHttpRequest(null)
                        .setMessageFormat(httpResponseTrigger.getTriggerClass() + " does not implement " + TriggerCallback.class.getName() + " required for responses using class callback")
                );
            }
        } catch (ClassNotFoundException e) {
            mockServerLogger.logEvent(
                new LogEntry()
                    .setLogLevel(Level.ERROR)
                    .setMessageFormat("ClassNotFoundException - while trying to instantiate TriggerCallback class \"" + httpResponseTrigger.getTriggerClass() + "\"")
                    .setThrowable(e)
            );
        } catch (NoSuchMethodException e) {
            mockServerLogger.logEvent(
                new LogEntry()
                    .setLogLevel(Level.ERROR)
                    .setMessageFormat("NoSuchMethodException - while trying to create default constructor on TriggerCallback class \"" + httpResponseTrigger.getTriggerClass() + "\"")
                    .setThrowable(e)
            );
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            mockServerLogger.logEvent(
                new LogEntry()
                    .setLogLevel(Level.ERROR)
                    .setMessageFormat("InvocationTargetException - while trying to execute default constructor on TriggerCallback class \"" + httpResponseTrigger.getTriggerClass() + "\"")
                    .setThrowable(e)
            );
        }
        return null;
    }


}
