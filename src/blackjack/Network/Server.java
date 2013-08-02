package blackjack.Network;
import blackjack.BlackjackHand;
import blackjack.Card;
import blackjack.Deck;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class Server {
    //static BlackjackGUI bjGUI;
    static ServerSocket server = null;
    static Socket clientSocket = null;
    public static ClientWorker t[] = new ClientWorker[3];
    public static ServerWorker sw;
    public static ServerAccepter sa;
    int portNum = 4444;
    static int player = 0;
    static int oPlayer = 0;
    static boolean sPlayer = true;
    static public boolean dTurn = false;
    public boolean sNewGame = false;
    public int[] pBetting = new int[4];
    public static int numPlayers = 1;
    //int sNew = 0;
    int[] ports = {4444,5555,6666};
    String winner = "";
    public BlackjackCanvas board;
    JFrame frame = new JFrame("Let's Play Blackjack!!!");
        String sOP = "U:\\BlackJack\\src\\blackjack\\cardPics\\";
        ImageIcon backOfCard = new ImageIcon(sOP+"0.jpg");
        ImageIcon diamondAce = new ImageIcon(sOP+"1.jpg");
        ImageIcon diamondTwo = new ImageIcon(sOP+"2.jpg");
        ImageIcon diamondThree = new ImageIcon(sOP+"3.jpg");
        ImageIcon diamondFour = new ImageIcon(sOP+"4.jpg");
        ImageIcon diamondFive = new ImageIcon(sOP+"5.jpg");
        ImageIcon diamondSix = new ImageIcon(sOP+"6.jpg");
        ImageIcon diamondSeven = new ImageIcon(sOP+"7.jpg");
        ImageIcon diamondEight = new ImageIcon(sOP+"8.jpg");
        ImageIcon diamondNine = new ImageIcon(sOP+"9.jpg");
        ImageIcon diamondTen = new ImageIcon(sOP+"10.jpg");
        ImageIcon diamondJack = new ImageIcon(sOP+"11.jpg");
        ImageIcon diamondQueen = new ImageIcon(sOP+"12.jpg");
        ImageIcon diamondKing = new ImageIcon(sOP+"13.jpg");
        ImageIcon heartAce = new ImageIcon(sOP+"14.jpg");
        ImageIcon heartTwo = new ImageIcon(sOP+"15.jpg");
        ImageIcon heartThree = new ImageIcon(sOP+"16.jpg");
        ImageIcon heartFour = new ImageIcon(sOP+"17.jpg");
        ImageIcon heartFive = new ImageIcon(sOP+"18.jpg");
        ImageIcon heartSix = new ImageIcon(sOP+"19.jpg");
        ImageIcon heartSeven = new ImageIcon(sOP+"20.jpg");
        ImageIcon heartEight = new ImageIcon(sOP+"21.jpg");
        ImageIcon heartNine = new ImageIcon(sOP+"22.jpg");
        ImageIcon heartTen = new ImageIcon(sOP+"23.jpg");
        ImageIcon heartJack = new ImageIcon(sOP+"24.jpg");
        ImageIcon heartQueen = new ImageIcon(sOP+"25.jpg");
        ImageIcon heartKing = new ImageIcon(sOP+"26.jpg");
        ImageIcon clubAce = new ImageIcon(sOP+"27.jpg");
        ImageIcon clubTwo = new ImageIcon(sOP+"28.jpg");
        ImageIcon clubThree = new ImageIcon(sOP+"29.jpg");
        ImageIcon clubFour = new ImageIcon(sOP+"30.jpg");
        ImageIcon clubFive = new ImageIcon(sOP+"31.jpg");
        ImageIcon clubSix = new ImageIcon(sOP+"32.jpg");
        ImageIcon clubSeven = new ImageIcon(sOP+"33.jpg");
        ImageIcon clubEight = new ImageIcon(sOP+"34.jpg");
        ImageIcon clubNine = new ImageIcon(sOP+"35.jpg");
        ImageIcon clubTen = new ImageIcon(sOP+"36.jpg");
        ImageIcon clubJack = new ImageIcon(sOP+"37.jpg");
        ImageIcon clubQueen = new ImageIcon(sOP+"38.jpg");
        ImageIcon clubKing = new ImageIcon(sOP+"39.jpg");
        ImageIcon spadeAce = new ImageIcon(sOP+"40.jpg");
        ImageIcon spadeTwo = new ImageIcon(sOP+"41.jpg");
        ImageIcon spadeThree = new ImageIcon(sOP+"42.jpg");
        ImageIcon spadeFour = new ImageIcon(sOP+"43.jpg");
        ImageIcon spadeFive = new ImageIcon(sOP+"44.jpg");
        ImageIcon spadeSix = new ImageIcon(sOP+"45.jpg");
        ImageIcon spadeSeven = new ImageIcon(sOP+"46.jpg");
        ImageIcon spadeEight = new ImageIcon(sOP+"47.jpg");
        ImageIcon spadeNine = new ImageIcon(sOP+"48.jpg");
        ImageIcon spadeTen = new ImageIcon(sOP+"49.jpg");
        ImageIcon spadeJack = new ImageIcon(sOP+"50.jpg");
        ImageIcon spadeQueen = new ImageIcon(sOP+"51.jpg");
        ImageIcon spadeKing = new ImageIcon(sOP+"52.jpg");
        public JButton hit;
        public JButton stand;
        //public JButton newGame;
        private JTextField dealer;
        private JTextField mplayer;
        final String PLAYER_MONEY = "2000";
        final String DEALER_MONEY = "2000";
        public int moneyBet, bidding;
        public int[] playerMoney;
        public String[] names = new String[4];
        char state;
        String pMoney, dMoney;
        public static int wStates[] = {1,1,1,1,1};

    public Server() { //Begin Constructor
            frame.setBackground(new Color(130,51,40));
            frame.setSize(700,560);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            dealer = new JTextField (10);
            mplayer = new JTextField (10);

            JPanel betPanel = new JPanel();
            betPanel.add(new JLabel("Dealer"));
            betPanel.add(dealer);
            betPanel.add(new JLabel("Player"));
            betPanel.add(mplayer);
            frame.getContentPane().add(betPanel,BorderLayout.NORTH);
            dealer.setEditable(false);
            mplayer.setEditable(false);
            dealer.setText(DEALER_MONEY);
            mplayer.setText(PLAYER_MONEY);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground( new Color(220,200,180) );
            frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            hit = new JButton( "Hit!" );
            buttonPanel.add(hit);
            hit.setEnabled(false);

            stand = new JButton( "Stand!" );
            buttonPanel.add(stand);
            stand.setEnabled(false);

            /*newGame = new JButton( "New Game" );
            buttonPanel.add(newGame);*/

            playerMoney = new int[4];
            for (int i = 0; i < 4; i++)
            playerMoney[i] = 2000;
            //Integer.parseInt(player.getText().trim());
            //dealerMoney = Integer.parseInt(dealer.getText().trim());
            board = new BlackjackCanvas();
            frame.getContentPane().add(board, BorderLayout.CENTER);
            //newGame.addActionListener(board);
            stand.addActionListener(board);
            hit.addActionListener(board);
            dealer.addActionListener(board);
            mplayer.addActionListener(board);
            frame.setVisible (true);
         /*server = new Server();
         panel = new JPanel();
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setTitle("Let's Play Blackjack");
         frame.pack();
         frame.setSize(500,500);
         frame.setVisible(true);
         frame.listenSocket();
         panel.getContentPane(frame);*/
         //bjGUI = bjg;
         listenSocket();
    } //End Constructor
    public static void main (String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server();
            }
        });
    }
       public class BlackjackCanvas extends JPanel implements ActionListener
       {
             // A nested class that displays the card game and does all the work
             // of keeping track of the state and responding to user events.

          public Deck deck;         // A deck of cards to be used in the game.

          public BlackjackHand dealerHand;   // Hand containing the dealer's cards.
          public BlackjackHand[] playerHand;   // Hand containing the user's cards.

          public String message = "";;  // A message drawn on the canvas, which changes
                           //    to reflect the state of the game.

          public boolean gameInProgress = false;  // Set to true when a game begins and to false
                                   //   when the game ends.

          Font bigFont;      // Font that will be used to display the message.
          Font smallFont;    // Font that will be used to draw the cards.

          public char[] winner = {'l','l','l','l','l'};


          public BlackjackCanvas()
          {
                // Constructor.  Creates fonts and starts the first game.
             dealerHand = new BlackjackHand();
             playerHand = new BlackjackHand[4];
             setBackground( new Color(0,120,0) );
             smallFont = new Font("SansSerif", Font.PLAIN, 12);
             bigFont = new Font("Serif", Font.BOLD, 14);
             //doNewGame();
          }


          public void actionPerformed(ActionEvent evt)
          {
                 // Respond when the user clicks on a button by calling
                 // the appropriate procedure.  Note that the canvas is
                 // registered as a listener in the BlackjackGUI class.
                 aServer(evt);
             /*String command = evt.getActionCommand();
             if (command.equals("Hit!"))
                doHit();
             else if (command.equals("Stand!"))
                doStand();
             else if (command.equals("New Game"))
                doNewGame();*/
          }


          public void doHit(int i)
          {
                 // This method is called when the user clicks the "Hit!" button.
                 // First check that a game is actually in progress.  If not, give
                 // an error message and exit.  Otherwise, give the user a card.
                 // The game can end at this point if the user goes over 21 or
                 // if the user has taken 5 cards without going over 21.
             /*if (gameInProgress == false)
             {
                message = "Click \"New Game\" to start a new game.";
                repaint();
                return;
             }*/

             //playerHand.addCard( deck.dealCard() );
                 if (playerHand[i].getBlackjackValue() > 21 )
                 {
                    //message = "You've busted!  Sorry, you lose.";
                    wStates[i] = 1;
                    /*playerMoney[i] -= bidding;
                    dealerMoney += bidding;
                    dMoney = Integer.toString(dealerMoney);
                    pMoney = Integer.toString(playerMoney[i]);
                    dealer.setText(dMoney);
                    player.setText(pMoney);
                    gameInProgress = false;*/
                 } else if (playerHand[i].getBlackjackValue() == 21) {
                     wStates[i] = 2;
                 } else if (playerHand[i].getCardCount() == 5) {
                    //message = "You win by taking 5 cards without going over 21.";
                    wStates[i] = 3;
                    /*playerMoney += bidding;
                    dealerMoney -= bidding;
                    dMoney = Integer.toString(dealerMoney);
                    pMoney = Integer.toString(playerMoney);
                    dealer.setText(dMoney);
                    player.setText(pMoney);
                    gameInProgress = false;*/
                 }
                 /*else
                 {
                     if (state == 's') {
                         message = "Player "+ Server.t[i].name +  " has "+ playerHand[i].getBlackjackValue() + ".  Hit or Stand?";
                     }
                 }*/
             repaint();
          }


          public String doStand() {
                  // This method is called when the user clicks the "Stand!" button.
                  // Check whether a game is actually in progress.  If it is,
                  // the game ends.  The dealer takes cards until either the
                  // dealer has 5 cards or more than 16 points.  Then the
                  // winner of the game is determined.
             /*if (gameInProgress == false) {
                message = "Click \"New Game\" to start a new game.";
                repaint();
                return;
             }*/
             //gameInProgress = false;
             boolean allOne = true;
             String temp = "";
             int big[] = {-1,-1,-1,-1};
             int n = 0;

             while (dealerHand.getBlackjackValue() <= 16 && dealerHand.getCardCount() < 5)
                dealerHand.addCard( deck.dealCard() );
             if (dealerHand.getBlackjackValue() > 21)
             {
                 wStates[5] = 1;
                 /*message = "You win!  Dealer has busted with " + dealerHand.getBlackjackValue() + ".";
                 playerMoney[i] += bidding;
                 dealerMoney -= bidding;
                 dMoney = Integer.toString(dealerMoney);
                 pMoney = Integer.toString(playerMoney);
                 dealer.setText(dMoney);
                 player.setText(pMoney);
                 gameInProgress = false;*/
             } else if (dealerHand.getBlackjackValue() == 21) {
                 wStates[5] = 2;
             }
             else if (dealerHand.getCardCount() == 5)
             {
                 wStates[5] = 3;
                 winner[5] = 'w';
                 /*message = "Sorry, you lose.  Dealer took 5 cards without going over 21.";
                 playerMoney -= bidding;
                 dealerMoney += bidding;
                 dMoney = Integer.toString(dealerMoney);
                 pMoney = Integer.toString(playerMoney);
                 dealer.setText(dMoney);
                 player.setText(pMoney);*/

             }
            for (int i = 0; i < wStates.length; i++) {
                if (wStates[i] != 1) {
                    allOne = false;
                     if (wStates[i] == 3) {
                        winner[i] = 'w';
                     for (int j = 0; j < wStates.length; j++) {
                         if (wStates[j] != 3) {
                             wStates[j] = 1;
                             winner[j] = 'l';
                         }
                     }
                     } else if (wStates[i] == 2) {
                         winner[i] = 'w';
                     }
                }
             }
             for (int i = 0; i < Server.numPlayers && allOne; i++) {
                 if (playerHand[big[n]].getBlackjackValue() <
                         playerHand[i].getBlackjackValue()) {
                     big[n] = i;
                 } else if (playerHand[big[n]].getBlackjackValue() ==
                         playerHand[i].getBlackjackValue()) {
                     big[++n] = i;
                 }
             }
             for (int i = 0; i < Server.numPlayers && allOne; i++) {
                 if (dealerHand.getBlackjackValue() > playerHand[big[0]].getBlackjackValue())
                 {
                     /*message = "Sorry, you lose, " + dealerHand.getBlackjackValue()
                                                       + " to " + playerHand[i].getBlackjackValue() + ".";
                     playerMoney -= bidding;
                     dealerMoney += bidding;
                     dMoney = Integer.toString(dealerMoney);
                     pMoney = Integer.toString(playerMoney);
                     dealer.setText(dMoney);
                     player.setText(pMoney);*/
                     winner[5] = 'w';
                 }
                 else if (dealerHand.getBlackjackValue() == playerHand[big[0]].getBlackjackValue())
                 {
                     /*message = "Sorry, you lose.  Dealer wins on a tie.";
                     playerMoney -= bidding;
                     dealerMoney += bidding;
                     dMoney = Integer.toString(dealerMoney);
                     pMoney = Integer.toString(playerMoney);
                     dealer.setText(dMoney);
                     player.setText(pMoney);*/
                     winner[5] = 'w';
                 }
                 else
                 {
                     winner[i] = 'w';
                     /*message = "You win, " + playerHand.getBlackjackValue()
                                                       + " to " + dealerHand.getBlackjackValue() + "!";
                     playerMoney += bidding;
                     dealerMoney -= bidding;
                     dMoney = Integer.toString(dealerMoney);
                     pMoney = Integer.toString(playerMoney);
                     dealer.setText(dMoney);
                     player.setText(pMoney);
                     gameInProgress = false;
                     gameInProgress = false;*/
                 }
             }
                if (winner[5] == 'w') {
                    temp = "5";
                } else {
                    for (int i = 0; i < winner.length; i++) {
                        if (winner [i] == 'w'){
                            temp = temp + i;
                        }
                    }
                    if (temp.length() == 0)
                        temp = "5";
                }
              return temp;
          }

          public String doNewGame(int a)
          {
             String inputValue;
             String temp = "";
             /*if (gameInProgress)
             {
                message = "You still have to finish this game!";
                repaint();
                return;
             }*/
             gameInProgress = true;
            if (state == 's') {
                 deck = new Deck();   // Create the deck and hands to use for this game.
                 dealerHand = new BlackjackHand();
                 for (int i = 0; i < Server.numPlayers; i++)
                 playerHand[i] = new BlackjackHand();
                 deck.shuffle();
                 dealerHand.addCard( deck.dealCard() );  // Deal two cards to each player.
                 dealerHand.addCard( deck.dealCard() );
                 for (int i = 0; i < Server.numPlayers; i++) {
                    playerHand[i].addCard( deck.dealCard() );
                    playerHand[i].addCard( deck.dealCard() );
                 }
             }
             repaint();
             inputValue = JOptionPane.showInputDialog("Place your bet");
             while(inputValue == null)
                inputValue = JOptionPane.showInputDialog("No bids were entered, please make a bet");
             if (!inputValue.equals(""))
             	{
                    while (true) {
                        try
                        {
                            while(inputValue == null)
                                inputValue = JOptionPane.showInputDialog("No bids were entered, please make a bet");
                            bidding = Integer.parseInt(inputValue);
                            break;
                        }catch (NumberFormatException e)
                        {
                            inputValue = null;
                            inputValue = JOptionPane.showInputDialog("INTEGER ONLY!!!!!! DAVID IS MAD");
                        }
                    }
             	}
            while (bidding > playerMoney[a])
            {
                JOptionPane.showMessageDialog(frame, "Not enough money!","Information",JOptionPane.INFORMATION_MESSAGE);
                inputValue = JOptionPane.showInputDialog("Place your bet");
                while(inputValue == null)
                    inputValue = JOptionPane.showInputDialog("No bids were entered, please make a bet");
                if (!inputValue.equals(""))
                {
                    while (true){
                        try
                        {
                            while(inputValue == null)
                                inputValue = JOptionPane.showInputDialog("No bids were entered, please make a bet");
                            bidding = Integer.parseInt(inputValue);
                            break;
                        }catch (NumberFormatException e)
                        {
                            inputValue = null;
                            inputValue = JOptionPane.showInputDialog("INTEGER ONLY!!!!!! DAVID IS MAD");
                        }
                    }
                }
                bidding = Integer.parseInt(inputValue);
            }

             for (int i = 0; i < Server.numPlayers; i++) {
                 if (dealerHand.getBlackjackValue() == 21) {
                     winner[4] = 'w';
                     gameInProgress = false;
                     message = "Sorry, you lose.  Dealer has Blackjack.";
                     /*playerMoney -= bidding;
                     dealerMoney += bidding;
                     dMoney = Integer.toString(dealerMoney);
                     pMoney = Integer.toString(playerMoney);
                     dealer.setText(dMoney);
                     player.setText(pMoney);
                     gameInProgress = false;*/
                 } else if (playerHand[i].getBlackjackValue() == 21) {
                     winner[i] = 'w';
                     gameInProgress = false;
                     message = "You win!  You have Blackjack.";
                     /*playerMoney += bidding;
                     dealerMoney -= bidding;
                     dMoney = Integer.toString(dealerMoney);
                     pMoney = Integer.toString(playerMoney);
                     dealer.setText(dMoney);
                     player.setText(pMoney);
                     gameInProgress = false;
                     gameInProgress = false;*/
                 } else {
                     message = "Deal Cards!";
                 }
             }
            repaint();

   	   /*while (bidding > dealerMoney)
   	   {
   	   	JOptionPane.showMessageDialog(frame, "Dealer cannot match this bet, dealer only has "+ dealerMoney + " to make bets" ,"Information",JOptionPane.INFORMATION_MESSAGE);
   	   	inputValue = JOptionPane.showInputDialog("Place your bet");
   		while(inputValue == null)
                    inputValue = JOptionPane.showInputDialog("No bids were entered, please make a bet");
   		if (!inputValue.equals(""))
             	{
                    while (true)
                    try
                    {
                    	bidding = Integer.parseInt(inputValue);
             		break;
                    }catch (NumberFormatException e)
                    {
                    	inputValue = JOptionPane.showInputDialog("INTEGER ONLY!!!!!! DAVID IS MAD");
                    }
             	}
   		bidding = Integer.parseInt(inputValue);
   	    }*/
             if (winner[4] == 'w') {
                temp = "5";
             } else {
                for (int i = 0; i < winner.length; i++) {
                    if (winner [i] == 'w')
                        temp = temp + i;
                }
             }
             repaint();
             return temp;
          }

          public void doLoser () {

          }

          public void changeMessage(String m) {
              message = m;
              repaint();
          }

          public void paintComponent(Graphics g) {
                // The paint method shows the message at the bottom of the
                // canvas, and it draws all of the dealt cards spread out
                // across the canvas.

             super.paintComponent(g); // fill with background color.

             g.setFont(bigFont);
             g.setColor(Color.green);
             g.drawString(message, 10, getSize().height - 10);

             // Draw labels for the two sets of cards.

             g.drawString("Dealer's Cards:", 10, 23);
             for (int i = 0; i < Server.numPlayers; i++)
             g.drawString(names[i], 10+i*125, 153 + i*160);

             // Draw dealer's cards.  Draw first card face down if
             // the game is still in progress,  It will be revealed
             // when the game ends.

             g.setFont(smallFont);
             if (gameInProgress)
                drawCard(g, null, 10, 30);
             else
                drawCard(g, dealerHand.getCard(0), 10, 30);
             for (int i = 1; i < dealerHand.getCardCount(); i++)
                drawCard(g, dealerHand.getCard(i), 10 + i * 90, 30);


             // Draw the user's cards.
             for (int a = 0; a < Server.numPlayers; a++){
                for (int i = 0; i < playerHand[a].getCardCount(); i++)
                    if (i < 2)
                        drawCard(g, playerHand[a].getCard(i), 10 + i * 90, 160 + a * 160);
             }
          }


          void drawCard(Graphics g, Card card, int x, int y)
          {
                  // Draws a card as a 80 by 100 rectangle with
                  // upper left corner at (x,y).  The card is drawn
                  // in the graphics context g.  If card is null, then
                  // a face-down card is drawn.  (The cards are
                  // rather primitive.)

                    // Draw a face-down card\

              if (card == null)
              {
               	g.drawImage(backOfCard.getImage(),10,30,80,100,this);
              }
              else if (card.getSuit() == Card.DIAMONDS && card.getValueAsString().equals("A"))
                  g.drawImage(diamondAce.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("2"))
                    g.drawImage (diamondTwo.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("3"))
                    g.drawImage (diamondThree.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("4"))
                            g.drawImage (diamondFour.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("5"))
                            g.drawImage (diamondFive.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("6"))
                            g.drawImage (diamondSix.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("7"))
                            g.drawImage (diamondSeven.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("8"))
                            g.drawImage (diamondEight.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("9"))
                            g.drawImage (diamondNine.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("T"))
                            g.drawImage (diamondTen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("J"))
                            g.drawImage (diamondJack.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("Q"))
                            g.drawImage (diamondQueen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.DIAMONDS && card.getValueAsString().equals("K"))
                            g.drawImage (diamondKing.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("A"))
                            g.drawImage(heartAce.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("2"))
                            g.drawImage (heartTwo.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("3"))
                            g.drawImage (heartThree.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("4"))
                            g.drawImage (heartFour.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("5"))
                            g.drawImage (heartFive.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("6"))
                            g.drawImage (heartSix.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("7"))
                            g.drawImage (heartSeven.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("8"))
                            g.drawImage (heartEight.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("9"))
                            g.drawImage (heartNine.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("T"))
                            g.drawImage (heartTen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("J"))
                            g.drawImage (heartJack.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("Q"))
                            g.drawImage (heartQueen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.HEARTS && card.getValueAsString().equals("K"))
                            g.drawImage (heartKing.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("A"))
                            g.drawImage(clubAce.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("2"))
                            g.drawImage (clubTwo.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("3"))
                            g.drawImage (clubThree.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("4"))
                            g.drawImage (clubFour.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("5"))
                            g.drawImage (clubFive.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("6"))
                            g.drawImage (clubSix.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("7"))
                            g.drawImage (clubSeven.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("8"))
                            g.drawImage (clubEight.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("9"))
                            g.drawImage (clubNine.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("T"))
                            g.drawImage (clubTen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("J"))
                            g.drawImage (clubJack.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("Q"))
                            g.drawImage (clubQueen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("K"))
                            g.drawImage (clubKing.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("A"))
                            g.drawImage(clubAce.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("2"))
                            g.drawImage (clubTwo.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("3"))
                            g.drawImage (clubThree.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("4"))
                            g.drawImage (clubFour.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("5"))
                            g.drawImage (clubFive.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("6"))
                            g.drawImage (clubSix.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("7"))
                            g.drawImage (clubSeven.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("8"))
                            g.drawImage (clubEight.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("9"))
                            g.drawImage (clubNine.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("T"))
                            g.drawImage (clubTen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("J"))
                            g.drawImage (clubJack.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("Q"))
                            g.drawImage (clubQueen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.CLUBS && card.getValueAsString().equals("K"))
                            g.drawImage (clubKing.getImage(),x,y,80,100,this);
             else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("A"))
                            g.drawImage(spadeAce.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("2"))
                            g.drawImage (spadeTwo.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("3"))
                            g.drawImage (spadeThree.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("4"))
                            g.drawImage (spadeFour.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("5"))
                            g.drawImage (spadeFive.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("6"))
                            g.drawImage (spadeSix.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("7"))
                            g.drawImage (spadeSeven.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("8"))
                            g.drawImage (spadeEight.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("9"))
                            g.drawImage (spadeNine.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("T"))
                            g.drawImage (spadeTen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("J"))
                            g.drawImage (spadeJack.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("Q"))
                            g.drawImage (spadeQueen.getImage(),x,y,80,100,this);
              else if (card.getSuit () == Card.SPADES && card.getValueAsString().equals("K"))
                            g.drawImage (spadeKing.getImage(),x,y,80,100,this);
             repaint();
          }
    	}
    public void listenSocket(){
        names[0] = JOptionPane.showInputDialog("Enter Your Name");
        (sw = new ServerWorker()).start();
        try{
          server = new ServerSocket(portNum);
        } catch (IOException e) {
        }
	(sa = new ServerAccepter()).start();
        /*while(true){
            try {
                clientSocket = server.accept();
                player++;
                for(int i=0; i<3; i++){
                    if(t[i]==null) {
                        (t[i] = new ClientWorker(clientSocket,t)).start();
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e);}
        }*/
        /*while(true){
          try{
            t[i] = new Thread (new ClientWorker(server.accept(), textArea));
            t.start();
          } catch (IOException e) {
            System.out.println("Accept failed: 4444");
            System.exit(-1);
          }
        }*/
    }
    public class ServerWorker extends Thread {
        String temp = "";
        public void run() {
            while (true) {
                if (sPlayer) {
                    if (board.gameInProgress) {
                        winner = board.doStand();
                    } else {
                        winner = temp;
                    }
                    while (!winner.equals(""));
                    temp = board.doNewGame(3);
                    sNewGame = true;
                    pBetting[3] = bidding;
                    if (board.gameInProgress) {
                        hit.setEnabled(true);
                        stand.setEnabled(true);
                        while(sPlayer);
                    }
                }
            }
        }
    }
	 
     public class ServerAccepter extends Thread {
        public void run() {
            while(true){
                try {
                    clientSocket = server.accept();
                    player++;
                    numPlayers++;
                    for(int i=0; i<3; i++){
                        if(t[i]==null) {
                            (t[i] = new ClientWorker(clientSocket,t)).start();
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e);}
                }

        }
     }

    public void aServer(ActionEvent ev) {
        if(ev.getSource() == hit) {
            //Send data over socket
            board.playerHand[0].addCard(board.deck.dealCard());
            board.doHit(3);
        } else if (ev.getSource() == stand) {
	    		hit.setEnabled(false);
	    		stand.setEnabled(false);
            oPlayer = 0;
            sPlayer=false;
        }
    }
    /*void nextP (int p) {
  	
    }

    void repaintAll () {
   
    }

    protected void finalize(){
    //Objects created in run method are finalized when
    //program terminates and thread exits
        try{
            server.close();
        } catch (IOException e) {
            System.out.println("Could not close socket");
            System.exit(-1);
        }
    }*/
    public class ClientWorker extends Thread {
        private Socket client;
        ClientWorker[] t;
        BufferedReader in = null;
        PrintWriter out = null;
        public String name = null;

        ClientWorker(Socket client, ClientWorker[] t) {
            this.client = client;
            this.t = t;
        }

        public void run(){
            String line;
            Card temp;
            int pNum = -1;
            try{
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
                while (name == null)
                    name = in.readLine();
                for (int i = 1; i <= player; i++) {
                    if (t[i] == this)
                        t[i].out.println("#" + i);
                }
                for (int i = 0; i < player; i++) {
                    if (t[i]!=null && t[i]==this) {
                        names[i] = name;
                    }
                }
                for (int i = 0; i < player; i++) {
                    if (t[i]!=null && t[i]!=this) {
                        t[i].out.println("n" + i + name);
                    }
                }
                while (true) {
                    if (sNewGame) {
                        out.println("N");
                        for (int i = 0; i < board.playerHand.length; i++) {
                            for (int j = 0; j < 2; j++) {
                                out.println("p" + board.playerHand[i].getCard(j).getValueAsString()+
                                        board.playerHand[i].getCard(j).getSuitAsString()+i);
                            }
                        }
                        for (int i = 0; i < 2; i++) {
                            out.println("a"+board.dealerHand.getCard(i).getValueAsString()+
                                    board.dealerHand.getCard(i).getSuitAsString());
                        }
                        out.println("bid");
                        while((line = in.readLine()) == null);
                        if (line.charAt(0) == 'b') {
                            for (int i = 0; i < player; i++) {
                                if (t[i] == this)
                                    pBetting[i] = Integer.parseInt(line);
                            }
                            for (int i = 0; i < pBetting.length; i++) {
                                out.println("b"+ i + pBetting[i]);
                            }
                        } sNewGame = false;
                    }
                    if (!sPlayer) {
                        for (; oPlayer < player; oPlayer++) {
                            line = in.readLine();
                            /*if (Server.oPlayer==0) {
                                bjGUI.hit.setEnabled(false);
                                bjGUI.stand.setEnabled(false);
                            }*/
                            if (t[oPlayer] == this)
                                t[oPlayer].out.println("aOY");
                            if (line.charAt(0) == 'h') {
                                temp = board.deck.dealCard();
                                board.playerHand[oPlayer+1].addCard(temp);
                                out.println("p"+temp.getValueAsString()+temp.getSuitAsString()+oPlayer);
                            } else if (line.charAt(0) == 's') {
                                if (oPlayer == player-1){
                                    sPlayer = true;
                                    dTurn = true;
                                    //bjGUI.board.doStand();
                                } else {
                                    out.println("s" + t[oPlayer].name);
                                }
                            } else {
                                pNum = line.charAt(3) - '0';
                                board.playerHand[pNum].addCard(Deck.setCard(Card.getSuitAsInt(line),
                                        Card.getValueAsInt(line)));
                                for (int i = 0; i < player; i++) {
                                    t[i].out.println(line);
                                }
                            }
                        }
                    }
                    if (sPlayer) {
                        if (!winner.equals("")){
                            for (int i = 0; i < board.dealerHand.getCardCount(); i++) {
                                out.println("a"+board.dealerHand.getCard(i).getValueAsString()+
                                        board.dealerHand.getCard(i).getSuitAsString());
                            }
                            out.println("w"+winner);
                            winner = "";
                            //sNew = 1;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("in or out failed");
                System.exit(-1);
            }
        }
    }
}


