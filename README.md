Waller Service

Run application: mvn spring-boot:run

Swagger: http://localhost:8080/wallet/swagger-ui/index.html#

Endpoint:

createWallet: Creates a new wallet with an initial balance of zero.

Functionality:

After creation, users can perform various transactions on the wallet, including deposits, withdrawals, and transfers.
Please note that this application uses an in-memory database, meaning that all data (including wallets and transactions) is lost upon application restart.

Technologies:

Spring Boot
In-memory H2 database

Application Division:
The application is divided into two services:
Wallet: Provides endpoints for creating a wallet, checking the current balance, and checking the balance for a specific period.
Wallet Transaction: Provides endpoints for deposit, withdrawal, and transfer transactions.

Command Pattern Usage:
Separating the transaction execution logic into specific commands is a good practice, promoting system flexibility and extensibility.

Exception Handling:
The try-catch block ensures that specific transaction exceptions are handled appropriately, while generic exceptions are caught and rethrown as InternalExceptions.

@Transactional Annotation:
The use of the @Transactional annotation guarantees that transaction operations are atomic, preventing data inconsistencies in case of failures.

Wallet Validation:
The validateWalletId method checks if the wallet exists in the database before proceeding with the transaction.
The validateBalance method ensures that the wallet has sufficient balance for the transaction value.
The validateIdempotecyKey method checks if a transaction with the same idempotency key has already been registered.

Improvements:

Unit tests
Application of the Strategy design pattern to remove conditional logic from the service layer.

