package de.schini.cheesecaskets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class CasketSwingWrapper extends JComponent implements MouseListener, ICheeseCasketsGameListener {
    private final Casket casket;
    private final int w;
    private final int h;
    private final CheeseCaskets game;
    
    public CasketSwingWrapper(final CheeseCaskets gameP, final Casket c, final int wP, final int hP) {
        super();
        game = gameP;
        game.addListener(this);
        casket  = c;
        w = wP;
        h = hP;
        setBackground(Color.LIGHT_GRAY);
        setBounds(w+casket.getX()*w,h+casket.getY()*h,w,h);
        addMouseListener(this);
    }
    
    private static final Color col1   = Color.MAGENTA;
    private static final Color col2   = Color.RED;
    private static final Color col3   = Color.GREEN;
    private static final Color col4   = Color.YELLOW;
    private static final Color coldef = Color.DARK_GRAY;

    public static final Color getColor(final int val) {
        switch(val) {
            case 1:
                return col1;
            case 2:
                return col2;
            case 3:
                return col3;
            case 4:
                return col4;
            default:
                return coldef;
        }
    }
    
    @Override
    public final int getWidth() {
        return w;
    }

    @Override
    public final int getHeight() {
        return h;
    }
    
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(w,h);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(w,h);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(w,h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //if (isOpaque()) { //paint background
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        //}
        final int linecnt = 1+ (getWidth() + getHeight())/45;
        
        g.setColor(getColor(casket.getMarkLeft()));
        g.drawLine(0,1,0,h-2);
        if(casket.isMarkedLeft()) {
            for(int i=0;i<linecnt;i++)
            g.drawLine(1+i,2+i,1+i,h-2-i);
        }

        g.setColor(getColor(casket.getMarkTop()));
        g.drawLine(1,0,w-2,0);
        if(casket.isMarkedTop()) {
        	for(int i=0;i<linecnt;i++)
            g.drawLine(1+i,1+i,w-3-i,1+i);
        }
        
        g.setColor(getColor(casket.getMarkRight()));
        g.drawLine(w-1,1,w-1,h-2);
        if(casket.isMarkedRight()) {
            for(int i=0;i<linecnt;i++)
            g.drawLine(w-2-i,1+i,w-2-i,h-3-i);
        }
        
        g.setColor(getColor(casket.getMarkBottom()));
        g.drawLine(1,h-1,w-2,h-1);
        if(casket.isMarkedBottom()) {
            for(int i=0;i<linecnt;i++)
            g.drawLine(2+i,h-2-i,w-2-i,h-2-i);
        }

        if(casket.isFull()) {
            g.setColor(getColor(casket.getLastMark()));
            g.drawLine(4,1,w-2,h-5);
            g.drawLine(3,2,w-3,h-4);
            g.drawLine(2,3,w-4,h-3);
            g.drawLine(2,h-4,w-4,3);
            g.drawLine(3,h-3,w-3,4);
            g.drawLine(4,h-2,w-2,5);
        }
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
        //System.out.println("CSW " + casket.getX() + "|" + casket.getY() + "; mouseClicked:" + e);System.out.flush();
        final int x = e.getX();
        final int y = e.getY();
        
        final int dleft   = x*x + (h/2 - y)*(h/2 - y);
        final int dtop    = y*y + (w/2 - x)*(w/2 - x);
        final int dright  = (w-1-x)*(w-1-x) + (h/2 - y)*(h/2 - y);
        final int dbottom = (h-1-y)*(h-1-y) + (w/2 - x)*(w/2 - x);
        //System.out.println("   d**2: " + dleft + " / " + dtop + " / " + dright + " / " + dbottom);System.out.flush();
        
        if(dleft<=dtop && dleft<=dright && dleft<=dbottom) {
    		game.reportBorderEvent(CheeseCaskets.BORDER.LEFT,casket);
        }
        else if(dtop<=dright && dtop<=dbottom) {
    		game.reportBorderEvent(CheeseCaskets.BORDER.TOP,casket);
        }
        else if(dright<=dbottom) {
    		game.reportBorderEvent(CheeseCaskets.BORDER.RIGHT,casket);
        }
        else {
    		game.reportBorderEvent(CheeseCaskets.BORDER.BOTTOM,casket);
        }
        getParent().repaint();
    }
    @Override
    public void mousePressed(final MouseEvent e) {
    }
    @Override
    public void mouseExited(final MouseEvent e) {
    }
    @Override
    public void mouseEntered(final MouseEvent e) {
    }
    @Override
    public void mouseReleased(final MouseEvent e) {
    }

	@Override
	public void gameStarted(final CheeseCaskets game,final int currentPlayer,final int numberOfPlayers) {
		// empty on purpose
	}

	@Override
	public void gameStopped(final CheeseCaskets game) {
		// empty on purpose
	}

	@Override
	public void gameEnded(final CheeseCaskets game) {
		// empty on purpose
	}

	@Override
	public void playerCountChanged(final CheeseCaskets game,final int player,final int count) {
		// empty on purpose
	}

	@Override
	public void currentPlayerChanged(final CheeseCaskets game,final  int currentPlayer) {
		// empty on purpose
	}

	@Override
	public void markChanged(final CheeseCaskets game,final Casket casketP) {
		if(casketP!=null && casketP==casket) {
			repaint();
		}
	}

	@Override
	public void gridChanged(final CheeseCaskets game) {
		// empty on purpose
	}
}
