package auctionsniper.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionEventListener.PriceSource;
import java.util.HashMap;
import java.util.Map;

public class AuctionMessageTranslator implements MessageListener {
    private final String sniperId;
    private final AuctionEventListener listener;

    public AuctionMessageTranslator(String sniperId, AuctionEventListener listener) {
        this.sniperId = sniperId;
        this.listener = listener;
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        try {
            translate(message.getBody());
        } catch (Exception parseException) {
            listener.auctionFailed();
        }
    }

    private void translate(String messageBody) throws Exception {
        AuctionEvent event = AuctionEvent.from(messageBody);

        String eventType = event.type();
        if ("CLOSE".equals(eventType)) {
            listener.auctionClosed();
        } else if ("PRICE".equals(eventType)) {
            listener.currentPrice(event.currentPrice(), event.increment(),
                    event.isFrom(sniperId));
        }
    }

    private static class AuctionEvent {
        private final Map<String, String> fields = new HashMap<>();
        public String type() throws MissingValueException { return get("Event"); }
        public int currentPrice() throws Exception { return getInt("CurrentPrice"); }
        public int increment() throws Exception { return getInt("Increment"); }

        public PriceSource isFrom(String sniperId) throws MissingValueException {
            return sniperId.equals(bidder()) ? PriceSource.FromSniper : PriceSource.FromOtherBidder;
        }
        private String bidder() throws MissingValueException { return get("Bidder"); }

        private int getInt(String fieldName) throws Exception {
            return Integer.parseInt(get(fieldName));
        }

        private String get(String fieldName) throws MissingValueException {
            String value = fields.get(fieldName);
            if (null == value) {
                throw new MissingValueException(fieldName);
            }
            return value;
        }

        private void addField(String field) {
            String[] pair = field.split(":");
            fields.put(pair[0].trim(), pair[1].trim());
        }

        static AuctionEvent from(String messageBody) {
            AuctionEvent event = new AuctionEvent();
            for (String field : fieldsIn(messageBody)) {
                event.addField(field);
            }
            return event;
        }

        static String[] fieldsIn(String messageBody) {
            return messageBody.split(";");
        }
    }

    private static class MissingValueException extends Exception {
        public MissingValueException(String fieldName) {
            super("Missing value for " + fieldName);
        }
    }
}
