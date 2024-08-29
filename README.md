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