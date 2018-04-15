N26 Test App
============

The app is a test application for 2 simple apis

/transactions
-------------
Every Time a new transaction happened, this endpoint will be called.
    
    Body:
        {
            "amount": 12.3,
            "timestamp": 1478192204000
        }

    Where:
        ● amount - transaction amount
        ● timestamp - transaction time in epoch in millis in UTC time zone (this is not current timestamp)
    
    Returns: Empty body with either 201 or 204.
        ● 201 - in case of success
        ● 204 - if transaction is older than 60 seconds

    Where:
        ● amount is a double specifying the amount
        ● time is a long specifying unix time format in milliseconds

​/statistics
-----------
It returns the statistic based on the transactions which happened in the last 60 seconds.

    Returns:
    {
        "sum": 1000,
        "avg": 100,
        "max": 200,
        "min": 50,
        "count": 10
    }
    
    Where:
        ● sum is a double specifying the total sum of transaction value in the last 60 seconds
        ● avg is a double specifying the average amount of transaction value in the last 60 seconds
        ● max is a double specifying single highest transaction value in the last 60 seconds
        ● min is a double specifying single lowest transaction value in the last 60 seconds
        ● count is a long specifying the total number of transactions happened in the last 60 seconds
        

Branches
--------
    master:
        has a basic implemntation of the above specified apis
    
    o1_attempt:
        has a attempted implementation of making statistics api O(1)