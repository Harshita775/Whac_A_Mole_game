import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
public class WhacAMole {
    int boardWidth = 600;
    int boardHeigth = 650;
    JFrame frame = new JFrame("WhacAMole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel restartPanel = new JPanel();

    JButton[] board = new JButton[9]; 
    ImageIcon moleicon;
    ImageIcon planticon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;

    int score;
    
    WhacAMole() {
        // frame.setVisible(true);
        frame.setSize(boardWidth , boardHeigth);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial" , Font.PLAIN , 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);

        JButton restart = new JButton(new ImageIcon("icons8-restart-64.png"));
         restartPanel.setLayout(new BorderLayout());
         restartPanel.setPreferredSize(new Dimension(50, 50));
         restartPanel.add(restart);
         frame.add(restartPanel,BorderLayout.SOUTH);
         restart.setBackground(Color.white);
         restart.setForeground(new Color(139, 69, 19));
         restart.setFont(new Font("Arial" , Font.BOLD , 20));
         restart.setFocusable(false);
         restart.setText("RESTART");

        boardPanel.setLayout(new GridLayout(3,3));
        // boardPanel.setBackground(Color.black);
        frame.add(boardPanel);

        // planticon = new ImageIcon(getClass().getResource("./piranha.png"));
        Image plantImg = new ImageIcon(getClass().getResource("./piranha1.jpg")).getImage();
        planticon = new ImageIcon(plantImg.getScaledInstance(150,150,java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./monty_mole1.jpg")).getImage();
        moleicon = new ImageIcon(moleImg.getScaledInstance(150,150,java.awt.Image.SCALE_SMOOTH));

        score = 0;
        for(int i = 0;i<9;i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            // tile.setIcon(moleicon);

            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if(tile == currMoleTile) {
                        score += 10;
                        textLabel.setText("Score:  " + Integer.toString(score));
                    }
                    else if(tile == currPlantTile) {
                        textLabel.setText("Game Over:  " + Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for(int i= 0;i<9;i++) {
                            board[i].setEnabled(false);
                        }
                    }
                }
            });

            restart.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tile.setIcon(null);
                    setMoleTimer.start();
                    setPlantTimer.start();
                    score = 0;
                    textLabel.setText("Score: 0");
                    for(int i= 0;i<9;i++) {
                            board[i].setEnabled(true);
                        }
                }
            });
        }

            setMoleTimer = new Timer(1000, new ActionListener() {
            public  void actionPerformed(ActionEvent e) {
                if(currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                // if the tile is occupied by plant , skip the tile this time>>>>
                if(currPlantTile == tile) return;


                currMoleTile = tile;
                currMoleTile.setIcon(moleicon);
            }
        });

        setPlantTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currPlantTile != null) {
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }

                int num = random.nextInt(9);
                JButton tile= board[num];

                // if the tile is occupied by mole , skip the tile this time>>>>
                if(currMoleTile == tile) return;


                currPlantTile = tile;
                currPlantTile.setIcon(planticon);
            }
        });


        setMoleTimer.start();
        setPlantTimer.start();
        

        frame.setVisible(true);
    }


    public static void main(String args[]) {
        WhacAMole wam = new WhacAMole();
    }
}