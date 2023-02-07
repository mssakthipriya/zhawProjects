package tracker;

import static java.lang.Math.acos;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


class HerdVector {
    double x, y;

    HerdVector() {
    }

    HerdVector(double x, double y) {
    	set(x, y);
    }
    
    void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
    
	public HerdVector set(HerdVector pos) {
		x = pos.getX();
		y = pos.getY();
		return this;
	}

    void add(HerdVector v) {
        x += v.x;
        y += v.y;
    }

    void sub(HerdVector v) {
        x -= v.x;
        y -= v.y;
    }

    void div(double val) {
        x /= val;
        y /= val;
    }

    void mult(double val) {
        x *= val;
        y *= val;
    }

    double mag() {
        return sqrt((x*x) + (y*y));
    }

    double dot(HerdVector v) {
        return x * v.x + y * v.y;
    }

    void normalize() {
        double mag = mag();
        if (mag != 0) {
            x /= mag;
            y /= mag;
        }
    }

    void limit(double lim) {
        double mag = mag();
        if (mag != 0 && mag > lim) {
            x *= lim / mag;
            y *= lim / mag;
        }
    }
    

    
    public final double length() {
		return Math.sqrt(lengthSquared());
	}

    double heading() {
        return atan2(y, x);
    }
    

	public double lengthSquared() {
		return x * x + y * y;
	}


	public HerdVector translate(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}


	public HerdVector negate() {
		x = -x;
		y = -y;
		return this;
	}
	



	public HerdVector negate(HerdVector dest) {
		if (dest == null)
			dest = new HerdVector();
		dest.x = -x;
		dest.y = -y;
		return dest;
	}



	public HerdVector normalise(HerdVector dest) {
		double l = length();

		if (dest == null)
			dest = new HerdVector(x / l, y / l);
		else
			dest.set(x / l, y / l);

		return dest;
	}


	public static double dot(HerdVector left, HerdVector right) {
		return left.x * right.x + left.y * right.y;
	}




	public static double angle(HerdVector a, HerdVector b) {
		double dls = dot(a, b) / (a.length() * b.length());
		if (dls < -1f)
			dls = -1f;
		else if (dls > 1.0f)
			dls = 1.0f;
		return (double)Math.acos(dls);
	}


	public static HerdVector add(HerdVector left, HerdVector right, HerdVector dest) {
		if (dest == null)
			return new HerdVector(left.x + right.x, left.y + right.y);
		else {
			dest.set(left.x + right.x, left.y + right.y);
			return dest;
		}
	}


	public static HerdVector sub(HerdVector left, HerdVector right, HerdVector dest) {
		if (dest == null)
			return new HerdVector(left.x - right.x, left.y - right.y);
		else {
			dest.set(left.x - right.x, left.y - right.y);
			return dest;
		}
	}
	
    static HerdVector sub(HerdVector v, HerdVector v2) {
        return new HerdVector(v.x - v2.x, v.y - v2.y);
    }


	public HerdVector scale(double scale) {

		x *= scale;
		y *= scale;

		return this;
	}


	public String toString() {
		StringBuilder sb = new StringBuilder(64);

		sb.append("HerdVector[");
		sb.append(x);
		sb.append(", ");
		sb.append(y);
		sb.append(']');
		return sb.toString();
	}


	public final double getX() {
		return x;
	}


	public final double getY() {
		return y;
	}


	public final void setX(double x) {
		this.x = x;
	}


	public final void setY(double y) {
		this.y = y;
	}	
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		HerdVector other = (HerdVector)obj;
		
		if (x == other.x && y == other.y) return true;
		
		return false;
	}



    static double dist(HerdVector v, HerdVector v2) {
        return sqrt(pow(v.x - v2.x, 2) + pow(v.y - v2.y, 2));
    }

    static double angleBetween(HerdVector v, HerdVector v2) {
        return acos(v.dot(v2) / (v.mag() * v2.mag()));
    }


}