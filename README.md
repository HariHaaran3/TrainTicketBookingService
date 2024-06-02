# 🚆 Train Ticket Booking Service

## 📝 Introduction

This is a Spring Boot service for managing train ticket reservations and bookings.

## 🛠 Prerequisites

Ensure you have the following installed:
- Java 11
- Maven 3.6.3 or later

## 🔧 Building the Project

To build the project, open a terminal and navigate to the project's root directory, then run:

```sh
mvn clean install

```
This command will clean the existing compiled files, download and install the project's dependencies, and build the project.

## ▶️ Running the Application

After building the project, you can run the application using the following command:

```sh
mvn spring-boot:run
```

## Note: Default values are inserted into the in-memory database when starting the server.

**Please log in using the browser: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)**
* **Driver Class:** org.h2.Driver
* **JDBC URL:** jdbc:h2:mem:testdb
* **User Name:** sa
* **Password:** password

### Queries to view the tables:

```sql
SELECT * FROM train_detail;
SELECT * FROM train_section_and_seat_detail;
SELECT * FROM user_detail;
SELECT * FROM ticket_booking_detail;
```


## 🌐 Endpoints

1. First, we need to create a user and then authenticate the user to generate a JWT token. This JWT token is valid for 5 minutes. We need to use this JWT token in the header to access all the endpoints. Steps 1 and 2 below are mandatory, after which you can use the other endpoints with the JWT token.

2. RateLimiter is implemented with limitForPeriod=3 requests per second, timeoutDuration=1s, and limitRefreshPeriod=1s.

## 📝 Step 1: Create User

* Endpoint: POST: http://localhost:8080/trainTicketBooking/v1/user
* Description: Creates a new user.


* Request Body:

```
{
    "userName": "Priya_Mohan_Cloud_Bees",
    "firstName": "Priya",
    "lastName": "Mohan",
    "email": "priya.mohan@example.com",
    "password": "password"
}
```

* Response Body:

```
{
    "userId": 6,
    "userName": "Priya_Mohan_Cloud_Bees",
    "firstName": "Priya",
    "lastName": "Mohan",
    "email": "priya.mohan@example.com",
    "password": "password",
    "ticketBookingDetails": []
}
```

## 🔑 Step 2: Authentication

* Endpoint: POST http://localhost:8080/trainTicketBooking/v1/authenticate
* Description: Authenticates a user and generates a JWT token.


* Request Body:

```
{
  "userName": "Priya_Mohan_Cloud_Bees",
  "password": "password"
}
```

* Response Body:

```
{
  "jwt": "your_jwt_token"
}
```

## 👤 User Details

## 3: Get User Details

* Endpoint: GET http://localhost:8080/trainTicketBooking/v1/user/{userId}
* Description: Retrieves user details by user ID.


* Response Body:

```
{
    "userId": 1,
    "userName": "Hari_Haran_Cloud_Bees",
    "firstName": "Hari",
    "lastName": "Haran",
    "email": "hari.Haran@example.com",
    "password": "password",
    "ticketBookingDetails": [
        {
            "ticketBookingId": 2,
            "userId": 1,
            "userName": "Hari_Haran_Cloud_Bees",
            "email": "hari.Haran@example.com",
            "trainNumber": 1,
            "trainName": "London to France Express",
            "fromLocation": "London",
            "toLocation": "France",
            "ticketCost": 5.25,
            "section": "Section_A",
            "seat": "A_2"
        },
        {
            "ticketBookingId": 1,
            "userId": 1,
            "userName": "Hari_Haran_Cloud_Bees",
            "email": "hari.Haran@example.com",
            "trainNumber": 1,
            "trainName": "London to France Express",
            "fromLocation": "London",
            "toLocation": "France",
            "ticketCost": 5.25,
            "section": "Section_A",
            "seat": "A_1"
        }
    ]
}
```

## 4: Delete User

* Endpoint: DELETE http://localhost:8080/trainTicketBooking/v1/user/{userId}
* Description: Deletes a user by user ID.


* Response Body: 204 No Content


## 🚂 Train Details

## 5: Create Train Detail

* Endpoint: POST http://localhost:8080/trainTicketBooking/v1/trainDetail
* Description: Creates a new train detail.


* Request Body:

```
{
    "trainName": "France to London Express",
    "fromLocation": "France",
    "toLocation": "London",
    "trainSectionAndSeatDetails": [
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_4"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_1"
        },
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_1"
        },
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_2"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_2"
        },
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_3"
        },
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_5"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_5"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_4"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_3"
        }
    ]
}
```

* Response Body:

```
{
    "trainNumber": 3,
    "trainName": "France to London Express",
    "fromLocation": "France",
    "toLocation": "London",
    "trainSectionAndSeatDetails": [
        {
            "sectionAndSeatId": 26,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_4"
        },
        {
            "sectionAndSeatId": 30,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_5"
        },
        {
            "sectionAndSeatId": 22,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_3"
        },
        {
            "sectionAndSeatId": 21,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_4"
        },
        {
            "sectionAndSeatId": 27,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_2"
        },
        {
            "sectionAndSeatId": 29,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_3"
        },
        {
            "sectionAndSeatId": 28,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_1"
        },
        {
            "sectionAndSeatId": 25,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_5"
        },
        {
            "sectionAndSeatId": 24,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_1"
        },
        {
            "sectionAndSeatId": 23,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_2"
        }
    ]
}
```

## 6: Update Train Detail

* Endpoint: PUT http://localhost:8080/trainTicketBooking/v1/trainDetail/{trainNumber}
* Description: Updates an existing train detail by train number.


* Request Body:

```
{
    "trainName": "France to London Express",
    "fromLocation": "France",
    "toLocation": "London",
    "trainSectionAndSeatDetails": [
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_6"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "A_7"
        },
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_8"
        },
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_9"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "A_10"
        },
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "B_6"
        },
        {
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "B_7"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_8"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_9"
        },
        {
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_10"
        }
    ]
}
```

* Response Body:

```
{
    "trainNumber": 3,
    "trainName": "France to London Express",
    "fromLocation": "France",
    "toLocation": "London",
    "trainSectionAndSeatDetails": [
        {
            "sectionAndSeatId": 32,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_8"
        },
        {
            "sectionAndSeatId": 33,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_8"
        },
        {
            "sectionAndSeatId": 34,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_6"
        },
        {
            "sectionAndSeatId": 40,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_9"
        },
        {
            "sectionAndSeatId": 38,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "A_9"
        },
        {
            "sectionAndSeatId": 39,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "A_10"
        },
        {
            "sectionAndSeatId": 35,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "B_6"
        },
        {
            "sectionAndSeatId": 31,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "B_10"
        },
        {
            "sectionAndSeatId": 36,
            "section": "Section_B",
            "ticketCost": 8.5,
            "seat": "A_7"
        },
        {
            "sectionAndSeatId": 37,
            "section": "Section_A",
            "ticketCost": 7.25,
            "seat": "B_7"
        }
    ]
}
```

## 7: Get Train Detail

* Endpoint: GET http://localhost:8080/trainTicketBooking/v1/trainDetail/{trainNumber}
* Description: Retrieves train detail by train number.


* Response Body:

```
json
Copy code
{
    "trainNumber": 1,
    "trainName": "London to France Express",
    "fromLocation": "London",
    "toLocation": "France",
    "trainSectionAndSeatDetails": [
        {
            "sectionAndSeatId": 9,
            "section": "Section_B",
            "ticketCost": 6.5,
            "seat": "B_4"
        },
        {
            "sectionAndSeatId": 5,
            "section": "Section_A",
            "ticketCost": 5.25,
            "seat": "A_5"
        },
        {
            "sectionAndSeatId": 2,
            "section": "Section_A",
            "ticketCost": 5.25,
            "seat": "A_2"
        },
        {
            "sectionAndSeatId": 8,
            "section": "Section_B",
            "ticketCost": 6.5,
            "seat": "B_3"
        },
        {
            "sectionAndSeatId": 4,
            "section": "Section_A",
            "ticketCost": 5.25,
            "seat": "A_4"
        },
        {
            "sectionAndSeatId": 7,
            "section": "Section_B",
            "ticketCost": 6.5,
            "seat": "B_2"
        },
        {
            "sectionAndSeatId": 3,
            "section": "Section_A",
            "ticketCost": 5.25,
            "seat": "A_3"
        },
        {
            "sectionAndSeatId": 10,
            "section": "Section_B",
            "ticketCost": 6.5,
            "seat": "B_5"
        },
        {
            "sectionAndSeatId": 1,
            "section": "Section_A",
            "ticketCost": 5.25,
            "seat": "A_1"
        },
        {
            "sectionAndSeatId": 6,
            "section": "Section_B",
            "ticketCost": 6.5,
            "seat": "B_1"
        }
    ]
}
```

## 8: Delete Train Detail

* Endpoint: DELETE http://localhost:8080/trainTicketBooking/v1/trainDetail/{trainNumber}
* Description: Deletes train detail by train number.


* Response: 204 No Content


## 🎫 Ticket Booking

## 9: Purchase Train Ticket

* Endpoint: POST http://localhost:8080/trainTicketBooking/v1/purchaseTrainTicket
* Description: Purchases a train ticket.


* Request Body:

```
{
    "userId": 1,
    "trainNumber": 1,
    "sectionAndSeatId": 1,
    "trainName": "London to France Express",
    "fromLocation": "London",
    "toLocation": "France",
    "ticketCost": 5.25,
    "section": "Section_A",
    "seat": "A_1"
}
```

* Response Body:

```
{
    "ticketBookingId": 1,
    "userId": 1,
    "userName": "Hari_Haran_Cloud_Bees",
    "email": "hari.Haran@example.com",
    "trainNumber": 1,
    "trainName": "London to France Express",
    "fromLocation": "London",
    "toLocation": "France",
    "ticketCost": 5.25,
    "section": "Section_A",
    "seat": "A_1"
}
```

## 10: Get Train Ticket Receipt by Booking ID

* Endpoint: GET http://localhost:8080/trainTicketBooking/v1/trainTicketReceipt/{ticketBookingId}
* Description: Retrieves train ticket receipt by booking ID.


* Response Body:

```
{
    "ticketBookingId": 1,
    "userId": 1,
    "userName": "Hari_Haran_Cloud_Bees",
    "email": "hari.Haran@example.com",
    "trainNumber": 1,
    "trainName": "London to France Express",
    "fromLocation": "London",
    "toLocation": "France",
    "ticketCost": 5.25,
    "section": "Section_A",
    "seat": "A_1"
}
```

## 11: Update Seat in Train Ticket Receipt

* Endpoint: PUT http://localhost:8080/trainTicketBooking/v1/trainTicketReceipt/{ticketBookingId}/seat
* Description: Updates seat in the train ticket receipt.


* Request Params:

```
section: Section_A
newSeat: A_5
```

* Response Body:

```
{
    "ticketBookingId": 1,
    "userId": 1,
    "userName": "Hari_Haran_Cloud_Bees",
    "email": "hari.Haran@example.com",
    "trainNumber": 1,
    "trainName": "London to France Express",
    "fromLocation": "London",
    "toLocation": "France",
    "ticketCost": 5.25,
    "section": "Section_A",
    "seat": "A_5"
}
```

## 12: Delete Train Ticket Receipt

* Endpoint: DELETE http://localhost:8080/trainTicketBooking/v1/trainTicketReceipt/{ticketBookingId}
* Description: Deletes train ticket receipt by booking ID.


* Response Body: 204 No Content


## 🎟 User Ticket Details

## 13: Get User Ticket Booking Details

* Endpoint: GET http://localhost:8080/trainTicketBooking/v1/trainTicket/{userId}
* Description: Retrieves all ticket booking details for a user by user ID.


* Response Body:

```
[
    {
        "ticketBookingId": 6,
        "userId": 3,
        "userName": "Anjali_Sharma_Cloud_Bees",
        "email": "anjali.sharma@example.com",
        "trainNumber": 1,
        "trainName": "London to France Express",
        "fromLocation": "London",
        "toLocation": "France",
        "ticketCost": 6.5,
        "section": "Section_B",
        "seat": "B_1"
    },
    {
        "ticketBookingId": 7,
        "userId": 3,
        "userName": "Anjali_Sharma_Cloud_Bees",
        "email": "anjali.sharma@example.com",
        "trainNumber": 1,
        "trainName": "London to France Express",
        "fromLocation": "London",
        "toLocation": "France",
        "ticketCost": 6.5,
        "section": "Section_B",
        "seat": "B_2"
    }
]
```

## 14. Delete User Ticket Booking Details

* Endpoint: DELETE http://localhost:8080/trainTicketBooking/v1/trainTicket/user/{userId}
* Description: Deletes all ticket booking details for a user by user ID.


* Response Body: 204 No Content

## 🚉 Train Section Details

## 15. Get Allotted Train Section Details

* Endpoint: GET http://localhost:8080/trainTicketBooking/v1/trainTicket/trainNumber/{trainNumber}/section/{section}
* Description: Retrieves all ticket booking details for a specific train number and section.


* Response Body:

```
[
    {
        "ticketBookingId": 1,
        "userId": 1,
        "userName": "Hari_Haran_Cloud_Bees",
        "email": "hari.Haran@example.com",
        "trainNumber": 1,
        "trainName": "London to France Express",
        "fromLocation": "London",
        "toLocation": "France",
        "ticketCost": 5.25,
        "section": "Section_A",
        "seat": "A_1"
    },
    {
        "ticketBookingId": 2,
        "userId": 1,
        "userName": "Hari_Haran_Cloud_Bees",
        "email": "hari.Haran@example.com",
        "trainNumber": 1,
        "trainName": "London to France Express",
        "fromLocation": "London",
        "toLocation": "France",
        "ticketCost": 5.25,
        "section": "Section_A",
        "seat": "A_2"
    },
    {
        "ticketBookingId": 3,
        "userId": 2,
        "userName": "Rahul_Verma_Cloud_Bees",
        "email": "rahul.verma@example.com",
        "trainNumber": 1,
        "trainName": "London to France Express",
        "fromLocation": "London",
        "toLocation": "France",
        "ticketCost": 5.25,
        "section": "Section_A",
        "seat": "A_3"
    },
    {
        "ticketBookingId": 4,
        "userId": 4,
        "userName": "Vikas_Gupta_Cloud_Bees",
        "email": "vikas.gupta@example.com",
        "trainNumber": 1,
        "trainName": "London to France Express",
        "fromLocation": "London",
        "toLocation": "France",
        "ticketCost": 5.25,
        "section": "Section_A",
        "seat": "A_4"
    },
    {
        "ticketBookingId": 5,
        "userId": 5,
        "userName": "Priya_Iyer_Cloud_Bees",
        "email": "priya.iyer@example.com",
        "trainNumber": 1,
        "trainName": "London to France Express",
        "fromLocation": "London",
        "toLocation": "France",
        "ticketCost": 5.25,
        "section": "Section_A",
        "seat": "A_5"
    }
]
```