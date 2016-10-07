package auctionsniper.xmpp;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import auctionsniper.Auction;
import auctionsniper.AuctionHouse;
import auctionsniper.Main;

public class XMPPAuctionHouse implements AuctionHouse {
    private final XMPPConnection connection;

    public XMPPAuctionHouse(XMPPConnection connection) {
        this.connection = connection;
    }

    public Auction auctionFor(String itemId) {
        return new XMPPAuction(connection, auctionId(itemId, connection));
    }

    public void disconnect() {
        connection.disconnect();
    }

    public static XMPPAuctionHouse connect(String hostname, String username, String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, Main.AUCTION_RESOURCE);
        return new XMPPAuctionHouse(connection);
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(Main.AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }
}
