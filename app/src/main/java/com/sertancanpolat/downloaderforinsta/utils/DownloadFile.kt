package com.sertancanpolat.downloaderforinsta.utils

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import com.sertancanpolat.downloaderforinsta.databinding.CustomAlertDialogBinding

fun downloadFile(context: Context, url: String, isVideo: Boolean) {

    val dialog = AlertDialog.Builder(context).create()
    val inflater = LayoutInflater.from(context)
    val binding = CustomAlertDialogBinding.inflate(inflater)
    val path = "DFI_${System.currentTimeMillis()}." + if (isVideo) "mp4" else "jpeg"

    binding.btnNegative.setOnClickListener { dialog.cancel() }
    binding.btnPositive.setOnClickListener {
        val request = DownloadManager.Request(Uri.parse(url)).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle("Downloader For Insta")
            setDescription("Post is downloading...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, path)
        }

        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        dialog.cancel()
    }

    dialog.setView(binding.root)
    dialog.setCancelable(true)
    dialog.show()
}
