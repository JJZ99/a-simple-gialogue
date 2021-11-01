package com.example.abcdialogue.Weibo

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.provider.Settings.System.FONT_SCALE
import android.view.WindowManager
import androidx.core.content.ContextCompat.getSystemService

class SystemObserver(mH: Handler, var context: Context) : ContentObserver(mH) {

    var fontScaleUri = Settings.System.getUriFor(FONT_SCALE)



    override fun onChange(selfChange: Boolean, uri: Uri?) {
        if(fontScaleUri.equals(uri)){
            return
        }

    }


}