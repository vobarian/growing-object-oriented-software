package auctionsniper.xmpp;

import java.util.logging.Logger;

public class LoggingXMPPFailureReporter implements XMPPFailureReporter {
    public LoggingXMPPFailureReporter(Logger logger) {
    }

    public void cannotTranslateMessage(String auctionId, String failedMessage, Exception exception) {
    }
}
