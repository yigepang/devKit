package com.pang.devkit.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import com.pang.devkit.utils.phonecheck.FindDebugger;
import com.pang.devkit.utils.phonecheck.FindEmulator;
import com.pang.devkit.utils.phonecheck.FindMonkey;
import com.pang.devkit.utils.phonecheck.FindTaint;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 由hoozy于2024/1/3 16:45进行创建
 * 描述：
 */
public class PhoneUtilCheck {
    /**
     * 判定是否为模拟器一
     */
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }


    /**
     * 判定是否模拟器二  2020
     */
    private static final String[] PKG_NAMES = {"com.mumu.launcher", "com.ami.duosupdater.ui", "com.ami.launchmetro", "com.ami.syncduosservices", "com.bluestacks.home", "com.bluestacks.windowsfilemanager", "com.bluestacks.settings", "com.bluestacks.bluestackslocationprovider", "com.bluestacks.appsettings", "com.bluestacks.bstfolder", "com.bluestacks.BstCommandProcessor", "com.bluestacks.s2p", "com.bluestacks.setup", "com.bluestacks.appmart", "com.kaopu001.tiantianserver", "com.kpzs.helpercenter", "com.kaopu001.tiantianime", "com.android.development_settings", "com.android.development", "com.android.customlocale2", "com.genymotion.superuser", "com.genymotion.clipboardproxy", "com.uc.xxzs.keyboard", "com.uc.xxzs", "com.blue.huang17.agent", "com.blue.huang17.launcher", "com.blue.huang17.ime", "com.microvirt.guide", "com.microvirt.market", "com.microvirt.memuime", "cn.itools.vm.launcher", "cn.itools.vm.proxy", "cn.itools.vm.softkeyboard", "cn.itools.avdmarket", "com.syd.IME", "com.bignox.app.store.hd", "com.bignox.launcher", "com.bignox.app.phone", "com.bignox.app.noxservice", "com.android.noxpush", "com.haimawan.push", "me.haima.helpcenter", "com.windroy.launcher", "com.windroy.superuser", "com.windroy.launcher", "com.windroy.ime", "com.android.flysilkworm", "com.android.emu.inputservice", "com.tiantian.ime", "com.microvirt.launcher", "me.le8.androidassist", "com.vphone.helper", "com.vphone.launcher", "com.duoyi.giftcenter.giftcenter"};
    private static final String[] PATHS = {"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/qemud", "/dev/qemu_pipe", "/dev/socket/baseband_genyd", "/dev/socket/genyd"};
    private static final String[] FILES = {"/data/data/com.android.flysilkworm", "/data/data/com.bluestacks.filemanager"};

    public static boolean isSimulator(Context paramContext) {
        try {
            List pathList = new ArrayList();
            pathList = getInstalledSimulatorPackages(paramContext);
            if (pathList.size() == 0) {
                for (int i = 0; i < PATHS.length; i++)
                    if (i == 0) {
                        if (new File(PATHS[i]).exists()) continue;
                        pathList.add(Integer.valueOf(i));
                    } else {
                        if (!new File(PATHS[i]).exists()) continue;
                        pathList.add(Integer.valueOf(i));
                    }
            }
            if (pathList.size() == 0) {
                pathList = loadApps(paramContext);
            }
            return (pathList.size() == 0 ? null : pathList.toString()) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List getSimulatorInfo(Context paramContext) {
        List pathList = new ArrayList();
        List simulatorMaps = new ArrayList();

        try {
            pathList = getInstalledSimulatorPackages(paramContext);
            String brand = getSimulatorBrand(pathList);
            if (TextUtils.isEmpty(brand)) {
                List<String> list = loadApps(paramContext);
                if (list.size() > 0) {
                    simulatorMaps.add(list.get(0));
                }
            } else {
                simulatorMaps.add(brand);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simulatorMaps;
    }

    @SuppressLint("WrongConstant")
    private static List getInstalledSimulatorPackages(Context context) {
        ArrayList localArrayList = new ArrayList();
        try {
            for (int i = 0; i < PKG_NAMES.length; i++)
                try {
                    context.getPackageManager().getPackageInfo(PKG_NAMES[i], PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
                    localArrayList.add(PKG_NAMES[i]);
                } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
                }
            if (localArrayList.size() == 0) {
                for (int i = 0; i < FILES.length; i++) {
                    if (new File(FILES[i]).exists())
                        localArrayList.add(FILES[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localArrayList;
    }

    public static List loadApps(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<String> list = new ArrayList<>();
        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(intent, 0);
        //for循环遍历ResolveInfo对象获取包名和类名
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            String packageName = info.activityInfo.packageName;
            CharSequence cls = info.activityInfo.name;
            CharSequence name = info.activityInfo.loadLabel(context.getPackageManager());
            if (!TextUtils.isEmpty(packageName)) {
                if (packageName.contains("bluestacks")) {
                    list.add("蓝叠");
                    return list;
                }
            }
        }
        return list;
    }

    private static String getSimulatorBrand(List<String> list) {
        if (list.size() == 0)
            return "";
        String pkgName = list.get(0);
        if (pkgName.contains("mumu")) {
            return "mumu";
        } else if (pkgName.contains("ami")) {
            return "AMIDuOS";
        } else if (pkgName.contains("bluestacks")) {
            return "蓝叠";
        } else if (pkgName.contains("kaopu001") || pkgName.contains("tiantian")) {
            return "天天";
        } else if (pkgName.contains("kpzs")) {
            return "靠谱助手";
        } else if (pkgName.contains("genymotion")) {
            if (Build.MODEL.contains("iTools")) {
                return "iTools";
            } else if ((Build.MODEL.contains("ChangWan"))) {
                return "畅玩";
            } else {
                return "genymotion";
            }
        } else if (pkgName.contains("uc")) {
            return "uc";
        } else if (pkgName.contains("blue")) {
            return "blue";
        } else if (pkgName.contains("microvirt")) {
            return "逍遥";
        } else if (pkgName.contains("itools")) {
            return "itools";
        } else if (pkgName.contains("syd")) {
            return "手游岛";
        } else if (pkgName.contains("bignox")) {
            return "夜神";
        } else if (pkgName.contains("haimawan")) {
            return "海马玩";
        } else if (pkgName.contains("windroy")) {
            return "windroy";
        } else if (pkgName.contains("flysilkworm")) {
            return "雷电";
        } else if (pkgName.contains("emu")) {
            return "emu";
        } else if (pkgName.contains("le8")) {
            return "le8";
        } else if (pkgName.contains("vphone")) {
            return "vphone";
        } else if (pkgName.contains("duoyi")) {
            return "多益";
        }
        return "";
    }

    /**
     * 判定是否为模拟器三
     */
    public static boolean isAdopt(Context context) {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatusIntent = context.registerReceiver(null, intentFilter);
        int voltage = batteryStatusIntent.getIntExtra("voltage", 99999);
        int temperature = batteryStatusIntent.getIntExtra("temperature", 99999);
        if (((voltage == 0) && (temperature == 0)) || ((voltage == 10000) && (temperature == 0))) {//这是通过电池的伏数和温度来判断是真机还是模拟器
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判定是否为模拟器四
     *
     * @param context 上下文
     * @return true：是模拟器；false：不是模拟器
     */
    public static boolean check(Context context) {
        try {
            return isTaintTrackingDetected(context) || isMonkeyDetected() || isDebugged()
                    || isQEmuEnvDetected(context);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判定是否为模拟器四，安全模式<br>
     * 机制较弱，可避免部分真机被识别为模拟器，但是这也造成了有些模拟器无法被识别
     *
     * @param context 上下文
     * @return true：是模拟器；false：不是模拟器
     */
    public static boolean checkSafely(Context context) {
        try {
            return isTaintTrackingDetected(context) || isMonkeyDetected() || isDebugged()
                    || isQEmuEnvSafeDetected(context);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isTaintTrackingDetected(Context context) {
        boolean hasAppAnalysisPackage = FindTaint.hasAppAnalysisPackage(context.getApplicationContext());
        boolean hasTaintClass = FindTaint.hasTaintClass();
        boolean hasTaintMemberVariables = FindTaint.hasTaintMemberVariables();
        log("Checking for Taint tracking...");
        log("hasAppAnalysisPackage : " + hasAppAnalysisPackage);
        log("hasTaintClass : " + hasTaintClass);
        log("hasTaintMemberVariables : " + hasTaintMemberVariables);
        if (hasAppAnalysisPackage || hasTaintClass || hasTaintMemberVariables) {
            log("Taint tracking was detected.");
            return true;
        } else {
            log("Taint tracking was not detected.");
            return false;
        }
    }

    private static boolean isMonkeyDetected() {
        boolean isUserAMonkey = FindMonkey.isUserAMonkey();
        log("Checking for Monkey user...");
        log("isUserAMonkey : " + isUserAMonkey);
        if (isUserAMonkey) {
            log("Monkey user was detected.");
            return true;
        } else {
            log("Monkey user was not detected.");
            return false;
        }
    }

    private static boolean isDebugged() {
        log("Checking for debuggers...");
        boolean tracer = false;
        try {
            tracer = FindDebugger.hasTracerPid();
        } catch (Exception e) {
            //Logger.write("AntiEmulator isDebugged Exception", e);
        }

        boolean isBeingDebugged = FindDebugger.isBeingDebugged();
        log("isBeingDebugged : " + isBeingDebugged);
        log("hasTracerPid : " + tracer);
        if (FindDebugger.isBeingDebugged() || tracer) {
            log("Debugger was detected.");
            return true;
        } else {
            log("No debugger was detected.");
            return false;
        }
    }

    private static boolean isQEmuEnvDetected(Context context) {
        boolean hasKnownDeviceId = FindEmulator.hasKnownDeviceId(context.getApplicationContext());
        boolean hasKnownImsi = FindEmulator.hasKnownImsi(context.getApplicationContext());
        boolean hasEmulatorBuild = FindEmulator.hasEmulatorBuild(context.getApplicationContext());
        boolean hasKnownPhoneNumber = FindEmulator.hasKnownPhoneNumber(context.getApplicationContext());
        boolean isOperatorNameAndroid = FindEmulator.isOperatorNameAndroid(context.getApplicationContext());
        boolean hasPipes = FindEmulator.hasPipes();
        boolean hasQEmuDrivers = FindEmulator.hasQEmuDrivers();
        boolean hasEmulatorAdb = FindEmulator.hasEmulatorAdb();
        boolean hasQEmuFiles = FindEmulator.hasQEmuFiles();
        boolean hasGenyFiles = FindEmulator.hasGenyFiles();

        log("Checking for QEmu env...");
        log("hasKnownDeviceId : " + hasKnownDeviceId);
        log("hasKnownImsi : " + hasKnownImsi);
        log("hasEmulatorBuild : " + hasEmulatorBuild);
        log("hasKnownPhoneNumber : " + hasKnownPhoneNumber);
        log("isOperatorNameAndroid : " + isOperatorNameAndroid);
        log("hasPipes : " + hasPipes);
        log("hasQEmuDriver : " + hasQEmuDrivers);
        log("hasEmulatorAdb :" + hasEmulatorAdb);
        log("hasQEmuFiles : " + hasQEmuFiles);
        log("hasGenyFiles : " + hasGenyFiles);

        // 这个检测比较耗时
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    for (String abi : Build.SUPPORTED_ABIS) {
        //        if (abi.equalsIgnoreCase("armeabi-v7a")) {
        //            log("hitsQemuBreakpoint : " + FindEmulator.checkQemuBreakpoint());
        //        }
        //    }
        //}

        if (hasKnownDeviceId || hasKnownImsi || hasEmulatorBuild || hasKnownPhoneNumber || hasPipes
                || hasQEmuDrivers || hasEmulatorAdb || hasQEmuFiles || hasGenyFiles) {
            log("QEmu environment detected.");
            return true;
        } else {
            log("QEmu environment not detected.");
            return false;
        }
    }

    private static boolean isQEmuEnvSafeDetected(Context context) {
        boolean hasKnownDeviceId = FindEmulator.hasKnownDeviceId(context.getApplicationContext());
        boolean hasKnownImsi = FindEmulator.hasKnownImsi(context.getApplicationContext());
        //boolean hasEmulatorBuild = FindEmulator.hasEmulatorBuild(context.getApplicationContext());
        boolean hasKnownPhoneNumber = FindEmulator.hasKnownPhoneNumber(context.getApplicationContext());
        //boolean isOperatorNameAndroid = FindEmulator.isOperatorNameAndroid(context.getApplicationContext());
        boolean hasPipes = FindEmulator.hasPipes();
        boolean hasQEmuDrivers = FindEmulator.hasQEmuDrivers();
        //boolean hasEmulatorAdb = FindEmulator.hasEmulatorAdb();
        //boolean hasQEmuFiles = FindEmulator.hasQEmuFiles();
        boolean hasGenyFiles = FindEmulator.hasGenyFiles();

        log("Checking for QEmu env Safe...");
        log("hasKnownDeviceId : " + hasKnownDeviceId);
        log("hasKnownImsi : " + hasKnownImsi);
        //log("hasEmulatorBuild : " + hasEmulatorBuild);
        log("hasKnownPhoneNumber : " + hasKnownPhoneNumber);
        //log("isOperatorNameAndroid : " + isOperatorNameAndroid);
        log("hasPipes : " + hasPipes);
        log("hasQEmuDriver : " + hasQEmuDrivers);
        //log("hasEmulatorAdb :" + hasEmulatorAdb);
        //log("hasQEmuFiles : " + hasQEmuFiles);
        log("hasGenyFiles : " + hasGenyFiles);

        // 这个检测比较耗时
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    for (String abi : Build.SUPPORTED_ABIS) {
        //        if (abi.equalsIgnoreCase("armeabi-v7a")) {
        //            log("hitsQemuBreakpoint : " + FindEmulator.checkQemuBreakpoint());
        //        }
        //    }
        //}

        // 有些检测方法被屏蔽掉的原因：在部分真机上，也会被识别为true，所以屏蔽掉
        if (hasKnownDeviceId || hasKnownImsi
                //|| hasEmulatorBuild
                || hasKnownPhoneNumber || hasPipes || hasQEmuDrivers
                //|| hasEmulatorAdb || hasQEmuFiles
                || hasGenyFiles) {
            log("QEmu environment detected.");
            return true;
        } else {
            log("QEmu environment not detected.");
            return false;
        }
    }

    private static void log(String msg) {
        Log.d("AntiEmulator", msg);
    }


    /**
     * 检测是否root 一
     */
    public static boolean checkRoot() {
        if (TextUtils.isEmpty(readFile("system/build.prop"))) {
            return false;
        } else {
            return true;
        }

    }

    public static String readFile(String fileName) {
        File file = new File(fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            while ((len = fis.read(bytes)) > 0) {
                bos.write(bytes, 0, len);
            }
            String result = new String(bos.toByteArray());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检测root 二
     */
    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", " /data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }

    }
}
