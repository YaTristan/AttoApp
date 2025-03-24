package com.example.attoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.attoapp.R


val mulishRegular = FontFamily(
    Font(R.font.mulish_regular)
)
val mulishBlack = FontFamily(
    Font(R.font.mulish_black)
)
val mulishBlackItalic = FontFamily(
    Font(R.font.mulish_blackitalic)
)
// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = mulishBlack,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        lineHeight = 23.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = mulishBlack,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = mulishBlack,
        fontWeight = FontWeight.W700,
        fontSize = 10.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = mulishRegular,
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = mulishBlack,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = mulishBlack,
        fontWeight = FontWeight.W700,
        fontSize = 8.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = mulishBlack,
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = mulishRegular,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = mulishRegular,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),

)