package auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {
    void sniperLost();
    void sniperStateChanged(final SniperSnapshot snapshot);
    void sniperWinning();
    void sniperWon();
}
