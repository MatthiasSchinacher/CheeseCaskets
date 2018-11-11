package de.schini.cheesecaskets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Instances of this class represent a "game" of <i>chesse caskets</i> in a platform- independent way.
 * <p />
 * The abstract grid, in which the caskets reside, has to be visualized as being
 * left to right in X- Dimension (the X- Coordinate increases to the right, the next-
 * right neighbor - if it exists - has Coordinate X+1) and top to bottom in Y- Dimension
 * (the Y- Coordinate increases down, not up!, the next- below neighbor - if it exists -
 * has Coordinate Y+1, the next above Y-1).
 * <p />
 * The Coordinates given are not meant to represent coordinates in a UI directly,
 * but are meant to be abstract indicators of the caskets relative positions.
 * 
 * @author mschina
 */
public final class CheeseCaskets {
	private final int maxX;
	private final int maxY;
	private final int numberOfPlayers;
	private final Casket[][] casketGrid;
	private Integer currentPlayer = null;
	
	public static enum BORDER {
		LEFT, TOP, RIGHT, BOTTOM;
	};
	
	/**
	 * Constructor, that creates an empty game.
	 * 
	 * @param maxXP
	 * @param maxYP
	 * @param numberOfPlayersP
	 */
	public CheeseCaskets(final int maxXP
						,final int maxYP
						,final int numberOfPlayersP) {
		super();
		if(maxXP<0) {
			throw new RuntimeException("<init>: maxXP<0!");
		}
		if(maxYP<0) {
			throw new RuntimeException("<init>: maxYP<0!");
		}
		if(numberOfPlayersP<2) {
			throw new RuntimeException("<init>: numberOfPlayersP<2!");
		}
		maxX = maxXP;
		maxY = maxYP;
		numberOfPlayers = numberOfPlayersP;
		casketGrid = new Casket[maxX+1][maxY+1];
		currentPlayer = null; // game has not startet
	}
	
	public final int getCasketCount() {
		synchronized(this) {
			int cnt = 0;
			for(int x=0;x<=maxX;x++) {
				for(int y=0;y<=maxY;y++) {
					if(casketGrid[x][y]!=null) {
						cnt++;
					}
				}
			}
			return cnt;
		}
	}

	public final int getFilledCount() {
		synchronized(this) {
			int cnt = 0;
			for(int x=0;x<=maxX;x++) {
				for(int y=0;y<=maxY;y++) {
					Casket c;
					if((c=casketGrid[x][y])!=null) {
						if(c.isFull()) {
							cnt++;
						}
					}
				}
			}
			return cnt;
		}
	}

	public final int getPlayerCount(final int player) {
		synchronized(this) {
			int cnt = 0;
			for(int x=0;x<=maxX;x++) {
				for(int y=0;y<=maxY;y++) {
					Casket c;
					if((c=casketGrid[x][y])!=null) {
						if(c.isFull() && c.getLastMark()==player) {
							cnt++;
						}
					}
				}
			}
			return cnt;
		}
	}

	public final List<Casket> getCasketList() {
		synchronized(this) {
			final List<Casket> l = new ArrayList<Casket>();
			for(int x=0;x<=maxX;x++) {
				for(int y=0;y<=maxY;y++) {
					Casket c;
					if((c=casketGrid[x][y])!=null) {
						l.add(c);
					}
				}
			}
			return l;
		}
	}
	
	public final int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	public final int getMaxX() {
		return maxX;
	}
	public final int getMaxY() {
		return maxY;
	}
	public final Casket getCasket(final int x, final int y) {
		return casketGrid[x][y];
	}

	public final Integer getCurrentPlayer() {
		synchronized(this) {
			return currentPlayer;
		}
	}
	
	public final void emptyGrid() {
		synchronized(this) {
			for(int x=0;x<maxX;x++) { // delete current grid
				for(int y=0;y<maxY;y++) {
					casketGrid[x][y] = null;
				}
			}
			fireGridChanged();
		}
	}

	public final void createNewGrid(final float d, final float xPref) {
		if(d<=(float)0 || d>(float)1) {
			throw new RuntimeException("createNewMap(...): d not in ]0,1]!");
		}
		if(xPref<=(float)0 || xPref>=(float)1) {
			throw new RuntimeException("createNewMap(...): xPref not in ]0,1[!");
		}
		final int maxSize    = (maxX+1)*(maxY+1);
		int targetSize = (int)(d*(float)maxSize);
		targetSize = targetSize<=0 ? 1 : targetSize;
		targetSize = targetSize>maxSize ? maxSize : targetSize;

		synchronized(this) {
			for(int x=0;x<maxX;x++) { // delete current grid
				for(int y=0;y<maxY;y++) {
					casketGrid[x][y] = null;
				}
			}
			
			if(targetSize==maxSize) {
				for(int x=0;x<maxX;x++) { // full grid
					for(int y=0;y<maxY;y++) {
						casketGrid[x][y] = new Casket(x,y);
					}
				}
			}
			else {
				final Random r = new Random();
				final List<Casket> l = new ArrayList<Casket>();
				
				while(l.size()<targetSize) {
					if(l.size()==0) {
						final int x = maxX/3 + r.nextInt(maxX/3+1);
						final int y = maxY/3 + r.nextInt(maxY/3+1);
						casketGrid[x][y] = new Casket(x,y);
						l.add(casketGrid[x][y]);
					}
					else {
						final int i = l.size()==1 ? 0 : r.nextInt(l.size());
						final Casket c = l.get(i);
						final int test = r.nextInt(5);
						if(test<=c.getMarkCount()) { // yes, we prefer Caskets that are allready heavily connected
							int x = Integer.MIN_VALUE;
							int y = Integer.MAX_VALUE;
							final float dimPref    = r.nextFloat();
							final int choice = r.nextInt(2);
							if(dimPref > xPref) { // up/down
								switch(choice) {
								case 0:
									if(!c.hasUp() && c.getY()>0) {
										x = c.getX();
										y = c.getY()-1;
									}
									break;
								case 1:
									if(!c.hasDown() && c.getY()<maxY) {
										x = c.getX();
										y = c.getY()+1;
									}
									break;
								default:
									break;
								}
							}
							else { // right/left
								switch(choice) {
								case 0:
									if(!c.hasLeft() && c.getX()>0) {
										x = c.getX()-1;
										y = c.getY();
									}
									break;
								case 1:
									if(!c.hasRight() && c.getX()<maxX) {
										x = c.getX()+1;
										y = c.getY();
									}
									break;
								default:
									break;
								}
							}
							if(x>=0 && y>=0) {
								final Casket newc = new Casket(x,y);
								casketGrid[x][y] = newc;
								casketGrid[newc.getX()][newc.getY()] = newc;
								l.add(newc);
								if(x>0 && casketGrid[x-1][y]!=null) {
									casketGrid[x-1][y].setRight(newc);
								}
								if(x<maxX && casketGrid[x+1][y]!=null) {
									casketGrid[x+1][y].setLeft(newc);
								}
								if(y>0 && casketGrid[x][y-1]!=null) {
									casketGrid[x][y-1].setDown(newc);
								}
								if(y<maxY && casketGrid[x][y+1]!=null) {
									casketGrid[x][y+1].setUp(newc);
								}
							}
						}
					}
				}
				
				// grid is as full as intended, but we want to eliminate caskets with only 1 connection
				boolean found = true;
				while(found) {
					Casket lonely = null;
					found = false;
					for(Casket c: l) {
						if(c.getConnectCount()==1) {
							found = true;
							lonely = c;
							break;
						}
					}
					if(found) {
						int x = Integer.MIN_VALUE;
						int y = Integer.MAX_VALUE;

						if(!lonely.hasLeft() && lonely.getX()>0) {
							x = lonely.getX()-1;
							y = lonely.getY();
						}
						else if(!lonely.hasUp() && lonely.getY()>0) {
							x = lonely.getX();
							y = lonely.getY()-1;
						}
						else if(!lonely.hasRight() && lonely.getX()<maxX) {
							x = lonely.getX()+1;
							y = lonely.getY();
						}
						else if(!lonely.hasDown() && lonely.getY()<maxY) {
							x = lonely.getX();
							y = lonely.getY()+1;
						}
						
						if(x>=0 && y>=0) {
							final Casket newc = new Casket(x,y);
							casketGrid[x][y] = newc;
							casketGrid[newc.getX()][newc.getY()] = newc;
							l.add(newc);
							if(x>0 && casketGrid[x-1][y]!=null) {
								casketGrid[x-1][y].setRight(newc);
							}
							if(x<maxX && casketGrid[x+1][y]!=null) {
								casketGrid[x+1][y].setLeft(newc);
							}
							if(y>0 && casketGrid[x][y-1]!=null) {
								casketGrid[x][y-1].setDown(newc);
							}
							if(y<maxY && casketGrid[x][y+1]!=null) {
								casketGrid[x][y+1].setUp(newc);
							}
						}
						else {
							// we did find a lonely casket, but it's not connectable!
							found = false;
						}
					}
				}
			}
			fireGridChanged();
		}
	}

	// TODO: maybe we should guarantee the order of events (theoretically not certain because of threads)
	private List<ICheeseCasketsGameListener> listeners = new ArrayList<ICheeseCasketsGameListener>();
	
	public final void addListener(final ICheeseCasketsGameListener listener) {
		synchronized(listeners) {
			if(listener!=null && !listeners.contains(listener)) {
				listeners.add(listener);
			}
		}		
	}
	public final void removeListener(final ICheeseCasketsGameListener listener) {
		synchronized(listeners) {
			if(listener!=null && listeners.contains(listener)) {
				listeners.remove(listener);
			}
		}		
	}
	
	public final void clearListeners() {
		synchronized(listeners) {
			listeners.clear();
		}		
	}

	private void fireGameStarted(final int currentPlayerP,final int numberOfPlayersP) {
		synchronized(listeners) {
			if(listeners.size()>0); {
				final List<ICheeseCasketsGameListener> tmpl = new  ArrayList<ICheeseCasketsGameListener>(listeners);
				final Thread t = new Thread(new Runnable(){
					@Override
					public void run() {
						for(ICheeseCasketsGameListener l: tmpl) {
							try {
								l.gameStarted(CheeseCaskets.this, currentPlayerP, numberOfPlayersP);
							}
							catch(RuntimeException rte) {
								rte.printStackTrace();
							}
						}
					}
				});
				t.start();
			}
		}
	}

	private void fireGameStopped() {
		synchronized(listeners) {
			if(listeners.size()>0); {
				final List<ICheeseCasketsGameListener> tmpl = new  ArrayList<ICheeseCasketsGameListener>(listeners);
				final Thread t = new Thread(new Runnable(){
					@Override
					public void run() {
						for(ICheeseCasketsGameListener l: tmpl) {
							try {
								l.gameStopped(CheeseCaskets.this);
							}
							catch(RuntimeException rte) {
								rte.printStackTrace();
							}
						}
					}
				});
				t.start();
			}
		}
	}

	private void fireGameEnded() {
		synchronized(listeners) {
			if(listeners.size()>0); {
				final List<ICheeseCasketsGameListener> tmpl = new  ArrayList<ICheeseCasketsGameListener>(listeners);
				final Thread t = new Thread(new Runnable(){
					@Override
					public void run() {
						for(ICheeseCasketsGameListener l: tmpl) {
							try {
								l.gameEnded(CheeseCaskets.this);
							}
							catch(RuntimeException rte) {
								rte.printStackTrace();
							}
						}
					}
				});
				t.start();
			}
		}
	}
	
	private void fireGridChanged() {
		synchronized(listeners) {
			if(listeners.size()>0); {
				final List<ICheeseCasketsGameListener> tmpl = new  ArrayList<ICheeseCasketsGameListener>(listeners);
				final Thread t = new Thread(new Runnable(){
					@Override
					public void run() {
						for(ICheeseCasketsGameListener l: tmpl) {
							try {
								l.gridChanged(CheeseCaskets.this);
							}
							catch(RuntimeException rte) {
								rte.printStackTrace();
							}
						}
					}
				});
				t.start();
			}
		}
	}

	private void fireMarkChanged(final Casket c) {
		synchronized(listeners) {
			if(listeners.size()>0); {
				final List<ICheeseCasketsGameListener> tmpl = new  ArrayList<ICheeseCasketsGameListener>(listeners);
				final Thread t = new Thread(new Runnable(){
					@Override
					public void run() {
						for(ICheeseCasketsGameListener l: tmpl) {
							try {
								l.markChanged(CheeseCaskets.this,c);
							}
							catch(RuntimeException rte) {
								rte.printStackTrace();
							}
						}
					}
				});
				t.start();
			}
		}
	}
	
	private void firePlayerCountChanged(final int playerP, final int count) {
		synchronized(listeners) {
			if(listeners.size()>0); {
				final List<ICheeseCasketsGameListener> tmpl = new  ArrayList<ICheeseCasketsGameListener>(listeners);
				final Thread t = new Thread(new Runnable(){
					@Override
					public void run() {
						for(ICheeseCasketsGameListener l: tmpl) {
							try {
								l.playerCountChanged(CheeseCaskets.this, playerP, count);
							}
							catch(RuntimeException rte) {
								rte.printStackTrace();
							}
						}
					}
				});
				t.start();
			}
		}
	}
	
	private void fireCurrentPlayerChanged(final int currentPlayerP) {
		synchronized(listeners) {
			if(listeners.size()>0); {
				final List<ICheeseCasketsGameListener> tmpl = new  ArrayList<ICheeseCasketsGameListener>(listeners);
				final Thread t = new Thread(new Runnable(){
					@Override
					public void run() {
						for(ICheeseCasketsGameListener l: tmpl) {
							try {
								l.currentPlayerChanged(CheeseCaskets.this, currentPlayerP);
							}
							catch(RuntimeException rte) {
								rte.printStackTrace();
							}
						}
					}
				});
				t.start();
			}
		}
	}
	
	/** how many moves can the current player make. */
	private int openMoves = 0;
	private static class Move {
		private final Casket casket;
		private final BORDER border;
		private final int player;
		private final int openMovesBefore;
		private final List<Casket> marked;
		public Move(final Casket c, final BORDER b, final int p, final int omb, final List<Casket> markedP) {
			casket          = c;
			border          = b;
			player          = p;
			openMovesBefore = omb;
			marked          = markedP!=null ? new ArrayList<Casket>(markedP) : new ArrayList<Casket>();
		}
	}
	
	private Stack<Move> history;
	
	public final int getHistorySize() {
		final Stack<Move> tmp = history;
		return tmp!=null ? tmp.size() : 0;
	}
	
	public final void startGame(final int firstPlayer) {
		synchronized(this) {
			if(getCasketCount()<2) {
				throw new RuntimeException("startGame(): no grid?");
			}
			if(currentPlayer==null) {
				currentPlayer = Integer.valueOf(firstPlayer>0 && firstPlayer<=numberOfPlayers ? firstPlayer : 1);
				fireGameStarted(currentPlayer.intValue(), numberOfPlayers);
				openMoves = 1;
				history = new Stack<Move>();
			}
		}
	}

	public final void stopGame() {
		synchronized(this) {
			if(currentPlayer!=null) {
				currentPlayer = null;
				openMoves = 0;
				history = null;
				fireGameStopped();
			}
		}
	}
	
	public final boolean isGameRunning() {
		return currentPlayer!=null;
	}
	
	public final void reportBorderEvent(final BORDER border, final Casket casket) {
		synchronized(this) {
			if(currentPlayer!=null && casket!=null) {
				final int p   = currentPlayer.intValue();
				final int cnt = getPlayerCount(p);
				List<Casket> marked = null;
				switch(border) {
					case LEFT:
						if(casket.hasLeft() && !casket.isMarkedLeft()) {
							marked = casket.markLeft(p);
						}
						break;
					case TOP:
						if(casket.hasUp() && !casket.isMarkedTop()) {
							marked = casket.markTop(p);
						}
						break;
					case RIGHT:
						if(casket.hasRight() && !casket.isMarkedRight()) {
							marked = casket.markRight(p);
						}
						break;
					case BOTTOM:
						if(casket.hasDown() && !casket.isMarkedBottom()) {
							marked = casket.markBottom(p);
						}
						break;
					default:
						break;
				}
				if(marked!=null && marked.size()>0) {
					final Move m = new Move(casket,border,p,openMoves,marked);
					history.push(m);

					final int newcnt = getPlayerCount(p);
					if(cnt>=newcnt) {
						openMoves--;
						if(openMoves<=0) {
							currentPlayer = p>=numberOfPlayers ? 1 : p+1;
							openMoves = 1;
							fireCurrentPlayerChanged(currentPlayer.intValue());
						}
					}
					else if(cnt < newcnt) {
						openMoves = newcnt - cnt;
						firePlayerCountChanged(p, newcnt);
					}
					
					for(Casket c: marked) {
						fireMarkChanged(c);
					}
					if(getFilledCount()==getCasketCount()) {
						fireGameEnded();
					}
				}
			}
		}
	}

	public final void back() {
		synchronized(this) {
			if(currentPlayer!=null && !history.empty()) {
				final Move m = history.pop();
				switch(m.border) {
					case LEFT:
						m.casket.markLeft(0);
						break;
					case TOP:
						m.casket.markTop(0);
						break;
					case RIGHT:
						m.casket.markRight(0);
						break;
					case BOTTOM:
						m.casket.markBottom(0);
						break;
					default:
						break;
				}
				currentPlayer = Integer.valueOf(m.player);
				openMoves     = m.openMovesBefore;
				final int cnt = getPlayerCount(m.player);
				fireCurrentPlayerChanged(currentPlayer.intValue());
				firePlayerCountChanged(m.player,cnt);
				
				for(Casket c: m.marked) {
					fireMarkChanged(c);
				}
			}
		}
	}
}
