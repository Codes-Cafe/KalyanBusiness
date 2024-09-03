package com.app.kalyanbusiness.baseapp.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.app.kalyanbusiness.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DialogUtils {

    public static void warningDialog(Context ctx, String title, String message,
                                     Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText,
                                     SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE);

        dialog.setTitleText("\n" + title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();
    }

    public static void succesDialog(Context ctx, String title, String message, Boolean Cancellable,
                                    Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText,
                                    SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);

        dialog.setTitleText("\n" + title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void errorDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);

        dialog.setTitleText("\n" + title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void normalDialog(Context ctx, String title, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx);

        dialog.setTitleText("\n" + title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void processDialog(Context ctx, String title, String message, Boolean Cancellable, SweetAlertDialog dialog) {

        dialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);

        dialog.getProgressHelper().setBarColor(ctx.getResources().getColor(R.color.colorPrimary));
        dialog.setTitleText(title);
        if (message != null) {
            dialog.setContentText(message + "\n");
        }
        dialog.setCancelable(Cancellable);

        dialog.show();

    }

    public static void editTextDialog(Context ctx, EditText editText, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE);

        dialog.setTitleText("\n" + message + "\n");
        dialog.setCustomView(editText);
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

    public static void customDialog(Context ctx, View customView, String message, Boolean Cancellable, Boolean ShowCancelButton, String cancelBtnText, String confirmBtnText, SweetAlertDialog.OnSweetClickListener ButtonClicklistener) {

        SweetAlertDialog dialog = new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE);

        dialog.setTitleText("\n" + message + "\n");
        dialog.setCustomView(customView);
        dialog.setConfirmText(confirmBtnText);
        dialog.setCancelable(Cancellable);

        if (ShowCancelButton) {

            dialog.setCancelButton(cancelBtnText, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });

        }

        if (ButtonClicklistener != null) {

            dialog.setConfirmClickListener(ButtonClicklistener);

        } else {

            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

        dialog.show();

    }

}
