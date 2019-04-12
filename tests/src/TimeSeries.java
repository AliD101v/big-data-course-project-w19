public class TimeSeries {
    private long time;
    private double value;

    public TimeSeries()
    {
        time = 0;
        value = 0.0;
    }

    public TimeSeries(long tVal, double vVal)
    {
        setTime(tVal);
        setValue(vVal);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public long getTime() {
        return time;
    }
}
