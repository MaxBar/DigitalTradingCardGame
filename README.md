# DigitalTradingCardGame

To make it work you have to download both the server from the server branch as well as 2 clients from the client branch. The clients and server needs to be behind the same router since it can't currently handle requests made by the same IP (as it is going into the router).

You also need to edit the IP it will connect to in the source code. For client: NetworkClient/NetworkClient at row 20
