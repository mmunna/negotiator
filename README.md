Negotiator
======

As of now this application queries and manages clients' product price information.
In future it will do more intelligent stuff like negotiating price, etc.

Introduction
------------

Negotiator is a RESTful HTTP server. You can call provide client url and category Id and Negotiator will return you the prices for various products in that category for that client.
Negotiator also has an api to process bulk request so that you can process a large number of clients in one request.

Documentation
-------------
* [Negotiator Client API] (https://github.com/bazaarvoice/doula/tree/master/docs/NegotiatorClientApi.md)

Quick Start
-----------

### Installation

1.  Install Maven

2.  Download the [Negotiator source code] (https://github.com/mmunna/negotiator.git):

        $ git clone git@github.com:/mmunna/negotiator.git negotiator

3.  Build the source and run the tests:

        $ cd negotiator
        $ mvn clean install

4.  Run the Negotiator server locally:

        $ cd service
        $ mvn -Prun
        ...
        INFO  [2014-01-13 16:40:09,935] org.eclipse.jetty.server.AbstractConnector: Started InstrumentedBlockingChannelConnector@0.0.0.0:8080
        INFO  [2014-01-13 16:40:09,939] org.eclipse.jetty.server.AbstractConnector: Started SocketConnector@0.0.0.0:8081
        # Use Ctrl-C to kill the server when you are done.

7.  Check that the server responds to requests (from another window):

        $ curl -s "http://localhost:8081/ping"
        pong

        $ curl 'localhost:8081/healthcheck'
          * deadlocks: OK
          * negotiationservice: OK
            service is alive


#### Examples:

The following examples assume you have [jsonpp] (http://jmhodges.github.com/jsonpp/) or an equivalent (see Recommended
Software below).  It is optional--jsonpp just formats the JSON responses to make them easier to read.

####Get client product price using Negotiator.

1. Provide clientUrl and categoryId as query parameter

   $ curl 'localhost:8080/price?clientUrl=http://example.com/app/v1&categoryId=1234' | jsonpp

   {
     "/products/1": "123.00",
     "/products/3": "134.00",
     "/products/2": "34.00",
     "/products/5": "345.00",
     "/products/4": "433.00"
   }

2. Send a bulk request for multiple clientUrl and categoryIds.

   $ curl -XPOST -H "Content-Type: application/json" 'http://localhost:8080/price/batch' --data-binary '[{"clientUrl":"http://www.example1.com/app/v1","categoryId":"123"}, {"clientUrl":"http://www.example.com/v2","categoryId":"345"}]' | jsonpp

   [
     {
       "clientUrl": "http://www.example.com/v2",
       "categoryId": "345",
       "price": {
         "/products/1": "234.00",
         "/products/3": "345.00",
         "/products/2": "45.00",
         "/products/5": "435.00",
         "/products/4": "13.00"
       }
     },
     {
       "clientUrl": "http://www.example1.com/app/v1",
       "categoryId": "123",
       "price": {
         "/products/1": "45.00",
         "/products/3": "565.00",
         "/products/2": "646.00",
         "/products/5": "54.00",
         "/products/4": "456.00"
       }
     }
   ]


Recommended Software
--------------------

For debugging, it's useful to have a JSON pretty printer.  On a Mac with [Homebrew] (http://mxcl.github.com/homebrew/)
installed:

    brew install jsonpp

Alternatively, use Python's `json.tool`:

    alias jsonpp='python -mjson.tool'

Many of the examples include `jsonpp`.  Running the examples without `jsonpp` will work just fine, but the results may
be more difficult to read.

