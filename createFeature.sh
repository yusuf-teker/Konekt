#!/bin/bash

# Kullanım: ./createFeature.sh Dashboard
# Yusuf Teker - Konekt Auto Feature Generator

if [ -z "$1" ]; then
  echo "❌ Kullanım: ./createFeature.sh FeatureName"
  exit 1
fi

FEATURE_NAME=$1
FEATURE_LOWER=$(echo "$FEATURE_NAME" | tr '[:upper:]' '[:lower:]')

# Yollar
SHARED_PATH="shared/src/commonMain/kotlin/org/yusufteker/konekt/feature/$FEATURE_LOWER"
UI_PATH="composeApp/src/commonMain/kotlin/org/yusufteker/konekt/ui/screen/$FEATURE_LOWER/presentation"

echo "📦 Creating feature: $FEATURE_NAME"
echo "🧱 Paths:"
echo " - $SHARED_PATH"
echo " - $UI_PATH"

mkdir -p $SHARED_PATH
mkdir -p $UI_PATH

# Action
cat <<EOF > $SHARED_PATH/${FEATURE_NAME}Action.kt
package org.yusufteker.konekt.feature.$FEATURE_LOWER

sealed interface ${FEATURE_NAME}Action {
    object Init : ${FEATURE_NAME}Action
    object NavigateBack : ${FEATURE_NAME}Action
}
EOF

# State
cat <<EOF > $SHARED_PATH/${FEATURE_NAME}State.kt
package org.yusufteker.konekt.feature.$FEATURE_LOWER

import org.yusufteker.konekt.base.BaseState

data class ${FEATURE_NAME}State(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null
) : BaseState {
    override fun copyWithLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}
EOF

# ViewModel
cat <<EOF > $UI_PATH/${FEATURE_NAME}ViewModel.kt
package org.yusufteker.konekt.ui.screen.$FEATURE_LOWER.presentation

import org.yusufteker.konekt.feature.$FEATURE_LOWER.${FEATURE_NAME}Action
import org.yusufteker.konekt.feature.$FEATURE_LOWER.${FEATURE_NAME}State
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel

class ${FEATURE_NAME}ViewModel :
    PlatformBaseViewModel<${FEATURE_NAME}State>(${FEATURE_NAME}State()) {

    fun onAction(action: ${FEATURE_NAME}Action) {
        when (action) {
            is ${FEATURE_NAME}Action.Init -> init()
            is ${FEATURE_NAME}Action.NavigateBack -> navigateBack()
        }
    }

    private fun init() {
        // TODO: Implement feature logic
    }
}
EOF

# Screen
cat <<EOF > $UI_PATH/${FEATURE_NAME}Screen.kt
package org.yusufteker.konekt.ui.screen.$FEATURE_LOWER.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.koin.compose.viewmodel.koinViewModel
import org.yusufteker.konekt.feature.$FEATURE_LOWER.${FEATURE_NAME}Action
import org.yusufteker.konekt.feature.$FEATURE_LOWER.${FEATURE_NAME}State
import org.yusufteker.konekt.ui.base.BaseContentWrapper
import org.yusufteker.konekt.ui.navigation.NavigationHandler
import org.yusufteker.konekt.ui.navigation.NavigationModel

@Composable
fun ${FEATURE_NAME}ScreenRoot(
    viewModel: ${FEATURE_NAME}ViewModel = koinViewModel(),
    onNavigateTo: (NavigationModel) -> Unit = {}
) {
    val state = viewModel.state.collectAsState().value
    NavigationHandler(viewModel) { model ->
        onNavigateTo(model)
    }
    BaseContentWrapper(state = state) { modifier ->
        ${FEATURE_NAME}Screen(state = state, onAction = viewModel::onAction)
    }
    LaunchedEffect(Unit) {
        viewModel.onAction(${FEATURE_NAME}Action.Init)
    }
}

@Composable
fun ${FEATURE_NAME}Screen(
    state: ${FEATURE_NAME}State,
    onAction: (${FEATURE_NAME}Action) -> Unit
) {
    Scaffold {
        Text("🚧 $FEATURE_NAME Screen is under construction")
    }
}
EOF

echo "✅ Feature '$FEATURE_NAME' created successfully!"
