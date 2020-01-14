package com.connected.theapp

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.test.rule.ActivityTestRule
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier


inline fun <reified T : ViewModel, A : ComponentActivity> ActivityTestRule<A>.viewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazy { activity.getViewModel<T>(qualifier, parameters) }

