## News Compose Pagination
A simple demo project for demonstrating the Paging3 library with Jetpack Compose.

#### Screenprints

<img width="150" alt="image" src="https://github.com/user-attachments/assets/e992472d-b4d9-4c2a-806f-59785c8b0505" /> <img width="150" alt="image" src="https://github.com/user-attachments/assets/d5b510d7-83a0-4111-9944-20168800f6b2" /> <img width="150" alt="image" src="https://github.com/user-attachments/assets/18b96e3b-dbea-456f-b773-3ddb70f00dd6" />



#### App Features
- Users can view the latest news from the news api.
- Users can also search for any news from the api.
- Supports pagination so you can literally view all news from around the world in the last 5 years.

#### App Architecture
Based on mvvm architecture and repository pattern.

## The app includes the following main components:
- A web api service.
- Pagination support for data received from the api.
- A repository that works with the api service, providing a unified data interface.
- A ViewModel that provides data specific for the UI.
- The UI, using Jetpack Compose, which shows a visual representation of the data in the ViewModel.
- Unit Test cases for API service and Paging source.

## App Packages
- **data** - contains

   - . **remote** - contains the api classes to make api calls to MovieDB server, using Retrofit.
   - . **local** - contains the db classes to cache network data.
  - . **repository** - contains the repository classes, which acts as a bridge between the db, api and the paging classes.   
  - . **source** - contains the remote mediator and paging source classes, responsible for checking if data is available in the db and triggering api requests, if it is not, saving the response in the database.
- **module** - contains dependency injection classes, using Hilt.
- **ui** - contains compose components and classes needed to display movie/tv list and movie/tv detail screen.
- **util** - contains util classes needed for compose redirection, ui/ux animations.


