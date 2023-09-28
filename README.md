# TR Record Shop API

## Project description

Our MVP is an API backend for a music record shop. The backend system will store information about the records in stock, allowing us to query this data using various criteria such as title, year, genre, artists, and stock levels. Additionally, we have implemented functionality for updating and deleting records.

The project is developed using Java, Spring Boot, H2 database, postgresql, lombok, JUnit 5, and Maven.

We made the assumption that the "genre" field is represented as an enum rather than a string. This decision is based on the assumption that the frontend will feature a dropdown menu with predefined values for selecting a genre.

## Installation

Please ensure that you have Java and Maven installed on your computer. To run the unit tests, use the command 'mvn test'.

## Approaches

We are a team of 3, and our approach to the task begins with designing the API structure and service layer functionality. Next, we have collaboratively code the solution, with one team member taking the lead as the driver.

### API Structure:

#### Base URL /api/v1/album

#### get all albums in stock
* /instock
* return: array of albums [{id, title, artist, releasedYear, genre, stock}, ... ]
* error status:


#### get all albums by a given artist
* GET /artist
* parameter: artist
* return: array of albums [{id, title, artist, releasedYear, genre, stock}, ... ]
* error status: 404 unknown artist


#### get all albums by a given release year
* GET /year
* parameter: releasedYear
* return: array of albums [{id, title, artist, releasedYear, genre, stock}, ... ]
* error status: invalid year (not a number, out of range)


#### get all albums by a given genre
* GET /genre
* parameter: genre (now implemented as a string)
* return: array of albums [{id, title, artist, releasedYear, genre, stock}, ... ]
* error status: 404 - unknown genre


#### get album information by album title
* GET /title
* parameter: title
* return: array of albums [{id, title, artist, releasedYear, genre, stock}, ... ]
* error status: 404 - missing or invalid title string


#### add new album into the database
* POST /
* parameters: title, artist, releasedYear, genre.
* return: array of albums [{id, title, artist, releasedYear, genre, stock}, ... ]
* error status: if (artist, releasedYear and title) match another record - return error.


#### update album details
* PUT /
* parameters: id, title, artist, releasedYear, genre.
* error status: if (artist, year and title) match a record other than the one being edited - return error.
* error status: unknown id


#### update stock amounts of a particular album
* PATCH /stock
* parameters: id, stock
* error status: unknown id
* error status: stock >= 0


#### delete albums from the inventory
* DELETE /
* parameters: id
* error status: unknown id

### Service Layer Functionality:


#### getAlbumsInStock()
- returns list of all albums in stock 
- findByStockGreaterThan(0)

#### getAlbumsByArtist( String artist)
- Returns a list of all albums by artist
- findAllAlbumsByArtistContainingIgnoreCase(String artist)

#### getAlbumByYear( int year)
- Returns list of all albums for given year
- findAllAlbumsByYear( int year)

#### getAlbumsByGenre( Genre genre)
- Returns a list of all albums by genre
- findAllAlbumsByGenre( Genre genre)

#### getAlbumsByTitle(String title)
- Returns list of albums with the given title
- findAllAlbumsByTitleContainingIgnoreCase( String title)

#### insertAlbum( Album album )
- Calls repo.save(album)

#### updateAlbumById( Album album) - album = findById(id)
- repo.save(album)

#### updateAlbumStockById( long id, int stock ) - album = findById(id)
- repo.save(album)

#### deleteAlbumById( long id ) - album = findById
- repo.delete(album)

### Database Tables:

#### Albums

| ID           | integer - auto-increment | 
|--------------|:------------------------:| 
| title        |          string          | 
| artist       |          string          | 
| releasedYear |         integer          | 
| genre        |          string          | - originally implemented as enum 
| stock        |         integer          | 

## Future Roadmap

We plan to add a frontend to connect with our backend. The frontend will allow users to browse the music catalog, search for and purchase albums. Additionally, we can create an admin frontend to manage stock levels.