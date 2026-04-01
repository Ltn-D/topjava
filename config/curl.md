# TopJava API cURL Commands

> Use bash

### Get meal 100003

'curl -X GET "http://localhost:8080/topjava/rest/user/meals/100003"'

### Get all meal 
'curl -X GET "http://localhost:8080/topjava/rest/user/meals"'

### Get filtered mealsTo

'curl -G "http://localhost:8080/topjava/rest/user/meals/filter" \
--data-urlencode "startDate=2020-01-31" \
--data-urlencode "endDate=2020-01-31" \
--data-urlencode "startTime=00:00" \
--data-urlencode "endTime=20:00"'

### Create meal

'curl -X POST "http://localhost:8080/topjava/rest/user/meals" \
-H "Content-Type: application/json" \
-d '{
"dateTime": "2020-02-01T18:00:00",
"description": "Созданный ужин",
"calories": 300
}''

### Update meal

'curl -X PUT "http://localhost:8080/topjava/rest/user/meals/100003" \
-H "Content-Type: application/json" \
-d '{
"id": 100003,
"dateTime": "2020-01-30T10:02:00",
"description": "Обновленный завтрак",
"calories": 200
}''

### Delete meal 100003

'curl -X DELETE "http://localhost:8080/topjava/rest/user/meals/100003"'