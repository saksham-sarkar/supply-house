Disclaimer

SupplyHouse.com Programming Test

The following problem is a simplified version of an actual programming task that has been built at
SupplyHouse. Your solution will not be used for any other purpose than evaluating programming skills of
candidates.

Instructions
Please return the answers within 72 hours of the receipt of these questions, in the form of email
attachments. Refer to our email for the exact time we expect to receive your solutions.
For clarification of the problems and submitting solutions, use devinterview@supplyhouse.com. Include
"[JAVA]" (with the square brackets) in the subject line when you turn in your work.
Because we intentionally leave the problem open-ended in order to see how you make assumptions,
you should try to limit your clarification questions only for the purpose of understanding what is being
asked.
Please do not post the questions/answers on the web or distribute them in any form. We want our
assessment to be accurate and fair to all candidates.

Problem
SupplyHouse is an e-commerce website that allows customers to create accounts and view their order
history. Our customers are either individuals or businesses. Currently, a business can have multiple
employees place orders with us, thus needing multiple accounts. We want to develop a new
functionality to link those accounts, thereafter “subaccounts”, to a common business account,
thereafter “business owner account”.
For example, Bob has his own business and an account with SupplyHouse. His employees, Jane and
Jack, also have their own accounts, and all 3 of them have placed orders on our website. We would like
to link Jane and Jack account to Bob’s account, so that Bob can see the orders that Jane and Jack
have placed. Bob would need to send an invitation to Jane and Jack SupplyHouse accounts, and they
can either accept or decline the invitation.
Your task is to design a Back-End system to support the below functionalities:
- A SupplyHouse account shall have the ability to request to upgrade their account to a
  business owner account. If the account has placed at least 10 orders within the past year, the
  request will be approved.
- Once approved, the business owner account shall have the ability to send invitations to other
  SupplyHouse accounts to join their business as subaccounts.
- After receiving such invitations, a SupplyHouse account shall have the ability to either accept or
  decline the invitation. If they accept it and become a subaccount, they can decide to share their
  order history from either their account creation date or the date they accept the invitation. A
  subaccount can only be linked to one business owner account, thus other business owners
  won’t have the ability to invite them to their account.

- At any point in time, a subaccount shall have the ability to unlink their account from the
  business owner account. Conversely a business owner account can also unlink any
  subaccounts if they wish. Once a subaccount leaves a business owner account, they can
  be invited to and linked to other businesses.
  You will need to design the necessary APIs, application layer, and persistence layer for this project.
  Discuss any performance or security concerns. Clearly state your assumptions, if any.
  The following tables are available:

Table Column Notes
ACCOUNT ACCOUNT_ID * Primary key

CREATION_DATE

ORDER ORDER_ID * Primary key

ACCOUNT_ID Foreign key constraint to
ACCOUNT_ID column in
ACCOUNT table

ORDER_DATE

Deliverables:
- APIs documentation with endpoints, HTTP method, request body, and response body.
- Java files with class skeletons, and sufficient in-file documentation. Implement methods
  for sending invitations and accepting/declining invitations. Other business logic can be in
  skeleton form.
- Database schema for the necessary tables.

######################################################################################################
API Documentation
- Account-Service
  - Create Account 
    - Method: POST
    - Endpoint: /account/api/create
    - Request: 
          - {
            "accountId": "string",
            "email": "string",
            "isBusinessOwner": true,
            "businessOwnerAccountId": "string",
            "subAccountIds": [1, 2, 3],
            "isShareFullHistory": true,
            "subAccountLinkedDate": "YYYY-MM-DDTHH:MM:SS"
            }
    - Response: 
          - {
            "accountId": "string",
            "email": "string",
            "isBusinessOwner": true,
            "businessOwnerAccountId": "string",
            "subAccountIds": [1, 2, 3],
            "isShareFullHistory": true,
            "subAccountLinkedDate": "YYYY-MM-DDTHH:MM:SS"
            }
  - Get Account based on account id
    - Method: GET
    - Endpoint: /account/api/{accountId}
    - PathVariable: String
    - Response: String
  - Upgrade to Business Owner
    - Method: POST
    - Endpoint: /account/api/upgrade?accountId={accountId}
    - RequestParam: String
    - Response: String
  - Link Subaccount to BusinessOwner
    - Method: POST
    - Endpoint: /account/api/link-subaccount
    - Request:
          - {
            "subAccountId": "12345",
            "businessOwnerId": "67890",
            "shareFullHistory": true
            }
    - Response: String
  - Unlink Subaccount
    - Method: GET
    - Endpoint: /account/api/unlink-subaccount/{accountId}
    - PathVariable: String
    - Response: String
  - Is SubAccount linked
    - Method: GET
    - Endpoint: /account/api/unlink-subaccount/{accountId}
    - PathVariable: String
    - Response: Boolean
- Invitation-Service
  - Send new invitation
    - Method: POST
    - Endpoint: /invitation/api/send
    - Request: 
          - {
            "invitationId": "1234",
            "businessOwnerId": "5678",
            "subAccountId": "abcd123",
            "invitationToken": "token12345",
            "senderEmail": "owner@example.com",
            "recipientEmail": "employee@example.com",
            "sentAt": "2024-08-28T12:34:56",
            "status": "PENDING",
            "acceptedAt": null,
            "declinedAt": null
            }
    - Response: String - invitationToken
  - Accept invitation
    - Method: POST
    - Endpoint: /invitation/api/accept?invitationToken={invitationToken}
    - RequestParam: String
    - Response: String
  - Decline invitation
    - Method: POST
    - Endpoint: /invitation/api/decline?invitationToken={invitationToken}
    - RequestParam: String
    - Response: String
  - Get Invitation by token
    - Method: GET
    - Endpoint: /invitation/api/{invitationToken} 
    - PathVariable: String
    - Response:
          - {
            "invitationId": "1234",
            "businessOwnerId": "5678",
            "subAccountId": "abcd123",
            "invitationToken": "token12345",
            "senderEmail": "owner@example.com",
            "recipientEmail": "employee@example.com",
            "sentAt": "2024-08-28T12:34:56",
            "status": "PENDING",
            "acceptedAt": null,
            "declinedAt": null
            }
- Notification-Service:
  - Send email notification
    - Method: POST
    - Endpoint: /notification/api/send-mail
    - Request:
        - {
          "notificationId": "123",
          "senderId": "s001",
          "recipientId": "r001",
          "senderEmail": "sender@example.com",
          "recipientEmail": "recipient@example.com",
          "type": "Invitation",
          "emailContent": "You've been invited!",
          "invitationToken": "token123"
          }
    - Response: String
- Order-Service:
  - Has placed 10 orders
    - Method: GET
    - Endpoint: /orders/api/has-placed-ten-orders/{accountId}
    - PathVariable: String
    - Response: Boolean
  - Fetch all orders 
    - Method: GET
    - Endpoint: /orders/api/fetch-all/{accountId}
    - PathVariable: String
    - Response: 
          - [
              {
              "orderId": "123",
              "accountId": "456",
              "orderDate": "2024-08-28"
              }
            ]
  - Fetch all orders after given date
    - Method: GET
    - Endpoint: /orders/api/fetch?accountId={accountId}&orderDate={orderDate}
    - RequestParam: String, String
    - Response:
          - [
             {
              "orderId": "123",
              "accountId": "456",
              "orderDate": "2024-08-28"
              }
            ]
- Orchestration-Service:
  - Upgrade Account
    - Method: POST
    - Endpoint: /orchestration/api/upgrade-account?accountId={accountId}
    - RequestParam: String
    - Response: String
  - Create Account
    - Method: POST
    - Endpoint: /orchestration/api/create-account
    - Request: 
          - {
            "accountId": "string",
            "email": "string",
            "isBusinessOwner": true,
            "businessOwnerAccountId": "string",
            "subAccountIds": [1, 2, 3],
            "isShareFullHistory": true,
            "subAccountLinkedDate": "YYYY-MM-DDTHH:MM:SS"
            }
    - Response: String
  - Create Order
    - Method: POST
    - Endpoint: /orchestration/api/create-order
    - Request:
          - {
            "orderId": "123",
            "accountId": "456",
            "orderDate": "2024-08-28"
            }
    - Response: String
  - Send Invitation
    - Method: POST
    - Endpoint: /orchestration/api/send-invitation?businessOwnerId={businessOwnerId}&subAccountId={subAccountId}
    - RequestParam: String, String
    - Response: String
  - Respond to Invitation
    - Method: POST
    - Endpoint: /orchestration/api/respond-to-invitation
    - Request:
          - {
            "subAccountId": "exampleSubAccountId",
            "invitationToken": "exampleInvitationToken",
            "isAccept": true,
            "isShareFullHistory": false
            }
    - Response: String
  - Get SubAccount Order History
    - Method: POST
    - Endpoint: /orchestration/api/get-order-history
    - Request:
          - {
            "subAccountId": "sub-account-id-123",
            "businessOwnerId": "business-owner-id-456",
            "shareFullHistory": true
            }
    - Response: String
  - Unlink SubAccount
    - Method: POST
    - Endpoint: /orchestration/api/unlink
    - Request:
          - {
            "subAccountId": "sub-account-id-123",
            "businessOwnerId": "business-owner-id-456",
            "shareFullHistory": true
            }
    - Response: String

###################################################################################################
Database Schema: Used In-memory database h2
  - ACCOUNTDB:
    - Table: ACCOUNT
      - columns: account_id(Bigint) not null primary key, email(varchar) not null, creation_date(datetime) not null, 
        last_updated(datetime) not null, account_link_date(datetime) nullable, shareFullHistory(boolean), 
      businessOwner(boolean), business_owner_id(Bigint)
  - INVITATIONDB:
    - Table: INVITATION
      - columns: id(Bigint) not null primary key, business_owner_id(Bigint) not null, sub_account_id(Bigint) not null, 
      invitation_token(varchar) unique not null, sender_email(varchar) not null, recipient_email(varchar) not null, 
      sent_at(datetime) not null, accepted_at(datetime), declined_at(datetime), status(varchar) not null
  - ORDERSDB:
    - Table: ORDERS
      - columns: order_id(Bigint) not null primary key, account_id(Bigint) not null, order_date(datetime) not null
  - NOTIFICATIONDB
    - Table: NOTIFICATION
      - columns: id(Bigint) not null primary key, sender_id(Bigint) not null, recipient_id(Bigint) not null,
      sender_email(varchar) not null, recipient_email(varchar) not null, type(enum) not null, content(varchar) not null

##############################################################################################################################
Implementation:
- Supplyhouse is a multi-module service application, having different microservices interacting 
- via a common microservice - orchestration-service
- Orchestration service initiates the  interaction with other service via Feign client.
- Application is Synchronous as we have the api for communication with other services.
- We have written the integration tests in the orchestration-service module which contains all the scenarios mentioned below:
  - Integration Tests Scenarios:
    - testRequestUpgradeToBusinessOwner
    - testSendInvitation
    - testAcceptInvitationAndShareFullHistory
    - testAcceptInvitationAndSharePartialHistory
    - testDeclineInvitation
    - testUnlinkSubAccount
    - testGetSubAccountOrderFullHistory
    - testGetSubAccountOrderPartialHistory

Communication: Feign Client
 - Orchestration-Service : Port 8090
   - Account-service : Port 8091
   - Order-service : Port 8094
   - Invitation-service : Port 8092
       - Notification-service : Port 8093
   

java Version: 11
Spring-boot-version: 2.6.8
