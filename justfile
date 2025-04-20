set export

gradlew := "./gradlew"

# List available commands
default:
    just --list --unsorted

# Build project
[group("app")]
build:
    {{ gradlew }} build

# Run project
[group("app")]
run:
    {{ gradlew }} bootRun

# Test project
[group("app")]
test:
    {{ gradlew }} test

# Run services
[group("services")]
run-services:
    docker compose up -d

# Tear down services
[group("services")]
tear-services:
    docker compose down

# Create user
[group("requests")]
[group("users")]
create-user-request username password:
    hurl --variable username={{ username }} --variable password={{ password }} requests/user/create.hurl

# Greeting request
[group("requests")]
[group("greeting")]
greeting-request:
    hurl requests/greeting/get.hurl
