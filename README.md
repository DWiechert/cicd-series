# cicd-series
Simple REST service with full CI/CD.

Example commands:
```
// Add a new guest
curl -H "Content-Type: application/json" -X POST -d '{"name":"Dan", "age":31}' http://127.0.0.1:8080/guests

// List all current guests
curl -H "Content-Type: application/json" -X GET http://127.0.0.1:8080/guests

// Delete a guest
curl -H "Content-Type: application/json" -X DELETE -d '{"name":"Dan", "age":31}' http://127.0.0.1:8080/guests
```