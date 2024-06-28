# Tool Rental Application

This is a simple tool rental application for a store, similar to Home Depot, that rents out tools. The application calculates rental charges based on the tool type, rental days, and any applicable discounts.

## Requirements

- JDK 11 or later
- Maven

## Tools

The tools available for rental are as follows:

| Tool Code | Tool Type  | Brand    | Daily Charge | Weekday Charge | Weekend Charge | Holiday Charge |
|-----------|------------|----------|--------------|----------------|----------------|----------------|
| CHNS      | Chainsaw   | Stihl    | $1.49        | Yes            | No             | Yes            |
| LADW      | Ladder     | Werner   | $1.99        | Yes            | Yes            | No             |
| JAKD      | Jackhammer | DeWalt   | $2.99        | Yes            | No             | No             |
| JAKR      | Jackhammer | Ridgid   | $2.99        | Yes            | No             | No             |

## Holidays

- Independence Day: July 4th (observed on the closest weekday if it falls on a weekend)
- Labor Day: First Monday in September

## Checkout Information

Checkout requires the following information to be provided:

- Tool code
- Rental day count (must be 1 or greater)
- Discount percent (0-100)
- Check out date

## Installation

1. Clone the repository:
    
    git clone https://github.com/Vamsikldev/kl0480.git
    cd kl0480    

2. Build the project using Maven:

    mvn clean install
    Above command Run the test cases 
    


