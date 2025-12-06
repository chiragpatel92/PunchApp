package com.employee.punch.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.employee.punch.ui.theme.Primary
import com.employee.punch.ui.theme.TextSecondary

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(hint, color = TextSecondary) },
        textStyle = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Primary,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Primary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        modifier = modifier.fillMaxWidth()
    )
}
