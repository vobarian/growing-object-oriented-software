package auctionsniper;

import java.util.ArrayList;
import auctionsniper.ui.SnipersTableModel;

public class SniperLauncher implements UserRequestListener {
    private final ArrayList<Auction> notToBeGCd = new ArrayList<>();
    private final AuctionHouse auctionHouse;
    private final SnipersTableModel snipers;

    public SniperLauncher(AuctionHouse auctionHouse, SnipersTableModel snipers) {
        this.auctionHouse = auctionHouse;
        this.snipers = snipers;
    }

    @Override
    public void joinAuction(String itemId) {
        Auction auction = auctionHouse.auctionFor(itemId);
        notToBeGCd.add(auction);
        AuctionSniper sniper = new AuctionSniper(itemId, auction);
        snipers.addSniper(sniper);
        sniper.addSniperListener(new SwingThreadSniperListener(snipers));
        auction.addAuctionEventListener(sniper);
        auction.join();
    }
}