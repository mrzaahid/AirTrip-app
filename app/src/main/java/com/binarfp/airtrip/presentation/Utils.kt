package com.binarfp.airtrip.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.EditText
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import kotlin.math.abs

object Utils {
    fun isempty(text : EditText): Boolean {
        val str : CharSequence = text.text.toString()
        return str.isEmpty()
    }
    fun bitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
    fun convertStringToBitmap(image: String): Bitmap {
        val imageBytes = Base64.decode(image, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
    fun duration(time1: String, time2: String): CharSequence {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val date1 = simpleDateFormat.parse(time1)
        val date2 = simpleDateFormat.parse(time2)
        // Calculating the difference in milliseconds
        val differenceInMilliSeconds = abs(date2.time - date1.time)
        // Calculating the difference in Hours
        val differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000) % 24)
        // Calculating the difference in Minutes
        val differenceInMinutes = differenceInMilliSeconds / (60 * 1000) % 60
        // Calculating the difference in Seconds
//        val differenceInSeconds = differenceInMilliSeconds / 1000 % 60
//        val date1 = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(time1)
//        val date2 = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(time2)
        return (differenceInHours.toString() + " hours "
                + differenceInMinutes + " minutes ")
    }
}