package com.saadfauzi.simase.helper

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class Helpers {
    companion object {
        const val URL = "http://192.168.205.48:8000/"
        const val ENDPOINT_LOGIN = "api/login"
        const val ENDPOINT_LOGOUT = "api/logout"
        const val ENDPOINT_Register = "api/register"
        const val ENDPOINT_CUTI = "api/cuti"
        const val ENDPOINT_JENIS_CUTI = "api/jeniscuti"
        const val ENDPOINT_PEGAWAI = "api/pegawai"
        const val ENDPOINT_JABATAN = "api/jabatan"
        const val ENDPOINT_PROFILE_USER = "api/profile"
        const val ENDPOINT_ATTENDANCE = "api/attendance"
        const val ENDPOINT_SHOW_IMAGE = "${URL}storage/attendance/"
        const val ENDPOINT_SHOW_PHOTO_USER = "${URL}storage/users/"
    }
}

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len:Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -=  5
    } while (streamLength > 1000000)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

@SuppressLint("SimpleDateFormat")
fun dateFormatter(params: String): String {
    val currentDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val formatter = SimpleDateFormat("EEE dd MMMM yyyy")
    val date = currentDate.parse(params)
    return date?.let { formatter.format(it) }.toString()
}