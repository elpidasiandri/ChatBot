
The user interface (UI) of our application is designed with simplicity and functionality in mind:
- Ellipsis Menu: In the top-left corner, an ellipsis icon appears only if there are previous conversations available.
- View-Only Past Conversations: Users can click on any past conversation to view its full content, but they cannot continue the discussion or make changes.
- Reset Button: Located at the top-right, the reset button allows users to restart their session, effectively deleting their current conversation.
  
The application is built with a robust and modern architecture, leveraging the following technologies:
- 	Room Database: Used to store chat conversations locally, enabling the display of conversation history in the UI.
- Jetpack Compose: The UI is implemented using Jetpack Compose, ensuring a responsive and declarative design approach.
- MVVM Architecture: The app follows the Model-View-ViewModel (MVVM) pattern. All HTTP calls and state updates are handled within the ViewModel to maintain a clean separation of concerns.
- 	Koin Dependency Injection: Dependency injection is achieved using Koin, enabling the seamless injection of use cases and repositories throughout the app.
 	
The application is designed with a robust architecture to ensure a seamless and user-friendly experience:
- 	Module Loading: Upon startup, the app loads the necessary modules, including the network module, database module, and chatNetworkModule.
- Conversation History: The ViewModel invokes a use case at launch to check for previous conversations with the chatbot. This ensures that users can view their conversation history even after closing and reopening the app. The use of Room Database guarantees that these conversations are stored locally, preserving them across sessions.
- Room Database Integration: All interactions with the chatbot are saved to the Room database as they occur.
- Modern Architecture: The app leverages Jetpack Compose for the UI, MVVM for clean state management, and Koin for dependency injection, providing a highly modular and maintainable codebase.
- Error Handling: A custom extension, catchAndHandleError, is used in HTTP calls to handle errors gracefully.If the user lacks an internet connection, an appropriate error message ("Internet connection required") is displayed. For other errors, a generic error message is shown to maintain a consistent user experience.
- Custom UI for Errors: Errors are visually communicated through a CustomToastComponent, which displays a red error message to the user. This toast appears for 3 seconds, ensuring clarity without being obtrusive.
- Secure Secret Key Management: The application's secret key is stored securely in the local.properties file, which is listed in .gitignore. This ensures that the secret key is excluded from version control, preventing it from being exposed when the project is pushed to a remote repository.
- 	I used the OpenAI SDK to communicate with OpenAI's servers via their API. The OpenAI SDK provides a set of tools and libraries that simplify interaction with OpenAI's services, such as GPT models, without the need to manually handle HTTP requests and responses. The OpenAI SDK facilitates sending requests to the OpenAI platform and processing the responses returned by the server. By using the openai SDK library, my application connects to OpenAI via RESTful API requests, which include sending data and receiving results in JSON format. This follows a client-server architecture, where my application acts as the client, directly communicating with OpenAI’s server to exchange data. Specifically, through the OpenAI SDK, I am able to send data to the OpenAI API, such as questions or text for analysis and process the responses returned by the API. Utilize tools that simplify authentication, request handling, and response processing in a secure and efficient manner. This approach with the OpenAI SDK is particularly useful as it eliminates the need for manual handling of HTTP requests and reduces the complexity of developing the application. With this technology, my application can effectively interact with OpenAI's capabilities, offering users detailed results and advanced features powered by artificial intelligence. https://github.com/openai/openai-java?fbclid=IwZXh0bgNhZW0CMTAAAR3pEWx-ANn5bTtVrGyW3PdnRyMZvgpQ4URM9Px4H8T5Cfe_hrteKT6ffzk_aem_JO5mEleemHc3SYIzv8xlsw 

Schema:

1. Db (creation room)
- 	dao
- 	entity
2. Di (modules)
3. Models (domain model)
4. Network (connection with openai)
5. Repositories
-  db
-  network
6. Screens (ui with Jetback Compose)
-  components
7. Ui.theme
8. useCases
- db
- network
9. utils (extensions)
10. viewModel
-  state
