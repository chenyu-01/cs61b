import java.lang.reflect.Array;

public class NBody {
    public static double readRadius(String filename) {
        In in = new In(filename);
        in.readInt();// read the first number and go on
        double radius = in.readDouble(); 
        return radius;
    }
    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int num = in.readInt();// read the number of planets n
        double radius = in.readDouble();
        Planet[] planets = new Planet[num];
        for (int i = 0; i < num ; i++) {
            double planets_xxPos = in.readDouble();
            double planets_yyPos = in.readDouble();
            double planets_xxVel = in.readDouble();
            double planets_yyVel = in.readDouble();
            double planets_mass = in.readDouble();
            String planets_imgFileName = in.readString();
            planets[i] = new Planet(planets_xxPos,planets_yyPos,planets_xxVel,planets_yyVel,planets_mass,planets_imgFileName);
        }
        return planets;
    }
    
    /**
     * Collecting All Needed Input
     * Drawing the Background
     * Drawing All of the Planets
     * Creating an Animation

     */
    public static void main(String args[]) {
        double T = Double.valueOf(args[0]);
        double dt = Double.valueOf(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        int num = planets.length;
        double radius = readRadius(filename);

        /** Sets up the universe so it goes from 
		  * -100, -100 up to 100, 100 */
		StdDraw.setScale(-radius, radius);
        StdDraw.enableDoubleBuffering();

		/* Clears the drawing window. */
		StdDraw.clear();    
        /* Stamps image as background. */
        StdDraw.picture(0, 0, "./images/starfield.jpg");
        // Create a time variable and set it to 0. Set up a loop to loop until this time variable is T.
        double t = 0;
        while (t < T) {
            Double[] xForces = new Double[num];
            Double[] yForces = new Double[num];
            // calc the force
            for (int i = 0; i < num ; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
                
            }
            // update the pos
            for (int i = 0; i < num ; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            // draw it together
            StdDraw.picture(0, 0, "./images/starfield.jpg");
            for (Planet planet : planets){
                planet.draw();
            }
            StdDraw.show();
            //Pause the animation for 10 milliseconds (see the pause method of StdDraw). You may need to tweak this on your computer.
            StdDraw.pause(12);
            t = t + dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                        planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
        

        
    }
}