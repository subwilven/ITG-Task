package com.islam.basepropject.project_base.utils

import android.content.Context
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.islam.basepropject.project_base.base.POJO.Message

object DialogManager {


    private fun buildDialog(context: Context,
                            @StringRes title: Int,
                            @StringRes positiveButton: Int,
                            @StringRes negativeButton: Int,
                            cancelable: Boolean = true,
                            cancelOnTouchOutside: Boolean = true,
                            onPositiveClick: (() -> Unit)?,
                            onNegativelick: (() -> Unit)?): MaterialDialog {
        var dialog = MaterialDialog(context)
                .title(title)

                .cancelOnTouchOutside(cancelOnTouchOutside)
                .cancelable(cancelable)
        if (positiveButton != -1)
            dialog.positiveButton(positiveButton) { onPositiveClick?.invoke() }

        if (negativeButton != -1)
            dialog.negativeButton(negativeButton) { onNegativelick?.invoke() }

        return dialog
    }

    fun showDialog(context: Context,
                   @StringRes title: Int,
                   message: Message,
                   @StringRes positiveButton: Int = -1,
                   @StringRes negativeButton: Int = -1,
                   cancelable: Boolean = true,
                   cancelOnTouchOutside: Boolean = true,
                   onPositiveClick: (() -> Unit)? = null,
                   onNegativelick: (() -> Unit)? = null) {

        val dialog = buildDialog(context, title, positiveButton, negativeButton, cancelable, cancelOnTouchOutside, onPositiveClick, onNegativelick)
        dialog.message(text = message.getValue(context))

        dialog.show()
    }

    fun showDialogList(context: Context,
                       @StringRes title: Int,
                       @StringRes positiveButton: Int = -1,
                       @StringRes negativeButton: Int = -1,
                       cancelable: Boolean = true,
                       cancelOnTouchOutside: Boolean = true,
                       onPositiveClick: (() -> Unit)? = null,
                       onNegativelick: (() -> Unit)? = null,
                       initialSelection: Int = -1,
                       initialSelectionArray: IntArray = IntArray(0),
                       items: List<String>? = null,
                       onSingleChoiceClicked: ((dialog: MaterialDialog, index: Int, text: String) -> Unit)? = null,
                       onMultiChoiceClicked: ((dialog: MaterialDialog, indices: IntArray, items: List<String>) -> Unit)? = null) {

        var dialog = buildDialog(context, title, positiveButton, negativeButton, cancelable, cancelOnTouchOutside, onPositiveClick, onNegativelick)
        onSingleChoiceClicked?.let {
            dialog = dialog.listItemsSingleChoice(
                    items = items,
                    initialSelection = initialSelection,
                    selection = onSingleChoiceClicked)
        }

        onMultiChoiceClicked?.let {
            dialog = dialog.listItemsMultiChoice(
                    items = items,
                    allowEmptySelection = false,
                    initialSelection = initialSelectionArray,
                    selection = onMultiChoiceClicked)
        }

        dialog.show()
    }
}