package com.phongdq;

import javax.swing.*;

public class Board {
    private JButton button;
    private boolean isOpen = false;

    public JButton getButton() {
        return button;
    }
    public void setButton(JButton button) {
        this.button = button;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
}
