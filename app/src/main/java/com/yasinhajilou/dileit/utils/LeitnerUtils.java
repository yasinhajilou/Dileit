package com.yasinhajilou.dileit.utils;

import com.yasinhajilou.dileit.constant.LeitnerStateConstant;
import com.yasinhajilou.dileit.model.entity.Leitner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeitnerUtils {

    //get today card that are ready for reviewing
    public static List<Leitner> getPreparedLeitnerItems(List<Leitner> leitnerList){
        List<Leitner> filteredList = new ArrayList<>();
        // Cache current time outside loop to avoid repeated system calls
        long currentTime = System.currentTimeMillis();
        int size = leitnerList.size();
        
        for (int i = size - 1; i >= 0; i--) {
            Leitner currentItem = leitnerList.get(i);
            int currentState = currentItem.getState();

            if (currentState == LeitnerStateConstant.BOX_ONE || currentState == LeitnerStateConstant.STARTED){
                filteredList.add(currentItem);
            }else{
                long startTime = currentItem.getLastReviewTime();
                long spaceTime = TimeUtils.getDaysBetweenTimestamps(startTime, currentTime);

                if (currentState == LeitnerStateConstant.BOX_TWO && spaceTime>=2){
                    filteredList.add(currentItem);
                }else if (currentState == LeitnerStateConstant.BOX_THREE && spaceTime>=4){
                    filteredList.add(currentItem);
                }else if (currentState == LeitnerStateConstant.BOX_FOUR && spaceTime>=9){
                    filteredList.add(currentItem);
                }else if (currentState == LeitnerStateConstant.BOX_FIVE && spaceTime>=14){
                    filteredList.add(currentItem);
                }
            }
        }
        Collections.sort(filteredList);
        return filteredList;
    }

    public static int nextBoxFinder(int currentState){
        switch (currentState){
            case LeitnerStateConstant.STARTED:
            case LeitnerStateConstant.BOX_ONE:
                return LeitnerStateConstant.BOX_TWO;
            case LeitnerStateConstant.BOX_TWO:
                return LeitnerStateConstant.BOX_THREE;
            case LeitnerStateConstant.BOX_THREE:
                return LeitnerStateConstant.BOX_FOUR;
            case LeitnerStateConstant.BOX_FOUR:
                return LeitnerStateConstant.BOX_FIVE;
            case LeitnerStateConstant.BOX_FIVE:
                return LeitnerStateConstant.LEARNED;
            default:
                return -1;
        }
    }
}