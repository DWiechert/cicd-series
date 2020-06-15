# cicd-series
Simple REST service with full CI/CD.

Blog posts about service can be found on:
- [Blogger](https://danwiechert.blogspot.com/)

Run with sbt:
```
sbt run
```

Run with artifact:
```
sbt assembly
java -jar target/scala-2.13/cicd-series-assembly-0.1.0-SNAPSHOT.jar
```

Verify service is running:
```
curl -H "Content-Type: application/json" -X GET http://127.0.0.1:8080/health
```

Example workflow of:
1. Query all guests (should be empty)
2. Add a guest
3. Query all guests (should have 1 guest)
4. Delete a guest
5. Query all guests (should be empty)

```
curl -H "Content-Type: application/json" -X GET localhost:8080/guests
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    13  100    13    0     0     63      0 --:--:-- --:--:-- --:--:--    63{"guests":[]}

curl -H "Content-Type: application/json" -X POST -d '{"name":"Dan", "age":31}' http://127.0.0.1:8080/guests
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   100  100    76  100    24   1727    545 --:--:-- --:--:-- --:--:--  2325The request has been fulfilled and resulted in a new resource being created.

curl -H "Content-Type: application/json" -X GET http://127.0.0.1:8080/guests
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    36  100    36    0     0   6000      0 --:--:-- --:--:-- --:--:--  6000{"guests":[{"age":31,"name":"Dan"}]}

curl -X DELETE http://127.0.0.1:8080/guests/Dan
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0

curl -H "Content-Type: application/json" -X GET http://127.0.0.1:8080/guests
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    13  100    13    0     0   2600      0 --:--:-- --:--:-- --:--:--  2600{"guests":[]}
```

Run PATs:
```
pip install -r requirements.txt
behave
```