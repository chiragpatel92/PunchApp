package com.employee.punch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.employee.punch.ui.theme.Primary
import com.employee.punch.ui.theme.PrimaryDark
import com.employee.punch.ui.theme.TextSecondary
import com.employee.punch.ui.theme.Lato

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = PrimaryDark,
            fontSize = 16.sp,
            fontFamily = Lato
        ),
        placeholder = {
            Text(
                hint,
                color = TextSecondary,
                fontFamily = Lato
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFD7E2F3),
            unfocusedContainerColor = Color(0xFFD7E2F3),
            cursorColor = Primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    )
}

