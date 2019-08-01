package com.islam.basepropject.project_base.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings

import androidx.browser.customtabs.CustomTabsIntent

import com.islam.basepropject.MyApplication
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.POJO.Message

class IntentManager private constructor() {

    private fun openGoogleMapAtSpecificLocation(context: Context, location: Location) {
        val isInstalled = isPackageInstalledAndEnabled("com.google.android.apps.map",
                context.packageManager)
        if (isInstalled) {
            val uri = Uri.parse("google.navigation:q=" + location.latitude + "," + location.longitude)
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        } else {
            ActivityManager.showToastLong(context,Message(R.string.google_map_app_not_found))
        }
    }

    companion object {

        private fun isPackageInstalledAndEnabled(packageName: String, packageManager: PackageManager): Boolean {
            try {
                return packageManager.getApplicationInfo(packageName, 0).enabled
            } catch (e: PackageManager.NameNotFoundException) {
                return false
            }

        }

        private fun launchExternalApp(context: Context, url: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }

        fun openTabBrowser(context: Context, url: String) {
            openTabBrowser(context, "https://www.facebook.com/YOUR_PAGE_URL", context.resources.getColor(R.color.colorPrimary))
        }

        fun openTabBrowser(context: Context, url: String, color: Int) {
            val builder = CustomTabsIntent.Builder()

            builder.setToolbarColor(color)
            //set start and exit animations
            builder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right)

            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }

        fun openAppSettings() {
            val context = MyApplication.instance!!.getApplicationContext()

            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", context.getPackageName(), null)
            intent.data = uri
            context.startActivity(intent)
        }


        fun openFacebookPage(context: Context) {
            val isInstalled = isPackageInstalledAndEnabled("com.facebook.katana",
                    context.packageManager)

            if (isInstalled) {
                launchExternalApp(context, "fb://page/YOUR_PAGE_ID")
                //ex:fb://page/314086229325675
            } else {
                openTabBrowser(context, "https://www.facebook.com/YOUR_PAGE_URL")
                //ex :https://www.facebook.com/thaipeoplepowerpartypage
            }
        }

        fun openTwitterPage(context: Context) {
            val isInstalled = isPackageInstalledAndEnabled("com.twitter.android",
                    context.packageManager)

            if (isInstalled) {
                launchExternalApp(context, "twitter://user?user_id=YOUR_USER_ID")
                //ex :twitter://user?user_id=1049571254137643009
            } else {
                openTabBrowser(context, "https://twitter.com/YOUR_PAGE_URL")
                //ex :https://twitter.com/0xREjn0OHxcFkQg
            }
        }

        fun openInstagramPage(context: Context) {
            val isInstalled = isPackageInstalledAndEnabled("com.instagram.android",
                    context.packageManager)

            if (isInstalled) {
                launchExternalApp(context, "http://instagram.com/_u/YOUR_PAGE_URL")
                //ex :http://instagram.com/_u/thaipeoplepowerparty
            } else {
                openTabBrowser(context, "https://www.instagram.com/YOUR_PAGE_URL/")
                //ex :https://www.instagram.com/thaipeoplepowerparty/
            }
        }

        fun openYoutubeChannel(context: Context) {
            val isInstalled = isPackageInstalledAndEnabled("com.google.android.youtube",
                    context.packageManager)

            if (isInstalled) {
                launchExternalApp(context, "https://www.youtube.com/channel/YOUR_PAGE_URL")
                //ex :https://www.youtube.com/channel/UC-Mz1RS-96VrxZv_N7mYzEw
            } else {
                openTabBrowser(context, "https://www.youtube.com/channel/YOUR_PAGE_URL")
                //ex :https://www.youtube.com/channel/UC-Mz1RS-96VrxZv_N7mYzEw
            }
        }

        fun dialNumber(context: Context, phoneNumber: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            context.startActivity(intent)
        }
    }
}
