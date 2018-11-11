package de.schini.cheesecaskets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represent a single "casket" in a platform- independent way.
 * <p />
 * The abstract grid, in which these caskets reside, has to be visualized as being
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
public final class Casket implements Serializable {
	/** Value that defines an unmarked edge. */
	public static final int UNMARKED = 0;

	private Casket left         = null;
	private Casket up           = null;
	private Casket right        = null;
	private Casket down         = null;
	private int markLeft        = UNMARKED;
	private int markTop         = UNMARKED;
	private int markRight       = UNMARKED;
	private int markBottom      = UNMARKED;
	private int lastMark        = UNMARKED;

	private final int x;
	private final int y;

	/**
	 * Constructor, that allows to set all properties.
	 * 
	 * @param xP
	 * @param yP
	 * @param markLeftP
	 * @param markTopP
	 * @param markRightP
	 * @param markBottomP
	 * @param lastMarkP
	 * @param leftP
	 * @param upP
	 * @param rightP
	 * @param downP
	 */
	public Casket(final int xP
				,final int yP
				,final int markLeftP
				,final int markTopP
				,final int markRightP
				,final int markBottomP
				,final int lastMarkP
				,final Casket leftP
				,final Casket upP
				,final Casket rightP
				,final Casket downP) {
		super();
		if(xP<0) {
			throw new RuntimeException("<init>: xP<0!");
		}
		if(yP<0) {
			throw new RuntimeException("<init>: yP<0!");
		}
		x          = xP;
		y          = yP;
		markLeft   = markLeftP;
		markTop    = markTopP;
		markRight  = markRightP;
		markBottom = markBottomP;
		lastMark   = lastMarkP;
		left       = leftP;
		up         = upP;
		right      = rightP;
		down       = downP;
	}

	/**
	 * Constructor, that allows to set all properties except the neighbors.
	 * 
	 * @param xP
	 * @param yP
	 * @param markLeftP
	 * @param markTopP
	 * @param markRightP
	 * @param markBottomP
	 * @param lastMarkP
	 */
	public Casket(final int xP
				,final int yP
				,final int markLeftP
				,final int markTopP
				,final int markRightP
				,final int markBottomP
				,final int lastMarkP) {
		this(xP,yP,markLeftP,markTopP,markRightP,markBottomP,lastMarkP,null,null,null,null);
	}

	/**
	 * Constructor, that only sets the coordinates.
	 * 
	 * @param xP
	 * @param yP
	 */
	public Casket(final int xP
				,final int yP) {
		this(xP,yP,UNMARKED,UNMARKED,UNMARKED,UNMARKED,UNMARKED);
	}

	
	public final int getX() {
		return x;
	}
	public final int getY() {
		return y;
	}
	
	public final void setLeft(final Casket casket) {
		synchronized(this) {
			if(casket==null) {
				throw new RuntimeException("setLeft: <null>!");
			}
			else if(left!=null && left!=casket) {
				throw new RuntimeException("setLeft: allready set!");
			}
			left = casket;
			casket.right = this;
		}
	}
	public final void setUp(final Casket casket) {
		synchronized(this) {
			if(casket==null) {
				throw new RuntimeException("setUp: <null>!");
			}
			else if(up!=null && up!=casket) {
				throw new RuntimeException("setUp: allready set!");
			}
			up = casket;
			casket.down = this;
		}
	}
	public final void setRight(final Casket casket) {
		synchronized(this) {
			if(casket==null) {
				throw new RuntimeException("setRight: <null>!");
			}
			else if(right!=null && right!=casket) {
				throw new RuntimeException("setRight: allready set!");
			}
			right = casket;
			casket.left = this;
		}
	}
	public final void setDown(final Casket casket) {
		synchronized(this) {
			if(casket==null) {
				throw new RuntimeException("setDown: <null>!");
			}
			else if(down!=null && down!=casket) {
				throw new RuntimeException("setDown: allready set!");
			}
			down = casket;
			casket.up = this;
		}
	}
	public final boolean hasLeft() {
		return left!=null;
	}
	public final boolean isMarkedLeft() {
		return markLeft!=UNMARKED;
	}
	public final List<Casket> markLeft(final int b) {
		synchronized(this) {
			final List<Casket> r = new ArrayList<Casket>();
			if(left!=null && markLeft!=b) {
				markLeft = b;
				lastMark = b;
				r.add(this);
				r.addAll(left.markRight(b));
			}
			return r;
		}
	}
	public final boolean hasUp() {
		return up!=null;
	}
	public final boolean isMarkedTop() {
		return markTop!=0;
	}
	public final List<Casket> markTop(final int b) {
		synchronized(this) {
			final List<Casket> r = new ArrayList<Casket>();
			if(up!=null && markTop!=b) {
				markTop = b;
				lastMark = b;
				r.add(this);
				r.addAll(up.markBottom(b));
			}
			return r;
		}
	}
	public final boolean hasRight() {
		return right!=null;
	}
	public final boolean isMarkedRight() {
		return markRight!=0;
	}
	public final List<Casket> markRight(final int b) {
		synchronized(this) {
			final List<Casket> r = new ArrayList<Casket>();
			if(right!=null && markRight!=b) {
				markRight = b;
				lastMark = b;
				r.add(this);
				r.addAll(right.markLeft(b));
			}
			return r;
		}
	}
	public final boolean hasDown() {
		return down!=null;
	}
	public final boolean isMarkedBottom() {
		return markBottom!=0;
	}
	public final List<Casket> markBottom(final int b) {
		synchronized(this) {
			final List<Casket> r = new ArrayList<Casket>();
			if(down!=null && markBottom!=b) {
				markBottom = b;
				lastMark = b;
				r.add(this);
				r.addAll(down.markTop(b));
			}
			return r;
		}
	}
	
	public final int getMarkLeft() {
		return markLeft;
	}

	public final int getMarkTop() {
		return markTop;
	}

	public final int getMarkRight() {
		return markRight;
	}

	public final int getMarkBottom() {
		return markBottom;
	}
	
	public final int getLastMark() {
		return lastMark;
	}
	public final boolean isFull() {
		return ( markLeft!=0   || left==null ) 
			&& ( markTop!=0    || up==null )
			&& ( markRight!=0  || right==null )
			&& ( markBottom!=0 || down==null ); 
	}

	public final int getMarkCount() {
		return (markLeft!=0 ? 1 : 0)
			+  (markTop!=0 ? 1 : 0)
			+  (markRight!=0 ? 1 : 0)
			+  (markBottom!=0 ? 1 : 0);
	}
	
	public final int getConnectCount() {
		return (left!=null ? 1 : 0)
			+  (up!=null ? 1 : 0)
			+  (right!=null ? 1 : 0)
			+  (down!=null ? 1 : 0);
	}
	

	@Override
	public final String toString() {
		return "Casket[(x;y)=(" + x + ";" + y + ")"
			+ ",markLeft=" + markLeft
			+ ",markTop=" + markTop
			+ ",markRight=" + markRight
			+ ",markBottom=" + markBottom
			+ ",lastMark=" + lastMark
			+ " => FULL?: " + isFull() + "]";
	}
}
