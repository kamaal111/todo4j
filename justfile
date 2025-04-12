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

# Hello request
[group("requests")]
[group("hello")]
hello-request:
    hurl requests/hello/get.hurl
