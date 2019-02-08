package news.app.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;

import news.app.R;

public class CommonMethods {

    private static String TAG = "CommonMethods";

    /**
     * This method is used to check network availability.
     *
     * @param context
     * @return
     */
    public static boolean isInternetConnected(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


    /**
     * @param context
     * @param title
     * @param message this method is used for showAlertDialog
     */
    public static void showAlertDialog(Context context, String title, String message) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
        // Create the AlertDialog object and return it
        final AlertDialog alertDialog = builder.create();
        if (title == null) {
            builder.setTitle(null);
            builder.setIcon(0);
        } else {
            // Setting Dialog Title
            alertDialog.setTitle(title);
        }
        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
        alertDialog.getButton(Dialog.BUTTON_POSITIVE).setAllCaps(false);
    }


}
