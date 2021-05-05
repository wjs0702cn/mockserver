package org.mockserver.mock.action;

import org.mockserver.logging.MockServerLogger;
import org.mockserver.mock.HttpState;

public interface TriggerCallback {
    void setHttpState(HttpState httpState);
    void setMockServerLogger(MockServerLogger logger);
    void trigger(String param);
}
