package org.mockserver.mock.action.http.trigger;

import org.apache.commons.lang3.StringUtils;
import org.mockserver.log.model.LogEntry;
import org.mockserver.logging.MockServerLogger;
import org.mockserver.mock.HttpState;
import org.mockserver.mock.action.TriggerCallback;
import org.mockserver.model.HttpRequest;
import org.slf4j.event.Level;

import java.util.Arrays;

import static org.mockserver.log.model.LogEntry.LogMessageType.CLEARED;

public class DeleteExpByIdTrigger implements TriggerCallback {

    private HttpState httpState;
    private MockServerLogger mockServerLogger;

    @Override
    public void setHttpState(HttpState httpState) {
        this.httpState = httpState;
    }

    @Override
    public void setMockServerLogger(MockServerLogger logger) {
        this.mockServerLogger = logger;
    }

    @Override
    public void trigger(String param) {
        // param like :   111111,232123,4323334,4321111
        if (StringUtils.isNotBlank(param)) {
            Arrays.stream(param.split(",")).forEach(
                key -> {
                    mockServerLogger.logEvent(new LogEntry()
                        .setType(CLEARED)
                        .setLogLevel(Level.INFO)
                        .setMessageFormat("[DeleteExpByIdTrigger] ready to clear expectation that match Id:{}")
                        .setArguments(key)
                    );

                    HttpRequest req = HttpRequest.request();
                    req.setId(key);
                    httpState.getRequestMatchers().clear(req);
                }
            );

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
