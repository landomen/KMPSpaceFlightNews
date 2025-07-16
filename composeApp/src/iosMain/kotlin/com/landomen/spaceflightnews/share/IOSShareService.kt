package com.landomen.spaceflightnews.share

import kotlinx.cinterop.BetaInteropApi
import platform.Foundation.NSString
import platform.Foundation.create
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

class IOSShareService: ShareService {
    @OptIn(BetaInteropApi::class)
    override fun share(title: String, url: String) {
        val activityItems = listOf(
            NSString.create(string = "$title\n\nRead more: $url")
        )
        val activityViewController =
            UIActivityViewController(activityItems = activityItems, applicationActivities = null)

        // Get the top-most view controller to present the activity view controller
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.presentViewController(activityViewController, animated = true, completion = null)
    }
}