package com.example.dileit.view.fragment.leitnermanager;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.dileit.constant.LeitnerModifierConstants;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.fragment.leitnercardhandler.LeitnerCardModifierBottomSheet;

public class LeitnerManagerHandler {
    static void edit(Fragment fragment , Leitner leitner){
        LeitnerCardModifierBottomSheet bottomSheet = LeitnerCardModifierBottomSheet.onNewInstance(LeitnerModifierConstants.EDIT , leitner.getId());
        bottomSheet.show(fragment.getChildFragmentManager() , "fragment_edit");
    }

    static void delete(){

    }
}
