import java.util.Timer;

/**
 * Created by akamikang on 2017. 7. 18..
 */
public class ResourceMeter {

    public static void main(String[] args) {
        GetResource getResource = new GetResource();


        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(getResource, 1000, 1000);

        /*try {
            Thread.sleep(20000);
        } catch (InterruptedException ex) {
            //
        }
        jobScheduler.cancel();*/

    }
}
