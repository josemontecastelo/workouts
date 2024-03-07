package com.josealfonsomora.workouts.features.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.josealfonsomora.workouts.R
import com.josealfonsomora.workouts.ui.theme.WorkoutsTheme

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    when(state){
        is SettingsState.Loaded -> SettingsContent(state as SettingsState.Loaded) {
            viewModel.onCheckedChange(it)
        }

        SettingsState.Loading -> SettingsLoading()
    }
}

@Composable
fun SettingsContent(state: SettingsState.Loaded, onCheckedChange: (DayOfWeek) -> Unit){
    Column(Modifier.padding(16.dp)){
        DailyCheck(state.lunes, R.string.lunes) { onCheckedChange(DayOfWeek.LUNES) }
        DailyCheck(state.martes, R.string.martes) { onCheckedChange(DayOfWeek.MARTES) }
        DailyCheck(state.miercoles, R.string.miercoles) { onCheckedChange(DayOfWeek.MIERCOLES) }
        DailyCheck(state.jueves, R.string.jueves) { onCheckedChange(DayOfWeek.JUEVES) }
        DailyCheck(state.viernes, R.string.viernes) { onCheckedChange(DayOfWeek.VIERNES) }
        DailyCheck(state.sabado, R.string.sabado) { onCheckedChange(DayOfWeek.SABADO) }
        DailyCheck(state.domingo, R.string.domingo) { onCheckedChange(DayOfWeek.DOMINGO) }
    }
}

@Composable
fun DailyCheck(state:Boolean, @StringRes text:Int, onCheckedChange: () -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically){
        Text(stringResource(id = text), style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.weight(1f))
        Checkbox(checked = state, onCheckedChange = {onCheckedChange()})
    }
}

@Composable
fun SettingsLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Loading..")
            Spacer(modifier = Modifier.padding(16.dp))
            CircularProgressIndicator()
        }
    }
}