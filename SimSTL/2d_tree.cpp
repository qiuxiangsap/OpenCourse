#ifndef __2D_TREE_H
#define __2D_TREE_H
#include <cmath>
#include <limits>

class Point {
public:
	Point(double x, double y): x(x), y(x) {}
	~Point(){}

	double getX(){
		return x;
	}

	void setX(double x) {
		this->x = x;
	}

	double getY() {
		return y;
	}
	void setY() {
		this->y = y;
	}

	double slope_to(Point *that) {
		if (that->getX() == this->x) {
			return std::numeric_limits<double>::min();
		}

		return (that->y - this->y) * 1.0 / (that->x - this->x) ;
	}

	double euclidean_dist(Point *that) {
		return std::sqrt( std::pow((this->x - that->getX()), 2)  + std::pow((this->y - that->getY()), 2) );
	}


private:
	double x, y;
};

class Rect {

public:
	Rect(const double&xmin,  const double& xmax, const double &ymin, const double &ymax):xmin(xmin),xmax(xmax),
		ymin(ymin), ymax(ymax){}
	~Rect(){}

	bool is_in(const Point &p) {

	}

	double getXMin() { return xmin; }
	double getXMax() { return xmax; }
	double getYMin() { return ymin; }
	double getYMax() { return ymax; }

	double setXMin(const double &xmin) { this->xmin = xmin; }
	double setXMax(const double &xmax) { this->xmax = xmax; }
	double setYMin(const double &ymin) { this->ymin = ymin; }
	double setYMax(const double &ymax) { this->ymax = ymax; }

private:
	double xmin, xmax, ymin, ymax;
};

struct Node {
	
};

// 2-D tree 
class KDTree {
public:
	KDTree();
	~KDTree();
private:

};

#endif   