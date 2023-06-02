# Meeting Room Reservation System
NSS semestral project - CTU FEE.

This repositry contains only backend part of the system. Frontend part can be found here: https://github.com/ifralou/reservation-system

# Semestral project requirements:
  1. Technology and programming language:  Java 17 + Spring Boot.
  2. Used DB: Postgre SQL on CTU servers.
  3. Used cache: Hazelcast embedded cache - used to cache user's reservations - can be found in Reservation Management Microservice
  4. Used messaging: Kafka + Mailgun - used to notify a user by email about successful reservation. Kafka producer can be found in Reservation Management Microservice.
     Kafka consumer can be found in Notification Management Microservice.
  5. Authentication and Authorization: Auth0 platform. Details can be found in FE repository.
  6. Communication: REST is used for both backend-backend and frontend-backend communication + Kafka is used for asynchronous communication between 
     Reservation Management Microseervice and Notification Management Microservice.
  7. Architecture: Microservices.
