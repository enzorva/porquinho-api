## Teste de Requisições via curl


# /users
curl -X POST "http://localhost:8080/api/v1/users" \
    -H "Content-Type: application/json" \
    -d '{
        "full_name": "Usuário de Teste da Silva 2",
        "email": "teste2@email.com",
        "password": "123456",
        "income": 1500,
        "gender": "masculine",
        "phone_number": 11932345678,
        "birthday": "2000-01-01",
        "profile_picture_url": "https://img.porquinhoapp.com.br/user-profile/1"
    }'

###

# /users
curl -X GET "http://localhost:8080/api/v1/users"

###

# /users/{id}
curl -X GET "http://localhost:8080/api/v1/users/1"

###

# /users/{id}
curl -X PUT "http://localhost:8080/api/v1/users/1" \
    -H "Content-Type: application/json" \
    -d '{
        "full_name": "Usuário de Teste da Silva Alterado",
        "email": "testealterado@email.com",
        "password": "123456",
        "income": 1500,
        "gender": "masculine",
        "phone_number": 11912345678,
        "birthday": "2000-01-01",
        "profile_picture_url": "https://img.porquinhoapp.com.br/user-profile/1"
    }'

###

# /users/{id}
curl -X PATCH "http://localhost:8080/api/v1/users/1" \
    -H "Content-Type: application/json" \
    -d '{
        "full_name": "Usuário de Teste da Silva Alterado Novamente"
    }'

###

# /wallets
curl -X POST "http://localhost:8080/api/v1/wallets" \
    -H "Content-Type: application/json" \
    -d '{
        "user_id": 1,
        "name": "carteira principal",
        "description": "essa e a carteira principal do usuario de teste"
    }'

###

# /wallets
curl -X GET "http://localhost:8080/api/v1/wallets"

###

# /wallets/{id}
curl -X GET "http://localhost:8080/api/v1/wallets/1"

###

# /wallets/{id}
curl -X PUT "http://localhost:8080/api/v1/wallets/1" \
    -H "Content-Type: application/json" \
    -d '{
        "user_id": 1,
        "name": "carteira principal editada",
        "description": "essa e a carteira principal do usuario de teste editada"
    }'

###

# /wallets/{id}
curl -X PATCH "http://localhost:8080/api/v1/wallets/1" \
    -H "Content-Type: application/json" \
    -d '{
        "name": "carteira principal editada novamente"
    }'

###

# /categories
curl -X POST "http://localhost:8080/api/v1/categories" \
    -H "Content-Type: application/json" \
    -d '{
        "user_id": 1,
        "name": "Food",
        "type": "expense"
    }'

###

# /categories
curl -X GET "http://localhost:8080/api/v1/categories"

###

# /categories/{id}
curl -X GET "http://localhost:8080/api/v1/categories/1"

###

# /categories/{id}
curl -X PUT "http://localhost:8080/api/v1/categories/1" \
    -H "Content-Type: application/json" \
    -d '{
        "user_id": 1,
        "name": "Oxxo",
        "type": "recipe"
    }'

###

# /categories/{id}
curl -X PATCH "http://localhost:8080/api/v1/categories/1" \
    -H "Content-Type: application/json" \
    -d '{
        "name": "Oxxo"
    }'

###

# /banks
curl -X GET "http://localhost:8080/api/v1/banks"

###

# /banks/{id}
curl -X GET "http://localhost:8080/api/v1/banks/1"

###

# /account-types
curl -X GET "http://localhost:8080/api/v1/account-types"

###

# /account-types/{id}
curl -X GET "http://localhost:8080/api/v1/account-types/1"

###

# /accounts
curl -X POST "http://localhost:8080/api/v1/accounts" \
    -H "Content-Type: application/json" \
    -d '{
        "wallet_id": 1,
        "name": "Checking Account",
        "balance": 1250.50,
        "overdraft": 0
    }'

###

# /accounts
curl -X GET "http://localhost:8080/api/v1/accounts"

###

# /accounts/{id}
curl -X GET "http://localhost:8080/api/v1/accounts/1"

###

# /accounts/{id}
curl -X PUT "http://localhost:8080/api/v1/accounts/1" \
    -H "Content-Type: application/json" \
    -d '{
        "wallet_id": 1,
        "name": "Filha",
        "balance": 0.50,
        "overdraft": 0
    }'

###

# /accounts/{id}
curl -X PATCH "http://localhost:8080/api/v1/accounts/1" \
    -H "Content-Type: application/json" \
    -d '{
        "balance": 200.30
    }'

###

# /transactions
curl -X POST "http://localhost:8080/api/v1/transactions" \
    -H "Content-Type: application/json" \
    -d '{
        "transaction_value": 250.75,
        "description": "Grocery shopping",
        "transaction_date": "2025-10-15",
        "has_occurred": 1,
        "is_auto_confirmed": 0,
        "observation": "Bought fruits and vegetables"
    }'

###

# /transactions
curl -X GET "http://localhost:8080/api/v1/transactions"

###

# /transactions/{id}
curl -X GET "http://localhost:8080/api/v1/transactions/1"

###

# /transactions/{id}
curl -X PUT "http://localhost:8080/api/v1/transactions/1" \
    -H "Content-Type: application/json" \
    -d '{
        "transaction_value": 250.75,
        "description": "Grocery shopping para a festa",
        "transaction_date": "2025-10-17",
        "has_occurred": 1,
        "is_auto_confirmed": 0,
        "observation": "Comprei bolo"
    }'

###

# /transactions/{id}
curl -X PATCH "http://localhost:8080/api/v1/transactions/1" \
    -H "Content-Type: application/json" \
    -d '{
        "transaction_value": 200.78
    }'

###

# /transactions
curl -X DELETE "http://localhost:8080/api/v1/transactions" \
    -H "Content-Type: application/json" \
    -d '1'

###

# /accounts
curl -X DELETE "http://localhost:8080/api/v1/accounts" \
    -H "Content-Type: application/json" \
    -d '1'

###

# /categories
curl -X DELETE "http://localhost:8080/api/v1/categories" \
    -H "Content-Type: application/json" \
    -d '1'

###

# /wallets
curl -X DELETE "http://localhost:8080/api/v1/wallets" \
    -H "Content-Type: application/json" \
    -d '1'

###

# /users
curl -X DELETE "http://localhost:8080/api/v1/users" \
    -H "Content-Type: application/json" \
    -d '1'

###
