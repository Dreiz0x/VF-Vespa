-keep class kotlinx.serialization.** { *; }
-keepclassmembers class **$$serializer { *; }
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault,Signature,InnerClasses,EnclosingMethod

-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends androidx.room.RoomDatabase
-keep class * implements androidx.work.ListenableWorker
-keep class dev.vskelk.cdf.** { *; }

-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn javax.annotation.**
