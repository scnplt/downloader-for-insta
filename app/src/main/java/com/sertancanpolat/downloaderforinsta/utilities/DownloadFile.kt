package com.sertancanpolat.downloaderforinsta.utilities

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import com.sertancanpolat.downloaderforinsta.R
import kotlinx.android.synthetic.main.custom_alert_dialog.view.*

fun downloadFile(context: Context, url: String, isVideo: Boolean) {
    val download: (AlertDialog) -> Unit = {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Downloader For Insta")
        request.setDescription("Post is downloading...")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "DFI_${System.currentTimeMillis()}." + if (isVideo) "mp4" else "jpeg"
        )
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        it.cancel()
    }

    val dialog = AlertDialog.Builder(context).create()
    val view = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)
    view.cad_button_positive.setOnClickListener { download(dialog) }
    view.cad_button_negative.setOnClickListener { dialog.cancel() }
    dialog.setView(view)
    dialog.setCancelable(true)
    dialog.show()
}