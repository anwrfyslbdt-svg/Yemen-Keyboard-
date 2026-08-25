package package android.open.keyboard.defaults

import android.open.keyboard.Keyboard
import android.open.keyboard.abstracts.layout.AbstractComposeLayout
import android.open.keyboard.defaults.layout.utils.ExtensionLayout
import android.open.keyboard.defaults.layout.utils.KeyboardUtilsRow
import android.open.keyboard.defaults.layout.utils.Lexicon
import android.open.keyboard.defaults.layout.views.AlphabeticView
import android.open.keyboard.defaults.layout.views.NumberView
import android.open.keyboard.extensions.annotations.Extension
import android.open.keyboard.utils.shift.ShiftState
import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
@Extension(ID = "android.open.keyboard.defaults.KeyboardLayout", description = "Simple Compose Keyboard Layout")
class KeyboardLayout : AbstractComposeLayout() {
    /**
     * Keyboard layout view
     */
    private val alphabeticView: MutableState<Boolean> = mutableStateOf(true)

    /**
     * Keyboard special view ( special number view )
     */
    private val specialView: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Keyboard Lexicon
     */
    private val lexicon: MutableState<List<String>> = mutableStateOf(ArrayList())

    /**
     * Keyboard Lexicon Manager
     */
    private lateinit var lexiconManager: Lexicon



    override fun onCreate(context: Keyboard) {
        super.onCreate(context)
        lexiconManager = Lexicon(context)
    }



    @Composable
    override fun Layout(context: Keyboard) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(285.dp)
                .padding(0.dp)
                .background(Color(0.2f, 0.2f, 0.2f, 0.95f)),
        ) {
            if(content == null)
                Column(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxWidth().height(45.dp)) {
                    if(lexicon.value.isEmpty()) ExtensionLayout(extensions)
                    else Row(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier
                            .padding(start = 5.dp, end = 15.dp)
                            .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            TextButton(
                                modifier = Modifier.size(30.dp),
                                onClick = { lexicon.value = listOf() },
                                contentPadding = PaddingValues(0.dp),

                                colors = ButtonDefaults.textButtonColors().copy(
                                    contentColor = Color(0.9f, 0.9f, 0.9f)
                                )
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    "Back"
                                )
                            }
                        }
                        Box(modifier = Modifier) {
                            Lexicon(
                                buffer,
                                shift,
                                lexicon.value,
                                lexiconManager
                            ) { lexicon.value = listOf() }
                        }
                    }
                }

                Box(modifier = Modifier) {
                    if(alphabeticView.value) AlphabeticView(shift) { shift = it }
                    else NumberView { specialView.value = it }
                }

                Box(modifier = Modifier) {
                    KeyboardUtilsRow(
                        alphabeticView.value,
                        { alphabeticView.value = it },
                        shift,
                        { shift = it },

                        specialView.value
                    )
                }
            }
            else Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .background(Color(0.2f, 0.7f, 0.8f, 0.1f))
                ) {
                    TextButton(
                        onClick = { unloadExtensionFromView() },
                        contentPadding = PaddingValues(0.dp),

                        colors = ButtonDefaults.textButtonColors().copy(
                            contentColor = Color(0.9f, 0.9f, 0.9f)
                        )
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            "Back"
                        )
                    }
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    content!!.Content()
                }
            }
        }
    }



    override fun onResume(context: Keyboard, info: EditorInfo?) {

        /* Resets variables */

        alphabeticView.value = true
        specialView.value = false
        shift = ShiftState.ON
        lexicon.value = listOf()
    }



    override fun onBufferChange(context: Keyboard, buffer: StringBuffer) {
        super.onBufferChange(context, buffer)

        if(buffer.isEmpty()) lexicon.value = listOf()
        else {
            val res = lexiconManager.getMatches(buffer.toString())
            if(!res.isEmpty()) lexicon.value = res
            else lexicon.value = listOf("")
        }
    }
}
