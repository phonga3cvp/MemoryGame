package com.phongdq;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Random;

public class MemoryGame extends JFrame {
    private static final String IMAGE_FOLDER = "images/";
    private static final String FILES[] = { IMAGE_FOLDER + "BlueEyesWhiteDragon.jpg", IMAGE_FOLDER + "DarkMagician.png", IMAGE_FOLDER + "GearfriedtheIronKnight.png",
            IMAGE_FOLDER + "HarpieLady.png", IMAGE_FOLDER + "Kuriboh.png", IMAGE_FOLDER + "RedEyesBlackDragon.png" };
    private static final String BACK_IMAGES = IMAGE_FOLDER + "Back.png";

    private Board boards[];
    private ImageIcon backIcon;
    private ImageIcon icons[];
    private JLabel time;
    private Timer timer;
    private Timer countDownTimer;

    private int numButtons;
    private int row;
    private int numClicks;
    private int oddClickIndex;
    private int currentIndex;
    private int countDownSecond;
    private int point = 0;

    public MemoryGame() {
        setTitle("My Memory Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        row = 2;
        countDownSecond = 20;
        numButtons = FILES.length * 2;
        backIcon = new ImageIcon(BACK_IMAGES);

        boards = new Board[numButtons];
        icons = new ImageIcon[numButtons];

        JPanel topPanel = new JPanel();
        JLabel timeText = new JLabel("Time: ");
        time = new JLabel("");
        topPanel.add(timeText);
        topPanel.add(time);
        topPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(row, numButtons / row));

        add(bottomPanel);

        for (int i = 0, j = 0; i < FILES.length; i++) {
            icons[j] = new ImageIcon(FILES[i]);
            setUpBoard(j);
            bottomPanel.add(boards[j++].getButton());

            icons[j] = icons[j - 1];
            setUpBoard(j);
            bottomPanel.add(boards[j++].getButton());
        }

        Random random = new Random();
        for (int i = 0; i < icons.length; i++) {
            int index = random.nextInt(numButtons);
            ImageIcon temp = icons[i];
            icons[i] = icons[index];
            icons[index] = temp;
        }
        pack();

        setVisible(true);

        timer = new Timer(1000, new TimerListener());

        countDownTimer = new Timer(1000, new CountDownListener());
        countDownTimer.start();
    }

    private void setUpBoard(int index) {
        boards[index] = new Board();
        boards[index].setButton(new JButton(""));
        boards[index].getButton().addActionListener(new ImageButtonListener());
        boards[index].getButton().setIcon(backIcon);
    }

    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!boards[currentIndex].isOpen()) {
                boards[currentIndex].getButton().setIcon(backIcon);
            }
            if (!boards[oddClickIndex].isOpen()) {
                boards[oddClickIndex].getButton().setIcon(backIcon);
            }

            timer.stop();
        }
    }


    private class CountDownListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (countDownSecond <= 0) {
                if (point >= FILES.length) {
                    System.out.println("Point: " + point);
                    JOptionPane.showMessageDialog(null, "You win!", "Result", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    dispose();
                }
                else {
                    System.out.println("Point: " + point);
                    JOptionPane.showMessageDialog(null, "You lose!", "Result", JOptionPane.WARNING_MESSAGE);
                    setVisible(false);
                    dispose();
                }
                ((Timer) e.getSource()).stop();
            } else {
                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                time.setText(df.format(countDownSecond * 1000));
                countDownSecond--;
            }
        }
    }

    private class ImageButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (timer.isRunning())
                return;
            numClicks++;

            for (int i = 0; i < numButtons; i++) {
                if (e.getSource() == boards[i].getButton()) {
                    boards[i].getButton().setIcon(icons[i]);
                    currentIndex = i;
                }
            }

            if (numClicks % 2 == 0) {
                if (currentIndex == oddClickIndex) {
                    numClicks--;
                    return;
                }
                if (icons[currentIndex] != icons[oddClickIndex]) {
                    timer.start();
                } else {
                    point++;
                    boards[currentIndex].setOpen(true);
                    boards[oddClickIndex].setOpen(true);
                }
            } else {
                oddClickIndex = currentIndex;
            }
        }
    }

    public static void main(String[] args) {
        new MemoryGame();
    }
}