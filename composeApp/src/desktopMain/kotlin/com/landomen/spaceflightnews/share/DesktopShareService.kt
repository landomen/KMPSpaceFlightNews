package com.landomen.spaceflightnews.share

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.JLabel
import javax.swing.JWindow
import javax.swing.SwingConstants
import javax.swing.Timer

class DesktopShareService : ShareService {
    override fun share(title: String, url: String) {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val selection = StringSelection("$title\n\nRead more: $url")
        clipboard.setContents(selection, selection)

        showToastMessage("Copied to clipboard")
    }

    fun showToastMessage(message: String, durationMillis: Int = 5000) {
        val window = JWindow()

        val label = JLabel(message, SwingConstants.CENTER).apply {
            foreground = Color.WHITE
            background = Color(0, 0, 0, 200)
            isOpaque = true
            font = Font("SansSerif", Font.PLAIN, 14)
            preferredSize = Dimension(300, 40)
        }

        window.contentPane.add(label)
        window.pack()

        // Position at bottom center of screen
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val x = (screenSize.width - window.width) / 2
        val y = screenSize.height - window.height - 100
        window.setLocation(x, y)

        window.isAlwaysOnTop = true
        window.isVisible = true

        // Auto-hide after timeout
        Timer(durationMillis) {
            window.isVisible = false
            window.dispose()
        }.start()
    }
}