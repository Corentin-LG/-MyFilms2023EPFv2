package fr.epf.mm.myfilms2023v3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class QRCodeScannerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(true)
        integrator.setCaptureActivity(CustomCaptureActivity::class.java)
        integrator.setPrompt("Faites face verticalement à un QR code ou à un Bar code bien net")
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val scannedData = result.contents
                Log.d("Erreurscan", "$scannedData")
                val intent = Intent(this, DetailsFilmActivity::class.java)
                intent.putExtra("scannedData", scannedData)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
