# topjava
{{base_url}} - http://localhost:8080/topjava
###
# @name Get meal
# This is a GET request and it is used to "get" data from an endpoint. There is no request body for a GET request, but you can use query parameters to help specify the resource you want data on (e.g., in this request, we have `id=1`).
# A successful GET response will have a `200 OK` status, and should include some kind of response body - for example, HTML web content or JSON data.
GET {{base_url}}/rest/user/meals/100003

> {%
// TODO: migrate to HTTP Client Response handler API
// pm.test("Status code is 200", function () {
//     pm.response.to.have.status(200);
// });
%}

###
# @name Get all meal
# This is a GET request and it is used to "get" data from an endpoint. There is no request body for a GET request, but you can use query parameters to help specify the resource you want data on (e.g., in this request, we have `id=1`).
# A successful GET response will have a `200 OK` status, and should include some kind of response body - for example, HTML web content or JSON data.
GET {{base_url}}/rest/user/meals

> {%
// TODO: migrate to HTTP Client Response handler API
// pm.test("Status code is 200", function () {
//     pm.response.to.have.status(200);
// });
%}

###
# @name Get filtered mealsTo
# This is a GET request and it is used to "get" data from an endpoint. There is no request body for a GET request, but you can use query parameters to help specify the resource you want data on (e.g., in this request, we have `id=1`).
# A successful GET response will have a `200 OK` status, and should include some kind of response body - for example, HTML web content or JSON data.
GET {{base_url}}/rest/user/meals/filter?
startDate=2020-01-31&
endDate=2020-01-31&
startTime=00%3A00&
endTime=20%3A00

> {%
// TODO: migrate to HTTP Client Response handler API
// pm.test("Status code is 200", function () {
//     pm.response.to.have.status(200);
// });
%}

###
# @name Post data
# This is a POST request, submitting data to an API via the request body. This request submits JSON data, and the data is reflected in the response.
# A successful POST request typically returns a `200 OK` or `201 Created` response code.
POST {{base_url}}/rest/user/meals
Content-Type: application/json

{
"dateTime": "2020-02-01T18:00:00",
"description": "Созданный ужин",
"calories": 300
}

> {%
// TODO: migrate to HTTP Client Response handler API
// pm.test("Successful POST request", function () {
//     pm.expect(pm.response.code).to.be.oneOf([200, 201]);
// });
//
%}

###
# @name Update data
# This is a PUT request and it is used to overwrite an existing piece of data. For instance, after you create an entity with a POST request, you may want to modify that later. You can do that using a PUT request. You typically identify the entity being updated by including an identifier in the URL (eg. `id=1`).
# A successful PUT request typically returns a `200 OK`, `201 Created`, or `204 No Content` response code.
PUT {{base_url}}/rest/user/meals/100003
Content-Type: application/json

{
"id": 100003,
"dateTime": "2020-01-30T10:02:00",
"description": "Обновленный завтрак",
"calories": 200
}

> {%
// TODO: migrate to HTTP Client Response handler API
// pm.test("Successful PUT request", function () {
//     pm.expect(pm.response.code).to.be.oneOf([200, 201, 204]);
// });
//
%}

###
# @name Delete data
# This is a DELETE request, and it is used to delete data that was previously created via a POST request. You typically identify the entity being updated by including an identifier in the URL (eg. `id=1`).
# A successful DELETE request typically returns a `200 OK`, `202 Accepted`, or `204 No Content` response code.
DELETE {{base_url}}/rest/user/meals/100003

> {%
// TODO: migrate to HTTP Client Response handler API
// pm.test("Successful DELETE request", function () {
//     pm.expect(pm.response.code).to.be.oneOf([200, 202, 204]);
// });
//
%}