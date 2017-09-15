package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Helpers;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

import com.wang.avi.AVLoadingIndicatorView;

import cloud.safe.com.kuchmynda.mark.safecloud.R;

public final class DialogInitializator {
    public static void initializeLoadingDialog(Dialog dialog) {
        dialog.setContentView(R.layout.loading_window);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(dialog.getContext().getResources().getColor(android.R.color.transparent)));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        AVLoadingIndicatorView indicatorView = (AVLoadingIndicatorView) dialog.findViewById(R.id.indicator);
        indicatorView.smoothToShow();
    }
}

