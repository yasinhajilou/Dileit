package com.example.dileit.utils;

import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.model.entity.Leitner;

import java.util.ArrayList;
import java.util.List;

public class LeitnerFilterUtils {
    public List<Leitner> getPreparedLeitnerItems(List<Leitner> leitnerList){
        List<Leitner> filteredList = new ArrayList<>();
        for (int i = leitnerList.size() ; i > 0 ; i--) {

            Leitner currentItem = leitnerList.get(i-1);
            int currentState = currentItem.getState();

            if (currentState == LeitnerStateConstant.BOX_ONE){
                filteredList.add(currentItem);
            }else{
                long startTime = currentItem.getLastReviewTime();
                long endTime = System.currentTimeMillis();
                long spaceTime = TimeUtils.getDaysBetweenTimestamps(startTime , endTime);

                if (currentState == LeitnerStateConstant.BOX_TWO && spaceTime>=2){

                    filteredList.add(currentItem);
                }else {

                    if (currentState == LeitnerStateConstant.BOX_THREE && spaceTime>=4){
                        filteredList.add(currentItem);
                    }else {
                        if (currentState == LeitnerStateConstant.BOX_FOUR && spaceTime>=9){
                            filteredList.add(currentItem);
                        }else {
                            if (currentState == LeitnerStateConstant.BOX_FIVE && spaceTime>=14){
                                filteredList.add(currentItem);
                            }
                        }
                    }
                }
            }
        }

        return filteredList;
    }
}
