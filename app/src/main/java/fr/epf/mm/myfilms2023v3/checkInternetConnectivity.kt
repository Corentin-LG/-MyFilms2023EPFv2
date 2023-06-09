package fr.epf.mm.myfilms2023v3

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

object InternetConnectivityChecker {

    fun checkInternetConnectivity(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected

        if (!isConnected) {
            showEnableInternetDialog(context)
        }
    }

    private fun showEnableInternetDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Activer Internet")
            .setMessage("Pour utiliser cette fonctionnalité, veuillez activer votre connexion Internet.")
            .setPositiveButton("Données Mobiles") { dialog: DialogInterface, _: Int ->
                //context.startActivity(Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)) //Ne permet pas sur Galaxy M32 d'être sur le bon paramètre
                context.startActivity(Intent(Settings.ACTION_DATA_USAGE_SETTINGS))
                dialog.dismiss()
            }
            .setNegativeButton("Wi-Fi") { dialog: DialogInterface, _: Int ->
                context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                dialog.dismiss()
            }
            .setNeutralButton("Annuler") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}
