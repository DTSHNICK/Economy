package Model;

import javax.swing.table.DefaultTableModel;

/**
 * Created by DTSHNICK on 25.10.2015.
 */
public class TimeTable extends DefaultTableModel {
    @Override
    public Class<?> getColumnClass(int columnIndex) {
                    /*if (!getColumnName(columnIndex).equals("Description of work")) {
                        return Boolean.class;
                    }*/
        if ((columnIndex != 0) && (columnIndex != 1)) {
            return Boolean.class;
        } else {
            if (columnIndex == 1) {
                return Period.class;
            }
        }
        return super.getColumnClass(columnIndex);
    }
}
