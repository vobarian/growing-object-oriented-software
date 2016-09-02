package auctionsniper;

import javax.swing.table.AbstractTableModel;

import static auctionsniper.MainWindow.STATUS_JOINING;

public class SnipersTableModel extends AbstractTableModel {
    private String statusText = STATUS_JOINING;

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return statusText;
    }

    public void setStatusText(String newStatusText) {
        statusText = newStatusText;
        fireTableRowsUpdated(0, 0);
    }
}
