package com.example.dileit.view.fragment.leitnermanager;

import android.app.AlertDialog;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.dileit.constant.LeitnerModifierConstants;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.adapter.recycler.LeitnerManagerRecyclerAdapter;
import com.example.dileit.view.fragment.leitnercardhandler.LeitnerCardModifierBottomSheet;
import com.example.dileit.viewmodel.InternalViewModel;

public class LeitnerManagerHandler {
    static void edit(Fragment fragment , Leitner leitner){
        LeitnerCardModifierBottomSheet bottomSheet = LeitnerCardModifierBottomSheet.onNewInstance(LeitnerModifierConstants.EDIT , leitner.getId());
        bottomSheet.show(fragment.getChildFragmentManager() , "fragment_edit");
    }

    static void showDeleteMessage(Context context , InternalViewModel internalViewModel , Leitner leitner){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Delete Leitner Card")
                .setMessage("Do you want to delete this leitner card?")
                .setNeutralButton("Yes" , (dialogInterface, i) -> {
                    internalViewModel.deleteLeitnerItem(leitner);
                })
                .setNegativeButton("No" , (dialogInterface, i) -> {
                   dialogInterface.dismiss();
                });
        builder.show();
    }
}
