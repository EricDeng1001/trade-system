package com.zts.xtp.common.jni;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class JNILoadLibrary {
    public static boolean glogHasInited = false;

    public static boolean isLoad = false;

    public static void loadLibrary() {
        if (!isLoad) {
            isLoad = true;
            String libraryPath = "";
            String osLibPath = System.getProperty("user.dir");
            String os = System.getProperty("os.name").toLowerCase();
            boolean osIsLinux = (os.indexOf("linux") != -1);
            boolean osIsWindows = false;
            boolean osIsMacOsX = false;
            if (osIsLinux) {
                libraryPath = osLibPath + "/lib/linux:";
            } else {
                osIsWindows = (os.indexOf("windows") != -1);
                if (osIsWindows) {
                    String arch = System.getProperty("os.arch");
                    if ("x86".equals(arch))
                        libraryPath = osLibPath + "/lib/win32:";
                    else
                        libraryPath = osLibPath + "/lib/win64:";
                } else {
                    //官方指出mac平台1.1.19.2版本存在bug无法登陆，如需使用mac版本请使用v1.1.18.19,此处不予实现
                    osIsMacOsX = "mac os x".equals(os);
                    libraryPath = "";
                }
            }

            final boolean osIsWindows_final = osIsWindows;
            final boolean osIsMacOsX_final = osIsMacOsX;
            String[] libraryPathArray = libraryPath.split(":");
            Map<String, String> nativeLibHasLoaded = new HashMap<String, String>();
            for (int i = 0; i < libraryPathArray.length; i++) {
                String dirPath = libraryPathArray[i];
                File dir = new File(dirPath);
                File[] defiles = dir.listFiles(pathName -> {
                    String name = pathName.getName();
                    boolean isLib = false;
                    if (osIsLinux)
                        isLib = (name.startsWith("libtradeplugin") || name.startsWith("libquoteplugin") ||
                                name.startsWith("libxtptraderapi") || name.startsWith("libxtpquoteapi") || name.startsWith("libsodium") || name.startsWith("libglog"))
                                && name.endsWith(".so");
                    if (osIsMacOsX_final)
                        isLib = (name.startsWith("libtradeplugin") || name.startsWith("libquoteplugin") ||
                                name.startsWith("libxtptraderapi") || name.startsWith("libxtpquoteapi") || name.startsWith("libglog"))
                                && (name.endsWith(".dylib") || name.endsWith(".jnilib"));
                    if (osIsWindows_final)
                        isLib = (name.startsWith("quoteplugin") || name.startsWith("tradeplugin") ||
                                name.startsWith("xtptraderapi") || name.startsWith("xtpquoteapi") || name.startsWith("glog"))
                                && name.endsWith(".dll");
                    return isLib;
                });
                if (defiles == null || defiles.length == 0) {
                    continue;
                }

                //定义加载顺序，如不按照glog -> xtpquote -> xtptrader -> trader -> quote的顺序加载，会出现依赖异常
                PriorityQueue<OrderFile> queue = new PriorityQueue<>((t0, t1) -> {
                    return t0.order - t1.order;
                });

                for (File defile : defiles) {
                    if (defile.getName().indexOf("glog") != -1) {
                        queue.offer(new OrderFile(1, defile));
                    } else if (defile.getName().indexOf("xtpquoteapi") != -1) {
                        queue.offer(new OrderFile(2, defile));
                    } else if (defile.getName().indexOf("xtptraderapi") != -1) {
                        queue.offer(new OrderFile(3, defile));
                    } else if (defile.getName().indexOf("tradeplugin") != -1) {
                        queue.offer(new OrderFile(4, defile));
                    } else if (defile.getName().indexOf("quoteplugin") != -1) {
                        queue.offer(new OrderFile(5, defile));
                    }
                }

                while (!queue.isEmpty()) {
                    OrderFile orderFile = queue.poll();
                    File defile = orderFile.file;
                    String fileName = defile.getName();
                    String prefix = fileName.substring(fileName.lastIndexOf("."));
                    int num = prefix.length();
                    String filerNameWithoutExt = fileName.substring(0, fileName.length() - num);
                    if (nativeLibHasLoaded.containsKey(filerNameWithoutExt))
                        continue;
                    System.load(defile.getAbsolutePath());
                    System.out.println("load native lib:" + defile.getAbsolutePath());
                    nativeLibHasLoaded.put(filerNameWithoutExt, "1");
                }
            }
        }
    }
}

//定义排序类
class OrderFile {
    int order;
    File file;

    public OrderFile(int order, File file) {
        this.order = order;
        this.file = file;
    }
}
