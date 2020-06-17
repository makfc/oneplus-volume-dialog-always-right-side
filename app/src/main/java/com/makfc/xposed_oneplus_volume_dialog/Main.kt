package com.makfc.xposed_oneplus_volume_dialog

import de.robv.android.xposed.*
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class Main : IXposedHookLoadPackage {
    fun log(text: String) {
        XposedBridge.log("${BuildConfig.APPLICATION_ID}: $text")
    }

    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        when (lpparam.packageName) {
            "com.android.systemui" -> {
                log("Loaded app: " + lpparam.packageName)
                val methodHookReturnTrue = object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        log(
                            "afterHookedMethod: ${param.method}"
                        )
                        param.result = true
                    }
                }

                // VolumeDialog Start Position
                findAndHookMethod(
                    "com.android.systemui.volume.VolumeDialogImpl", lpparam.classLoader,
                    "isLandscape", methodHookReturnTrue
                )

                // VolumeDialog End Position
                findAndHookMethod(
                    "com.oneplus.volume.OpVolumeDialogImpl", lpparam.classLoader,
                    "isLandscape", methodHookReturnTrue
                )

                findAndHookMethod(
                    "com.oneplus.volume.OpOutputChooserDialog", lpparam.classLoader,
                    "isLandscape", methodHookReturnTrue
                )
            }
        }
    }
}