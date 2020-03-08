package com.challenge.theScore.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Function to check the network state
 */
fun hasNetwork(context:Context):Boolean
{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetwork != null
}