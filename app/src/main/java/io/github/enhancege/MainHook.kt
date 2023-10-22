package io.github.enhancege

import android.content.Context
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.sonymobile.gameenhancer") {
            //OneHandedMode detect
            XposedHelpers.findAndHookMethod(
                "com.sonymobile.gameenhancer.floating.mode.AccessibilitySettingHelper",
                lpparam.classLoader, "isOneHandedModeServiceDisabled",
                object : XC_MethodReplacement() {
                    override fun replaceHookedMethod(param: MethodHookParam?): Any {
                        return true
                    }
                }
            )

            //Magnification detect
            XposedHelpers.findAndHookMethod(
                "com.sonymobile.gameenhancer.floating.mode.AccessibilitySettingHelper",
                lpparam.classLoader, "isDisplayMagnificationEnabled",
                object : XC_MethodReplacement() {
                    override fun replaceHookedMethod(param: MethodHookParam?): Any {
                        return false
                    }
                }
            )

            //Accessibility detect
            XposedHelpers.findAndHookMethod(
                "com.sonymobile.gameenhancer.floating.mode.AccessibilitySettingHelper",
                lpparam.classLoader, "isDisableShortcut",
                List::class.java, Set::class.java,
                object : XC_MethodReplacement() {
                    override fun replaceHookedMethod(param: MethodHookParam?): Any {
                        return true
                    }
                }
            )
            /*
                        XposedHelpers.findAndHookMethod(
                            "com.sonymobile.gameenhancer.floating.mode.AccessibilitySettingHelper",
                            lpparam.classLoader, "isTargetAccessibilityDisabled",
                            Set::class.java, Set::class.java, Set::class.java,
                            object : XC_MethodReplacement() {
                                override fun replaceHookedMethod(param: MethodHookParam?): Any {
                                    return true
                                }
                            }
                        )
            */
            //Accessibility conflict detect
            XposedHelpers.findAndHookMethod(
                "com.sonymobile.gameenhancer.common.util.DeviceStatusUtil",
                lpparam.classLoader, "isConflicted",
                Context::class.java, List::class.java,
                object : XC_MethodReplacement() {
                    override fun replaceHookedMethod(param: MethodHookParam?): Any {
                        return false
                    }
                }
            )

            //Blacklist package detect
            XposedHelpers.findAndHookMethod(
                "com.sonymobile.gameenhancer.database.roomdb.GameDatabase",
                lpparam.classLoader, "isBlacklisted",
                String::class.java,
                object : XC_MethodReplacement() {
                    override fun replaceHookedMethod(param: MethodHookParam?): Any {
                        return false
                    }
                }
            )
        }
    }
}