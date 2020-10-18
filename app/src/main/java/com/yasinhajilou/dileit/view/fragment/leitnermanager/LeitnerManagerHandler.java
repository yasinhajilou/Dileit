package com.yasinhajilou.dileit.view.fragment.leitnermanager;

import androidx.fragment.app.Fragment;

import com.yasinhajilou.dileit.constant.LeitnerModifierConstants;
import com.yasinhajilou.dileit.model.entity.Leitner;
import com.yasinhajilou.dileit.view.fragment.leitnercardhandler.LeitnerCardModifierBottomSheet;

public class LeitnerManagerHandler {
    static void edit(Fragment fragment , Leitner leitner){
        LeitnerCardModifierBottomSheet bottomSheet = LeitnerCardModifierBottomSheet.onNewInstance(LeitnerModifierConstants.EDIT , leitner.getId());
        bottomSheet.show(fragment.getChildFragmentManager() , "fragment_edit");
    }

}
