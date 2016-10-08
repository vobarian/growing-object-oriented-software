package test.integration.auctionsniper.ui;

import com.objogate.wl.swing.probe.ValueMatcherProbe;
import auctionsniper.Item;
import auctionsniper.SniperPortfolio;
import auctionsniper.UserRequestListener;
import auctionsniper.ui.MainWindow;
import test.endtoend.auctionsniper.AuctionSniperDriver;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.After;
import org.junit.Test;

public class MainWindowTest {
    private final MainWindow mainWindow = new MainWindow(new SniperPortfolio());
    private final AuctionSniperDriver driver = new AuctionSniperDriver(100);

    @Test public void makesUserRequestWhenJoinButtonClicked() {
        final ValueMatcherProbe<Item> itemProbe =
                new ValueMatcherProbe<>(equalTo(new Item("an item-id", 789)), "item request");

        mainWindow.addUserRequestListener(
                new UserRequestListener() {
                    public void joinAuction(Item item) {
                        itemProbe.setReceivedValue(item);
                    }
                });

        driver.startBiddingFor("an item-id", 789);
        driver.check(itemProbe);
    }

    @After public void disposeDriver() {
        driver.dispose();
    }
}
