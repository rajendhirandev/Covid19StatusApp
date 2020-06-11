package com.practice.covid19.utils

import java.text.NumberFormat
import java.util.*

/*
Author: Rajendhiran Easu
Date: 09-May-20
*/

fun Int.numberFormat(): String = NumberFormat.getNumberInstance(Locale.US).format(this)
