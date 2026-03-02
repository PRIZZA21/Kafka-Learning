## When does Rebalancing happen ?
Rebalancing happens when Kafka needs to redistribute partitions among consumers in a group.

Inside a consumer group → one partition can be assigned to only one consumer.

So whenever group membership changes, Kafka must reassign partitions.
That reassignment process = Rebalancing.

## Rebalancing Trigger factors
Rebalancing is triggered when:

1️⃣ A new consumer joins the group
2️⃣ A consumer leaves the group
3️⃣ A consumer crashes
4️⃣ Partitions of topic change

Any of these → group must be recalculated.


## When a consumer crashes
1.Heartbeats stop
2.Broker detects failure
3.Rebalance triggered
4.Active consumers pause
5.Partitions reassigned
6.Consumers resume from last committed offsets

## Freuquent Rebalancing leads to 
Rebalance Storm  <href>https://medium.com/@cobch7/kafka-rebalance-storm-0dac87ea7e59</href>


