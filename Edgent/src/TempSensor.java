import java.util.Random;

import org.apache.edgent.function.Supplier;

public class TempSensor implements Supplier<Double> {
    double currentTemp = 65.0;
    Random rand;

    TempSensor(){
        rand = new Random();
    }
    	
    //Returns a new temperature reading randomly, this temp is based off of old temp added to a random modifier
    @Override
    public Double get() {
        // Change the current temperature some random amount
        double newTemp = rand.nextGaussian() + currentTemp;
        currentTemp = newTemp;
        return currentTemp;
    }
}
