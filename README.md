# AFTAS Java Spring Boot API

This repository contains the source code for the AFTAS (Anglers' Fish Tracking and Scoring) Java Spring Boot API. The API provides endpoints for managing members, levels, fishes, competitions, rankings, and huntings in the context of an angling competition.

## Prerequisites

Before you start, make sure you have the following prerequisites installed on your machine:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [MySQL Database](https://www.mysql.com/)

## Configuration

1. Clone the repository to your local machine:

    ```bash
    git clone https://github.com/SuperGhostSama/AFTAS-API.git
    cd AFTAS-API
    ```

2. Open the `application.properties` file in the `src/main/resources` directory.

   - Update the MySQL database configuration (url, username, password) according to your setup.

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

## Database Setup

1. Create a MySQL database for AFTAS:

    ```sql
    CREATE DATABASE aftas-v1;
    ```

2. The application will automatically create tables based on the entity models when you run it.

## Running the Application

1. Run the application using Maven:

    ```bash
    mvn spring-boot:run
    ```

2. The API will be accessible at [http://localhost:8080](http://localhost:8080).

## Testing with Postman

## Overview

This Postman collection provides a set of API endpoints to interact with the AFTAS (Anglers' Fish Tracking and Scoring) application. The collection includes requests for managing members, levels, fishes, competitions, rankings, and huntings.

## How to Use

1. **Download and Install Postman**: [Postman Download](https://www.postman.com/downloads/)

2. **Import the AFTAS API collection into Postman**:
   - Copy the raw JSON content of this collection.
   - Open Postman.
   - Click on the "Import" button in the top-left corner.
   - Paste the JSON content and click "Import."

3. **Set Environment Variables**:
   - Before running the requests, set the base URL as an environment variable. The default is set to `http://localhost:8080`.

4. **Explore and Execute Requests**:
   - Navigate through the folders and requests in the collection.
   - Update request parameters (if needed) such as request bodies, URL parameters, etc.
   - Execute the requests and examine the responses.

## Collection Structure

### 1. Members

- **getAllMembers**: Retrieve a list of all members.
- **addMember**: Add a new member to the system.
- **updateMember**: Update details of an existing member.
- **getMemberById**: Retrieve details of a member by ID.
- **deleteMember**: Delete a member by ID.
- **getMembersByCompetitionId**: Retrieve members participating in a specific competition.

### 2. Levels

- **getAllLevels**: Retrieve a list of all levels.
- **getLevelById**: Retrieve details of a level by ID.
- **deleteLevel**: Delete a level by ID.
- **addLevel**: Add a new level.
- **updateLevel**: Update details of an existing level.

### 3. Fish

- **getAllFishes**: Retrieve a list of all fishes.
- **addFish**: Add a new fish to the system.
- **getFishById**: Retrieve details of a fish by ID.
- **updateFish**: Update details of an existing fish.
- **deleteFish**: Delete a fish by ID.

### 4. Competition

- **getAllCompetitions**: Retrieve a list of all competitions.
- **addCompetition**: Add a new competition.
- **getCompetitionById**: Retrieve details of a competition by ID.
- **deleteCompetition**: Delete a competition by ID.

### 5. Ranking

- **getRankingsByMemberIdAndCompetitionId**: Retrieve rankings for a member in a specific competition.
- **updateRankingsByMemberIdAndCompetitionId**: Update rankings for a member in a specific competition.
- **registerMemberForCompetition**: Register a member for a competition.
- **getTop3**: Retrieve the top 3 members in a competition.

### 6. Hunting

- **getHuntingsByCompetition**: Retrieve huntings for a specific competition.
- **getHuntingsByCompetitionAndMember**: Retrieve huntings for a specific competition and member.
- **getHuntingsById**: Retrieve details of a hunting by ID.
- **deleteHunting**: Delete a hunting by ID.
- **addHuntingResult**: Add hunting results for a member in a competition.
- **UpdateHunting**: Update details of an existing hunting.

## Note

- Ensure that the AFTAS application is running and accessible at the specified base URL.
- Update request parameters as needed for your specific use case.
- Refer to the API documentation for more detailed information on each endpoint.

Feel free to explore and use the provided requests to interact with the AFTAS API!
