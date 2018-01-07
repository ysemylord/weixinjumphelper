package com.plugin.xuyabo.weixingameplugin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by xuyabo on 2017/12/31.
 * 根据两点间的距离得出需要按压的时间（时间单位ms）
 */

public class DistanceToTime {
    public static LinkedHashMap<Integer, Integer> mCollecData = new LinkedHashMap<>();

    static {
        mCollecData.put(95,  100);
        mCollecData.put(200, 194);
        mCollecData.put(230, 330);
        mCollecData.put(240, 350);
        mCollecData.put(250, 365);
        mCollecData.put(265, 400);
        mCollecData.put(300, 460);
        mCollecData.put(330, 500);
        mCollecData.put(350, 500);
        mCollecData.put(400, 520);
        mCollecData.put(423, 535);
        mCollecData.put(440, 550);
        mCollecData.put(450, 570);
        mCollecData.put(470, 600);
        mCollecData.put(480, 650);
        mCollecData.put(507, 700);
        mCollecData.put(535, 710);
        mCollecData.put(550, 715);
        mCollecData.put(556, 725);
        mCollecData.put(570, 735);
        mCollecData.put(577, 750);
        mCollecData.put(587, 800);
        mCollecData.put(600, 810);
        mCollecData.put(625, 820);
        mCollecData.put(630, 825);
        mCollecData.put(640, 830);
        mCollecData.put(645, 835);
        mCollecData.put(650, 840);
        mCollecData.put(680, 850);
        mCollecData.put(700, 870);

    }

    /**
     * 根据距离计算出按下时间
     * @param distance
     * @return
     */
    public  static int calculateTime(int distance) {
        Integer needTime = mCollecData.get(distance);
        if(needTime!=null){
            return needTime;
        }
        int fisrtDistance=0;
        int lastDistance=0;
        int index=0;
        for (Integer distanceKey : mCollecData.keySet()) {
            if(index==0){
                fisrtDistance=distanceKey;
            }
            if(index==mCollecData.size()-1){
                lastDistance=distanceKey;
            }
            index++;
        }
        if(distance<fisrtDistance){
            return mCollecData.get(fisrtDistance);
        }else if(distance>lastDistance){
            return mCollecData.get(lastDistance);
        }else{
            Integer beforeTime=0;
            Integer afterTime=0;
            int beforeIndex=distance-1;
            int afterIndex=distance+1;
            while ((beforeTime=mCollecData.get(beforeIndex))==null){
                beforeIndex--;
            }

            while ((afterTime=mCollecData.get(afterIndex))==null){
                afterIndex++;
            }
            return (beforeTime+afterTime)/2;//大概取个中间值
        }

    }
}
