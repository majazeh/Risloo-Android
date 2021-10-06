# Add project specific ProGuard rules here.

# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# For more details, see
# http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following and
# specify the fully qualified class name to the JavaScript interface class :
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Specifies not to shrink the input class files. By default,
# shrinking is applied; all classes and class members are removed,
# except for the ones listed by the various -keep options,
# and the ones on which they depend, directly or indirectly.
# A shrinking step is also applied after each optimization step,
# since some optimizations may open the possibility to remove
# more classes and class members
-dontshrink

# ---------- OkHttp3 Rules ---------- #

-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**