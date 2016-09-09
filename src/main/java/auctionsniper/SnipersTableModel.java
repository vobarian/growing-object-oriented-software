package auctionsniper;

import javax.swing.table.AbstractTableModel;

import static auctionsniper.MainWindow.STATUS_JOINING;

public class SnipersTableModel extends AbstractTableModel {
    private final static SniperSnapshot STARTING_UP =
            new SniperSnapshot("", 0, 0, SniperState.JOINING);
    private static String[] STATUS_TEXT  = {
            MainWindow.STATUS_JOINING,
            MainWindow.STATUS_BIDDING
    };

    private String state = STATUS_JOINING;
    private SniperSnapshot snapshot = STARTING_UP;

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (Column.at(columnIndex)) {
            case ITEM_IDENTIFIER:
                return snapshot.itemId;
            case LAST_PRICE:
                return snapshot.lastPrice;
            case LAST_BID:
                return snapshot.lastBid;
            case SNIPER_STATE:
                return state;
            default:
                throw new IllegalArgumentException("No column at " + columnIndex);
        }
    }

    public void setStatusText(String newStatusText) {
        state = newStatusText;
        fireTableRowsUpdated(0, 0);
    }

    public void sniperStateChanged(SniperSnapshot newSnapshot) {
        this.snapshot = newSnapshot;
        this.state = STATUS_TEXT[newSnapshot.state.ordinal()];
        fireTableRowsUpdated(0, 0);
    }
}
