package com.example.chatbot.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chatbot.R
import com.example.chatbot.db.entity.ChatWithMessages
import com.example.chatbot.screens.components.CustomToast
import com.example.chatbot.screens.components.ChatMessageUi
import com.example.chatbot.screens.components.PreviousMessagesComposable
import com.example.chatbot.screens.components.PreviousMessagesWithSpecificChatComposable
import com.example.chatbot.utils.noRippleClickable
import com.example.chatbot.viewModel.ChatViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(vm: ChatViewModel = koinViewModel()) {
    val chatUiState by vm.state.collectAsStateWithLifecycle()
    var userMessage by remember { mutableStateOf("") }
    var showOldMessages by remember(chatUiState.oldMessages) { mutableStateOf(false) }
    val slideInAnimation = remember { MutableTransitionState(false).apply { targetState = true } }
    var selectedChat by remember { mutableStateOf<ChatWithMessages?>(null) }
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier
        .fillMaxSize()
        .noRippleClickable(
            interactionSource = interactionSource,
            onClick = {
                keyboardController?.hide()
                showOldMessages = false
                selectedChat = null
            }
        ), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (chatUiState.oldMessages.isNotEmpty()) {
                IconButton(
                    onClick = {
                        showOldMessages = !showOldMessages
                        slideInAnimation.targetState = showOldMessages
                    },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Show/Hide Old Messages"
                    )
                }
            } else {
                Spacer(modifier = Modifier.padding(16.dp))
            }
            Button(
                onClick = {
                    vm.restartChat()
                },
                modifier = Modifier
                    .height(56.dp)
                    .padding(top = 16.dp, end = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.new_conversation))
            }
        }


        Box(modifier = Modifier.fillMaxWidth()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 80.dp, end = 16.dp),
                reverseLayout = false
            ) {
                items(chatUiState.messages) { message ->
                    ChatMessageUi(
                        message = message
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomStart)
                    .background(if (selectedChat != null) Color.Gray else Color.Transparent),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = userMessage,
                    onValueChange = { userMessage = it },
                    placeholder = { Text(stringResource(R.string.write_your_message)) },
                    enabled = selectedChat == null,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = if (selectedChat == null) Color.Black else Color.Gray,
                        cursorColor = if (selectedChat == null) Color.Blue else Color.Gray,
                        placeholderColor = Color.Gray,
                        disabledTextColor = Color.Gray,
                        disabledPlaceholderColor = Color.DarkGray,
                    )
                )

                Button(
                    onClick = {
                        if (userMessage.isNotBlank() && selectedChat == null) {
                            vm.sendMessage(userMessage)
                            userMessage = ""
                        }
                    },
                    enabled = selectedChat == null,
                    modifier = Modifier
                        .height(56.dp) ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.send))
                }
            }

            Column(modifier = Modifier.background(Color.White)) {
                AnimatedVisibility(
                    visibleState = slideInAnimation,
                    enter = slideInHorizontally(initialOffsetX = { -it }),
                    exit = slideOutHorizontally(targetOffsetX = { -it })
                ) {
                    if (selectedChat == null) {
                        PreviousMessagesComposable(showOldMessages, chatUiState, {
                            selectedChat = it
                        }, {
                            showOldMessages = it
                        })

                    } else {
                        PreviousMessagesWithSpecificChatComposable(selectedChat) {
                            selectedChat = it
                            showOldMessages = true
                        }

                    }
                }
            }

            if (chatUiState.showErrorToast) {
                CustomToast(
                    message = stringResource(id = chatUiState.errorMessage),
                    position = Alignment.BottomCenter
                )
            }
        }
    }

}