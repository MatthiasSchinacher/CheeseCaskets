package de.schini.cheesecaskets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class CheeseCasketsSwing {

    public static void main(final String[] args) {
        try {
            //Create and set up the window.
            final JFrame frame = new JFrame("CHEESE CASKETS");
            frame.setLocationRelativeTo(null);
            frame.setSize(640,480);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            final JPanel contentPanel = new JPanel();
            frame.setContentPane(contentPanel);
            contentPanel.setLayout(new FormLayout(
                    new ColumnSpec[]{
                             FormSpecs.MIN_COLSPEC
                            ,ColumnSpec.decode("default:grow(1.0)")
                            ,FormSpecs.MIN_COLSPEC
                    }
                    ,new RowSpec[]{
                             FormSpecs.MIN_ROWSPEC
                            ,FormSpecs.DEFAULT_ROWSPEC
                            ,FormSpecs.RELATED_GAP_ROWSPEC
                            ,FormSpecs.DEFAULT_ROWSPEC
                            ,FormSpecs.RELATED_GAP_ROWSPEC
                            ,RowSpec.decode("default:grow(1.0)")
                            ,FormSpecs.MIN_ROWSPEC
                    }));

            final JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new FormLayout(
                    new ColumnSpec[]{
                    		 FormSpecs.DEFAULT_COLSPEC
                    		,FormSpecs.LABEL_COMPONENT_GAP_COLSPEC
                    		,FormSpecs.DEFAULT_COLSPEC
                    		,FormSpecs.RELATED_GAP_COLSPEC
                    		,FormSpecs.DEFAULT_COLSPEC
                    		,FormSpecs.LABEL_COMPONENT_GAP_COLSPEC
                    		,FormSpecs.DEFAULT_COLSPEC
                    		,FormSpecs.RELATED_GAP_COLSPEC
                    		,FormSpecs.DEFAULT_COLSPEC
                    		,FormSpecs.LABEL_COMPONENT_GAP_COLSPEC
                    		,FormSpecs.DEFAULT_COLSPEC
                    		,FormSpecs.RELATED_GAP_COLSPEC
                    		,FormSpecs.MIN_COLSPEC
                            ,ColumnSpec.decode("default:grow(1.0)")
                            ,FormSpecs.DEFAULT_COLSPEC
                    }
                    ,new RowSpec[]{FormSpecs.DEFAULT_ROWSPEC}));
            optionsPanel.setBorder(new TitledBorder("options"));
            contentPanel.add(optionsPanel,"2,2,fill,default");
            
            optionsPanel.add(new JLabel("columns"),"1,1,right,default");
            final JTextField columns = new JTextField();
            columns.setText("15");
            columns.setColumns(5);
            columns.setHorizontalAlignment(JTextField.RIGHT);
            optionsPanel.add(columns,"3,1");
            
            optionsPanel.add(new JLabel("rows"),"5,1,right,default");
            final JTextField rows = new JTextField();
            rows.setText("8");
            rows.setColumns(5);
            rows.setHorizontalAlignment(JTextField.RIGHT);
            optionsPanel.add(rows,"7,1");

            optionsPanel.add(new JLabel("# players"),"9,1,right,default");
            final JTextField nP = new JTextField();
            nP.setText("4");
            nP.setColumns(3);
            nP.setHorizontalAlignment(JTextField.RIGHT);
            optionsPanel.add(nP,"11,1");
            
            final JButton newGame = new JButton();
            newGame.setText("new game"); 
            optionsPanel.add(newGame,"15,1");

            final JPanel actionPanel = new JPanel();
            actionPanel.setLayout(new FormLayout(
                    new ColumnSpec[]{
                   		 FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.LABEL_COMPONENT_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.RELATED_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.LABEL_COMPONENT_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.UNRELATED_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.LABEL_COMPONENT_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.RELATED_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.LABEL_COMPONENT_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.RELATED_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.LABEL_COMPONENT_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.RELATED_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.LABEL_COMPONENT_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.RELATED_GAP_COLSPEC
                   		,FormSpecs.DEFAULT_COLSPEC
                   		,FormSpecs.RELATED_GAP_COLSPEC
                        ,ColumnSpec.decode("default:grow(1.0)")
                   		,FormSpecs.RELATED_GAP_COLSPEC
                        ,FormSpecs.DEFAULT_COLSPEC
                   }
                   ,new RowSpec[]{FormSpecs.DEFAULT_ROWSPEC}));
            actionPanel.setBorder(new TitledBorder("current game"));
            contentPanel.add(actionPanel,"2,4,fill,default");
            
            actionPanel.add(new JLabel("# players"),"1,1,right,default");
            final JTextField gnP = new JTextField();
            gnP.setEnabled(true);
            gnP.setEditable(false);
            gnP.setColumns(3);
            gnP.setHorizontalAlignment(JTextField.RIGHT);
            actionPanel.add(gnP,"3,1");

            actionPanel.add(new JLabel("player to move"),"5,1,right,default");
            final JTextField gP = new JTextField();
            gP.setEnabled(true);
            gP.setEditable(false);
            gP.setColumns(3);
            gP.setHorizontalAlignment(JTextField.RIGHT);
            actionPanel.add(gP,"7,1");

            final JTextField l1 = new JTextField("  1:  "); l1.setEditable(false); l1.setEnabled(false);
            l1.setBackground(CasketSwingWrapper.getColor(1));
            actionPanel.add(l1,"9,1,right,default");
            final JTextField l2 = new JTextField("  2:  "); l2.setEditable(false); l2.setEnabled(false);
            l2.setBackground(CasketSwingWrapper.getColor(2));
            actionPanel.add(l2,"13,1,right,default");
            final JTextField l3 = new JTextField("  3:  "); l3.setEditable(false); l3.setEnabled(false);
            l3.setBackground(CasketSwingWrapper.getColor(3));
            actionPanel.add(l3,"17,1,right,default");
            final JTextField l4 = new JTextField("  4:  "); l4.setEditable(false); l4.setEnabled(false);
            l4.setBackground(CasketSwingWrapper.getColor(4));
            actionPanel.add(l4,"21,1,right,default");
            
            final JTextField p1 = new JTextField(); p1.setEditable(false); p1.setEnabled(false); p1.setColumns(4); p1.setHorizontalAlignment(JTextField.RIGHT);
            final JTextField p2 = new JTextField(); p2.setEditable(false); p2.setEnabled(false); p2.setColumns(4); p2.setHorizontalAlignment(JTextField.RIGHT);
            final JTextField p3 = new JTextField(); p3.setEditable(false); p3.setEnabled(false); p3.setColumns(4); p3.setHorizontalAlignment(JTextField.RIGHT);
            final JTextField p4 = new JTextField(); p4.setEditable(false); p4.setEnabled(false); p4.setColumns(4); p4.setHorizontalAlignment(JTextField.RIGHT);
            actionPanel.add(p1,"11,1");
            actionPanel.add(p2,"15,1");
            actionPanel.add(p3,"19,1");
            actionPanel.add(p4,"23,1");

            final JLabel info = new JLabel();
            info.setHorizontalAlignment(JTextField.CENTER);
            actionPanel.add(info,"27,1");
            
            final JButton back = new JButton(" << ");
            back.setEnabled(false);
            actionPanel.add(back,"29,1");
            
            actionPanel.setVisible(false);
            
            final JPanel casketPanel = new JPanel();
            final JScrollPane sp = new JScrollPane();
            sp.setViewportView(casketPanel);
            contentPanel.add(sp,"2,6,fill,fill");
            
            final ICheeseCasketsGameListener gameListener = new ICheeseCasketsGameListener() {
				@Override
				public void gameStarted(final CheeseCaskets game, final int currentPlayer, final int numberOfPlayers) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							gP.setText(String.valueOf(currentPlayer));
							gnP.setText(String.valueOf(numberOfPlayers));
							info.setText("");
				            back.setEnabled(game.getHistorySize()>0);
						}
					});
				}

				@Override
				public void gameStopped(CheeseCaskets game) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							back.setEnabled(false);
						}
					});
				}

				@Override
				public void gameEnded(CheeseCaskets game) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							info.setText("game over");
						}
					});
				}

				@Override
				public void playerCountChanged(final CheeseCaskets game, final int player, final int count) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							switch(player) {
							case 1:
								p1.setText(String.valueOf(count));
								break;
							case 2:
								p2.setText(String.valueOf(count));
								break;
							case 3:
								p3.setText(String.valueOf(count));
								break;
							case 4:
								p4.setText(String.valueOf(count));
								break;
							default:
								break;
							}
						}
					});
				}

				@Override
				public void currentPlayerChanged(final CheeseCaskets game, final int currentPlayerP) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							gP.setText(String.valueOf(currentPlayerP));
						}
					});
				}

				@Override
				public void gridChanged(final CheeseCaskets game) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							casketPanel.invalidate();
						}
					});
				}

				@Override
				public void markChanged(final CheeseCaskets game, final Casket casket) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
				            back.setEnabled(game.getHistorySize()>0);
							info.setText("");
						}
					});
				}
            };

            newGame.addActionListener(new ActionListener() {
            	CheeseCaskets game = null;
            	ActionListener backListener = null;
				@Override
				public void actionPerformed(final ActionEvent arg0) {
					if(game!=null) {
						game.clearListeners();
						game.stopGame();
						game = null;
					}
					
					int maxX = 14;
					int maxY = 9;
					int nump = 2;
					try {maxX = Integer.parseInt(columns.getText()) -1;} catch(NumberFormatException nfe){;}
					try {maxY = Integer.parseInt(rows.getText()) -1;} catch(NumberFormatException nfe){;}
					try {nump = Integer.parseInt(nP.getText());} catch(NumberFormatException nfe){;}
					maxX = maxX<1 ? 1 : (maxX>999 ? 999 : maxX);
					maxY = maxY<1 ? 1 : (maxY>999 ? 999 : maxY);
					nump = nump<2 ? 2 : (nump>4 ? 4 : nump);
					
					game = new CheeseCaskets(maxX,maxY,nump);
					game.createNewGrid(0.85f,0.6f);
					game.addListener(gameListener);
					
					p1.setEnabled(true);
					p2.setEnabled(true);
					p3.setEnabled(nump>=3);
					p4.setEnabled(nump>=4);
					p1.setText("0");
					p2.setText("0");
					p3.setText(nump>=3?"0":"");
					p4.setText(nump>=4?"0":"");
					l1.setEnabled(true);
					l2.setEnabled(true);
					l3.setEnabled(nump>=3);
					l4.setEnabled(nump>=4);
					
					if(backListener!=null) {
						back.removeActionListener(backListener);
					}
					backListener = new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent arg0) {
							game.back();
						}
					};
		            back.addActionListener(backListener);

		            actionPanel.setVisible(true);
		            
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							casketPanel.removeAll();
							
				            final FormLayout casketLayout = new FormLayout();
				            casketLayout.appendColumn(FormSpecs.RELATED_GAP_COLSPEC);
				            for(int i=0;i<=game.getMaxX();i++) {
				                casketLayout.appendColumn(FormSpecs.DEFAULT_COLSPEC);
				            }
				            casketLayout.appendRow(FormSpecs.RELATED_GAP_ROWSPEC);
				            for(int i=0;i<=game.getMaxY();i++) {
				                casketLayout.appendRow(FormSpecs.DEFAULT_ROWSPEC);
				            }
				            casketPanel.setLayout(casketLayout);
				            
				            final List<Casket> l = game.getCasketList();
				            
				            for(Casket casket: l) {
				                final CasketSwingWrapper tmp = new CasketSwingWrapper(game,casket,60,48);
				                casketPanel.add(tmp,(2+casket.getX()) + "," + (2+casket.getY()));
				            }
				            sp.validate();
				            sp.repaint();

							game.startGame(1);
						}
					});
				}
            });

            //Display the window.
            frame.pack();
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        }
        catch(final Exception e) {
            e.printStackTrace();
        }
    }
}
