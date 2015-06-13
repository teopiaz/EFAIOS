package it.polimi.ingsw.cg15.gui.client;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;


public class RendererCelleLog extends DefaultListCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
        if (index % 2 != 0) {
            c.setBackground(Color.GRAY);
        } else {
            c.setBackground(Color.LIGHT_GRAY);
        }
        if (index == 0) {
            c.setBackground(Color.blue);
            c.setForeground(Color.WHITE);
            c.setFont(CFont.getFont("TopazPlus"));
        }

        return c;
    }

}
