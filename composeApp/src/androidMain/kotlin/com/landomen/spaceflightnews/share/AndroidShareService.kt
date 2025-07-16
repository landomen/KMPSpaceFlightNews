package com.landomen.spaceflightnews.share

import android.content.Context
import android.content.Intent
import com.landomen.spaceflightnews.R

class AndroidShareService(private val context: Context) : ShareService {

    override fun share(title: String, url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, constructMessage(title = title, url = url))
        }

        context.startActivity(
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.share_title)
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
    }

    private fun constructMessage(title: String, url: String): String {
        return context.getString(R.string.share_message, title, url)
    }
}