# Analysis

According to my test results:
- */post* endpoint showed the best performance with no failed requests.
- */postwithref* was a bit slow and ended up with 19 failed requests. This is because, there is an additional column for referenceLinkURL to insert.
- */postandref* endpoint was the slowest with 952 failed requests. This is because, the route creates reference seperately in another table and links it with post via foreign key and this takes additional time to do.

|Endpoint\Respond time|t < 800 ms|800 ms < t < 1200 ms|t > 1200 ms|failed|
|---|---|---|---|---|
|/post|7553|697|1750|0|
|/postwithref|7383|1028|1570|19|
|/postandref|403|663|7977|952|