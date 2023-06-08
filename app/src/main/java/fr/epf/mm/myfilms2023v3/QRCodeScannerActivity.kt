package fr.epf.mm.myfilms2023v3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult


class QRCodeScannerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialiser le scanner
        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(true) // Verrouillez l'orientation en mode portrait
        integrator.setCaptureActivity(CustomCaptureActivity::class.java) // Utilisez CustomCaptureActivity
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Récupérer les résultats du scanner
        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val scannedData = result.contents
                Log.d("Erreurscan", "$scannedData")
                // Faites quelque chose avec les données scannées (par exemple, les transmettre à une autre activité)
                val intent = Intent(this, DetailsFilmActivity::class.java)
                intent.putExtra("scannedData", scannedData)
                startActivity(intent)
            } else {
                // Aucune donnée scannée
                Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
