# Analysis

From the results we can see that, searching by Name field takes a lot of time and but searching by ID works perfectly.
That is because, ID is indexed field and it makes columns faster to query by creating pointers to where data is stored within the database.

|Statistics|t < 800ms|800ms < t < 1200ms|t > 1200ms|failed|
|---|---|---|---|---|
|/updatebyid|10000|0|0|0|
|/updatebyname|61|90|9846|3|
