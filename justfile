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

# Hello request
[group("requests")]
[group("hello")]
hello-request:
    hurl requests/hello/get.hurl

# Greeting request
[group("requests")]
[group("greeting")]
greeting-request:
    hurl requests/greeting/get.hurl
