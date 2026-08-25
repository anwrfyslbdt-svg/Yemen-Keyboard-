package android.open.keyboard.defaults

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

@Extension(
    ID = "android.open.keyboard.defaults.KeyboardLayout",
    description = "Yemen Keyboard Layout"
)
class KeyboardLayout : AbstractComposeLayout() {

    private val alphabeticView: MutableState<Boolean> =
        mutableStateOf(true)

    private val specialView: MutableState<Boolean> =
        mutableStateOf(false)

    private val lexicon: MutableState<List<String>> =
        mutableStateOf(ArrayList())

    private val yemenView: MutableState<Boolean> =
        mutableStateOf(false)

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
        ) {

            /*
             * خلفية الكيبورد الأصلية
             */
            Image(
                painter = painterResource(
                    id = android.open.keyboard.R.drawable.e715a8a47afac740d2b06a6e87acac5c
                ),
                contentDescription = "Keyboard Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color(0.1f, 0.1f, 0.1f, 0.35f)
                    )
            ) {

                /*
                 * إذا فتح المستخدم قسم اليمن
                 */
                if (yemenView.value) {

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        ) {

                            TextButton(
                                onClick = {
                                    yemenView.value = false
                                },
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults
                                    .textButtonColors()
                                    .copy(
                                        contentColor = Color.White
                                    )
                            ) {

                                Icon(
                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "Back"
                                )
                            }
                        }

                        /*
                         * صورة الإيموجيات اليمنية
                         */
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Image(
                                painter = painterResource(
                                    id = android.open.keyboard.R.drawable.yemen_emojis
                                ),
                                contentDescription = "Yemen Emojis",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(8.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }

                } else if (content == null) {

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        /*
                         * الشريط العلوي
                         */
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                        ) {

                            if (lexicon.value.isEmpty()) {

                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    ExtensionLayout(extensions)

                                    /*
                                     * زر القسم اليمني
                                     */
                                    TextButton(
                                        modifier = Modifier
                                            .padding(start = 4.dp)
                                            .size(40.dp),
                                        onClick = {
                                            yemenView.value = true
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        colors = ButtonDefaults
                                            .textButtonColors()
                                            .copy(
                                                contentColor = Color.White
                                            )
                                    ) {

                                        Image(
                                            painter = painterResource(
                                                id = android.open.keyboard.R.drawable.yemen_emojis
                                            ),
                                            contentDescription = "Yemen"
                                        )
                                    }
                                }

                            } else {

                                Row(
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .padding(
                                                start = 5.dp,
                                                end = 15.dp
                                            )
                                            .fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        TextButton(
                                            modifier = Modifier.size(30.dp),
                                            onClick = {
                                                lexicon.value = listOf()
                                            },
                                            contentPadding = PaddingValues(0.dp),
                                            colors = ButtonDefaults
                                                .textButtonColors()
                                                .copy(
                                                    contentColor = Color(
                                                        0.9f,
                                                        0.9f,
                                                        0.9f
                                                    )
                                                )
                                        ) {

                                            Icon(
                                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                                contentDescription = "Back"
                                            )
                                        }
                                    }

                                    Box {

                                        Lexicon(
                                            buffer,
                                            shift,
                                            lexicon.value,
                                            lexiconManager
                                        ) {
                                            lexicon.value = listOf()
                                        }
                                    }
                                }
                            }
                        }

                        /*
                         * لوحة الحروف والأرقام
                         */
                        Box {

                            if (alphabeticView.value) {

                                AlphabeticView(shift) {
                                    shift = it
                                }

                            } else {

                                NumberView {
                                    specialView.value = it
                                }
                            }
                        }

                        /*
                         * الصف السفلي
                         */
                        Box {

                            KeyboardUtilsRow(
                                alphabeticView.value,
                                {
                                    alphabeticView.value = it
                                },
                                shift,
                                {
                                    shift = it
                                },
                                specialView.value
                            )
                        }
                    }

                } else {

                    /*
                     * الإضافات
                     */
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {

                            TextButton(
                                onClick = {
                                    unloadExtensionFromView()
                                },
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults
                                    .textButtonColors()
                                    .copy(
                                        contentColor = Color(
                                            0.9f,
                                            0.9f,
                                            0.9f
                                        )
                                    )
                            ) {

                                Icon(
                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "Back"
                                )
                            }
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            content!!.Content()
                        }
                    }
                }
            }
        }
    }

    override fun onResume(
        context: Keyboard,
        info: EditorInfo?
    ) {

        alphabeticView.value = true
        specialView.value = false
        shift = ShiftState.ON
        lexicon.value = listOf()
        yemenView.value = false
    }

    override fun onBufferChange(
        context: Keyboard,
        buffer: StringBuffer
    ) {

        super.onBufferChange(context, buffer)

        if (buffer.isEmpty()) {

            lexicon.value = listOf()

        } else {

            val res = lexiconManager.getMatches(
                buffer.toString()
            )

            if (res.isNotEmpty()) {

                lexicon.value = res

            } else {

                lexicon.value = listOf("")
            }
        }
    }
}
