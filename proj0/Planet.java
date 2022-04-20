import java.lang.Math;

public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    // 1st
    public double calcDistance(Planet b) {
        double y_distance;
        double x_distance;
        x_distance = Math.abs(this.xxPos - b.xxPos);
        y_distance = Math.abs(this.yyPos - b.yyPos);
        return Math.sqrt(Math.pow(x_distance, 2) + Math.pow(y_distance, 2));
    }

    // 2nd
    public double calcForceExertedBy(Planet b) {
        double r, m1, m2;
        r = this.calcDistance(b);
        m1 = this.mass;
        m2 = b.mass;
        double result = G * m1 * m2 / r / r;
        return result;
    }

    // 3rd
    public double calcForceExertedByX(Planet b) {
        double x_distance = b.xxPos - this.xxPos;
        double r = this.calcDistance(b);
        double F = this.calcForceExertedBy(b);
        double result = F * x_distance / r;
        return result;
    }

    public double calcForceExertedByY(Planet b) {
        double y_distance = b.yyPos - this.yyPos;
        double r = this.calcDistance(b);
        double F = this.calcForceExertedBy(b);
        double result = F * y_distance / r;
        return result;
    }

    // 4th
    public double calcNetForceExertedByX(Planet[] planets) {
        double sum = 0;
        for (int i = 0; i < planets.length; i++) {
            if (this.equals(planets[i])) {
                continue;
            }
            sum = sum + this.calcForceExertedByX(planets[i]);
        }
        return sum;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double sum = 0;
        for (int i = 0; i < planets.length; i++) {
            if (this.equals(planets[i])) {
                continue;
            }
            sum = sum + this.calcForceExertedByY(planets[i]);
        }
        return sum;
    }

    // 5th
    public void update(double dt, double fx, double fy) {
        // 1. Calculate the acceleration using the provided x- and y-forces.
        double ax = fx / mass;
        double ay = fy / mass;
        // 2. Calculate the new velocity by using the acceleration and current velocity.
        // Recall that acceleration describes the change in velocity per unit time, so
        // the new velocity is (vx+dt⋅ax,vy+dt⋅ay).
        xxVel = xxVel + ax * dt;
        yyVel = yyVel + ay * dt;

        // 3. Calculate the new position by using the velocity computed in step 2 and
        // the current position. The new position is (px+dt⋅vx,py+dt⋅vy).
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }
    // draw the Planet’s image at the Planet’s position
    public void draw(){
        String fullpath = "./images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, fullpath);
    }
}
