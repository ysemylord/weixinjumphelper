package com.plugin.xuyabo.weixingameplugin;

import org.junit.Test;

import java.util.Set;

import static com.plugin.xuyabo.weixingameplugin.DistanceToTime.mCollecData;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testHashKeySet() throws Exception {
        Set<Integer> distanceSet = mCollecData.keySet();
        for (Integer distanceKey : distanceSet) {
            System.out.println(distanceKey);
        }

    }

    @Test
    public void testDistanceAndTime(){
       int time= DistanceToTime.calculateTime(510);
        System.out.println(time);
    }

}